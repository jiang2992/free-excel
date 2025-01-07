package org.jiang.office.excel.handler;

import java.util.HashMap;
import java.util.LinkedList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.jiang.office.excel.exporter.ElementFrame;
import org.jiang.office.excel.style.ExcelCellStyle;

/**
 * 单元格样式处理器
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelCellStyleHandler implements ExcelCellHandler {

    private final HashMap<ExcelCellStyle, CellStyle> cellStyleMap = new HashMap<>();
    private final HashMap<ExcelCellStyle, Font> cellFontMap = new HashMap<>();

    @Override
    public void invoke(ElementFrame elementFrame, Cell cell) {

        // 获取样式继承链路
        LinkedList<ExcelCellStyle> styleList = new LinkedList<>();
        ElementFrame c = elementFrame;
        while (c != null) {
            ExcelCellStyle style = c.getElement().getStyle();
            if (style != null) {
                styleList.addFirst(style);
                if (!style.isInherit()) {
                    break;
                }
            }
            c = c.getParent();
        }

        if (styleList.isEmpty()) {
            return;
        }

        // 处理样式
        CellStyle cellStyle = getCellStyle(styleList, cell);
        Font cellFont = getCellFont(styleList, cell);
        cellStyle.setFont(cellFont);
        cell.setCellStyle(cellStyle);
        for (ExcelCellStyle excelCellStyle : styleList) {
            excelCellStyle.decorate(cellStyle, cellFont);
        }

    }

    @Override
    public void complete(ElementFrame rootFrame, Sheet sheet) {
        LinkedList<ElementFrame> list = new LinkedList<>();
        list.add(rootFrame);
        while (!list.isEmpty()) {
            ElementFrame frame = list.pop();
            if (frame.getChildren() != null) {
                list.addAll(frame.getChildren());
            }

            if (frame.getElement().getStyle() == null) {
                continue;
            }

            // 处理列宽
            if (frame.getElement().getStyle().getWidth() > 0) {
                int width = frame.getElement().getStyle().getWidth();
                int endCol = frame.getStartCol() + frame.getWidth() - 1;
                for (int i = frame.getStartCol(); i <= endCol; i++) {
                    sheet.setColumnWidth(i, width);
                }
            }

            // 处理行高
            if (frame.getElement().getStyle().getHeight() > 0) {
                int height = frame.getElement().getStyle().getHeight();
                int endRow = frame.getStartRow() + frame.getHeight() - 1;
                for (int i = frame.getStartRow(); i <= endRow; i++) {
                    sheet.getRow(i).setHeightInPoints(height);
                }
            }

        }
    }

    private CellStyle getCellStyle(LinkedList<ExcelCellStyle> styleList, Cell cell) {
        return cellStyleMap.computeIfAbsent(styleList.getLast(), key -> cell.getRow().getSheet().getWorkbook().createCellStyle());
    }

    private Font getCellFont(LinkedList<ExcelCellStyle> styleList, Cell cell) {
        return cellFontMap.computeIfAbsent(styleList.getLast(), key -> cell.getRow().getSheet().getWorkbook().createFont());
    }

}
