package org.jiang.office.excel.element;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;

/**
 * excel单元格
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
public abstract class ExcelCell extends ExcelElement {

    private int rowSpan = 1;
    private int colSpan = 1;

    /**
     * 设置跨行
     *
     * @param span 行数
     * @return ExcelCell
     */
    public ExcelCell rowSpan(int span) {
        this.rowSpan = span;
        return this;
    }

    /**
     * 设置跨列
     *
     * @param span 列数
     * @return ExcelCell
     */
    public ExcelCell colSpan(int span) {
        this.colSpan = span;
        return this;
    }

    public abstract void updateCell(Cell cell, DataFormat dataFormat);

}
