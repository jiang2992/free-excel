package org.jiang.office.excel.element;

import lombok.Getter;

/**
 * 页面（通常代表一个Sheet）
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
public class ExcelPage extends ExcelContainer {

    private int freezeRow = 0;

    private int freezeCol = 0;

    public ExcelPage() {
        super(ExcelContainer.DIRECTION_VERTICAL);
    }

    /**
     * 创建一个指定排序方向的页面
     *
     * @param direction 排序方向
     */
    public ExcelPage(boolean direction) {
        this.direction(direction);
    }

    /**
     * 创建一个水平排序的页面
     *
     * @return ExcelPage
     */
    public static ExcelPage horizontal() {
        return new ExcelPage(ExcelContainer.DIRECTION_HORIZONTAL);
    }

    /**
     * 创建一个垂直排序的页面
     *
     * @return ExcelPage
     */
    public static ExcelPage vertical() {
        return new ExcelPage(ExcelContainer.DIRECTION_VERTICAL);
    }

    /**
     * 设置当前的排序方向
     *
     * @param direction 排序方向
     * @return ExcelPage
     */
    public ExcelPage direction(boolean direction) {
        super.setDirection(direction);
        return this;
    }

    /**
     * 添加子元素
     *
     * @param element 子元素
     * @return ExcelPage
     */
    public ExcelPage add(ExcelElement element) {
        super.addElement(element);
        return this;
    }

    /**
     * 添加子元素
     *
     * @param elements 子元素
     * @return ExcelPage
     */
    public ExcelPage add(ExcelElement... elements) {
        super.addElement(elements);
        return this;
    }

    /**
     * 设置冻结行数
     *
     * @param freezeRow 行数
     * @return ExcelPage
     */
    public ExcelPage freezeRow(int freezeRow) {
        this.freezeRow = freezeRow;
        return this;
    }

    /**
     * 设置冻结列数
     *
     * @param freezeCol 列数
     * @return ExcelPage
     */
    public ExcelPage freezeCol(int freezeCol) {
        this.freezeCol = freezeCol;
        return this;
    }

}
