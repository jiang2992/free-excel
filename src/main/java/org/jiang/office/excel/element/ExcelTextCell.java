package org.jiang.office.excel.element;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel文本单元格
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class ExcelTextCell extends ExcelCell {

    private String text;

    public ExcelTextCell(String text) {
        this.text = text;
    }

    public static ExcelTextCell of(String text) {
        return new ExcelTextCell(text);
    }

    @Override
    public void updateCell(Cell cell, DataFormat dataFormat) {
        Workbook workbook = cell.getRow().getSheet().getWorkbook();
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
            cell.setCellStyle(cellStyle);
        }
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
        cell.setCellValue(this.text);
    }

}
