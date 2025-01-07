package org.jiang.office.excel.element;

import java.util.Objects;
import lombok.Getter;
import org.jiang.office.excel.style.ExcelCellStyle;

/**
 * excel元素
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
public abstract class ExcelElement {

    private String id;

    private ExcelCellStyle style;

    public ExcelElement() {
    }

    public ExcelElement(String id) {
        this.id = id;
    }

    public ExcelElement id(String id) {
        this.id = id;
        return this;
    }

    public ExcelElement style(ExcelCellStyle style) {
        this.style = style.clone();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (id == null) {
            return super.equals(o);
        }
        ExcelElement that = (ExcelElement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hash(id);
        }
        return super.hashCode();
    }

}
