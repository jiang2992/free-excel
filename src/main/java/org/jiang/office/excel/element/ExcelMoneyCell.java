package org.jiang.office.excel.element;

import lombok.Getter;
import lombok.Setter;

/**
 * excel金额单元格
 *
 * @author Bin
 * @since 2024/12/3 14:33
 */
@Getter
@Setter
public class ExcelMoneyCell extends ExcelNumberCell {

    public ExcelMoneyCell(Double number) {
        this(number, 2);
    }

    public ExcelMoneyCell(Double number, int precision) {
        super(number, precision);
    }

    public static ExcelMoneyCell of(Double number) {
        return new ExcelMoneyCell(number);
    }

    public static ExcelMoneyCell of(Double number, int precision) {
        return new ExcelMoneyCell(number, precision);
    }

    @Override
    protected String getFormatStr() {
        return "#,##" + super.getFormatStr();
    }

}
