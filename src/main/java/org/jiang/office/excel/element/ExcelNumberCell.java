package org.jiang.office.excel.element;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel数字单元格
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class ExcelNumberCell extends ExcelCell {

    private Double number;
    private int precision;

    public ExcelNumberCell(Double number) {
        this(number, 2);
        this.number = number;
    }

    public ExcelNumberCell(Double number, int precision) {
        this.number = number;
        this.precision = precision;
    }

    public static ExcelNumberCell of(Double number) {
        return new ExcelNumberCell(number);
    }

    public static ExcelNumberCell of(Double number, int precision) {
        return new ExcelNumberCell(number, precision);
    }

    @Override
    public void updateCell(Cell cell, DataFormat dataFormat) {
        Workbook workbook = cell.getRow().getSheet().getWorkbook();
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
        }
        cellStyle.setDataFormat(dataFormat.getFormat(getFormatStr()));
        cell.setCellValue(this.number);
    }

    private String getFormatStr() {
        StringBuilder sb = new StringBuilder("0");
        if (precision <= 0) {
            return sb.toString();
        }
        sb.append(".");
        for (int i = 0; i < precision; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

}
