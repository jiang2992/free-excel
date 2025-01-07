package org.jiang.office.excel.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jiang.office.excel.exporter.ElementFrame;

/**
 * 单元格处理器
 *
 * @author Bin
 * @since 1.0.0
 */
public interface ExcelCellHandler {

    /**
     * Workbook初始化完毕调用该方法
     *
     * @param workbook Workbook
     */
    default void initialize(Workbook workbook) {
    }

    /**
     * 在每个单元格被创建之后会调用该方法
     *
     * @param elementFrame 元素结构
     * @param cell         单元格
     */
    default void invoke(ElementFrame elementFrame, Cell cell) {
    }

    /**
     * 在所有单元格创建完毕后会调用该方法
     *
     * @param rootFrame 根元素结构
     * @param sheet     工作表
     */
    default void complete(ElementFrame rootFrame, Sheet sheet) {
    }

}
