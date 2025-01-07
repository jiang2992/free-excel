package org.jiang.office.excel.exporter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jiang.office.excel.element.ExcelCell;
import org.jiang.office.excel.element.ExcelContainer;
import org.jiang.office.excel.element.ExcelElement;
import org.jiang.office.excel.element.ExcelTextCell;
import org.jiang.office.excel.handler.ExcelCellHandler;
import org.jiang.office.excel.handler.ExcelCellMergeHandler;
import org.jiang.office.excel.handler.ExcelCellStyleHandler;
import org.jiang.office.excel.handler.ExcelCellValueHandler;
import org.jiang.office.excel.handler.ExcelPageHandler;

/**
 * 一个简单的Excel导出器
 * <br> 最高支持10000行的内存操作
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelSimpleExporter extends ExcelExporter {

    private static final int MAX_ROW_ACCESS = 10000;

    private final Collection<ExcelCellHandler> handlers = new LinkedList<>();

    private final Map<String, ExcelContainer> sheets = new LinkedHashMap<>();

    public ExcelSimpleExporter() {
        this.workbook = new SXSSFWorkbook(MAX_ROW_ACCESS);
        handlers.add(new ExcelCellStyleHandler());
        handlers.add(new ExcelCellMergeHandler());
        handlers.add(new ExcelCellValueHandler());
        handlers.add(new ExcelPageHandler());
        for (ExcelCellHandler handler : handlers) {
            handler.initialize(workbook);
        }
    }

    public ExcelSimpleExporter(ExcelContainer container) {
        this();
        this.sheets.put(null, container);
    }

    /**
     * 从单个匿名工作表创建导出器
     *
     * @param container 根元素
     * @return ExcelSimpleExporter
     */
    public static ExcelSimpleExporter of(ExcelContainer container) {
        return new ExcelSimpleExporter(container);
    }

    /**
     * 添加工作表
     *
     * @param name      名称
     * @param container 根元素
     * @return ExcelSimpleExporter
     */
    public ExcelSimpleExporter addSheet(String name, ExcelContainer container) {
        this.sheets.put(name, container);
        return this;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        for (String name : this.sheets.keySet()) {
            ExcelContainer container = this.sheets.get(name);
            this.createSheet(workbook, name, container);
        }
        workbook.write(outputStream);
    }

    private void createSheet(Workbook workbook, String name, ExcelContainer container) {
        Sheet sheet = name != null ? workbook.createSheet(name) : workbook.createSheet();

        // 构造元素结构
        ElementFrame rootFrame = createElementFrame(container);
        rootFrame.updateSize(rootFrame.getWidth(), rootFrame.getHeight(), true, true);

        // 创建行
        List<Row> rows = new ArrayList<>(rootFrame.getHeight());
        for (int i = 0; i < rootFrame.getHeight(); i++) {
            rows.add(sheet.createRow(i));
        }

        // 创建单元格
        Stack<ElementFrame> stack = new Stack<>();
        stack.push(rootFrame);
        while (!stack.empty()) {
            ElementFrame frame = stack.pop();
            if (!frame.isCell()) {
                if (frame.getChildren() != null) {
                    frame.getChildren().forEach(stack::push);
                }
                continue;
            }
            Row row = rows.get(frame.getStartRow());
            Cell cell = row.createCell(frame.getStartCol());
            for (ExcelCellHandler handler : handlers) {
                handler.invoke(frame, cell);
            }
        }

        // 单元格处理器完成事件
        for (ExcelCellHandler handler : handlers) {
            handler.complete(rootFrame, sheet);
        }

    }

    /**
     * 这是一个递归函数，将工作表的所有元素作为一个树形，首次传入根节点，递归构造出对应的元素结构
     *
     * @param element 元素
     * @return 元素结构
     */
    private ElementFrame createElementFrame(ExcelElement element) {
        ElementFrame frame = new ElementFrame(element);
        if (frame.isCell()) {
            ExcelCell cell = (ExcelCell) element;
            frame.setWidth(cell.getColSpan());
            frame.setHeight(cell.getRowSpan());
            return frame;
        }

        ExcelContainer container = (ExcelContainer) element;

        // 如果这是一个空容器，则默认分配一个空单元格
        if (container == null || container.getChildren() == null) {
            frame.setElement(ExcelTextCell.of(""));
            frame.setWidth(1);
            frame.setHeight(1);
            return frame;
        }

        // 创建子元素结构
        for (ExcelElement child : container.getChildren()) {
            ElementFrame childFrame = this.createElementFrame(child);
            childFrame.setParent(frame);
            frame.add(childFrame);
            if (container.isHorizontal()) {
                frame.addWidth(childFrame.getWidth());
                frame.setHeight(Integer.max(frame.getHeight(), childFrame.getHeight()));
            } else {
                frame.setWidth(Integer.max(frame.getWidth(), childFrame.getWidth()));
                frame.addHeight(childFrame.getHeight());
            }
        }

        return frame;
    }

}
