package org.jiang.office.excel.element;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel日期单元格
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class ExcelDateCell extends ExcelCell {

    private Date date;
    private String format;

    public ExcelDateCell(Date date) {
        this(date, "yyyy-MM-dd HH:mm:ss");
        this.date = date;
    }

    public ExcelDateCell(Date date, String format) {
        this.date = date;
        this.format = format;
    }

    public static ExcelDateCell of(Date date) {
        return new ExcelDateCell(date);
    }

    public static ExcelDateCell of(Date date, String format) {
        return new ExcelDateCell(date, format);
    }

    @Override
    public void updateCell(Cell cell, DataFormat dataFormat) {
        Workbook workbook = cell.getRow().getSheet().getWorkbook();
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle == null) {
            cellStyle = workbook.createCellStyle();
        }
        cellStyle.setDataFormat(dataFormat.getFormat(format));
        cell.setCellValue(this.date);
    }

}
