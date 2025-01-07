package org.jiang.office.excel.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.jiang.office.excel.element.ExcelCell;
import org.jiang.office.excel.exporter.ElementFrame;

/**
 * 单元格值处理器
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelCellValueHandler implements ExcelCellHandler {

    private DataFormat dataFormat;

    @Override
    public void initialize(Workbook workbook) {
        dataFormat = workbook.createDataFormat();
    }

    @Override
    public void invoke(ElementFrame frame, Cell cell) {
        ExcelCell excelCell = (ExcelCell) frame.getElement();
        excelCell.updateCell(cell, dataFormat);
    }

}
