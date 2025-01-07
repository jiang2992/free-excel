package org.jiang.office.excel.element;

/**
 * 块元素
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelPiece extends ExcelContainer {

    public ExcelPiece() {
    }

    /**
     * 创建一个指定排序方向的块元素
     *
     * @param direction 排序方向
     */
    public ExcelPiece(boolean direction) {
        this.direction(direction);
    }

    /**
     * 创建一个水平排序的块元素
     *
     * @return ExcelPiece
     */
    public static ExcelPiece horizontal() {
        return new ExcelPiece(DIRECTION_HORIZONTAL);
    }

    /**
     * 创建一个垂直排序的块元素
     *
     * @return ExcelPiece
     */
    public static ExcelPiece vertical() {
        return new ExcelPiece(DIRECTION_VERTICAL);
    }

    /**
     * 设置当前的排序方向
     *
     * @param direction 排序方向
     * @return ExcelPiece
     */
    public ExcelPiece direction(boolean direction) {
        super.setDirection(direction);
        return this;
    }

    /**
     * 添加子元素
     *
     * @param element 子元素
     * @return ExcelPiece
     */
    public ExcelPiece add(ExcelElement element) {
        super.addElement(element);
        return this;
    }

    /**
     * 添加子元素
     *
     * @param elements 子元素
     * @return ExcelPiece
     */
    public ExcelPiece add(ExcelElement... elements) {
        super.addElement(elements);
        return this;
    }

}
