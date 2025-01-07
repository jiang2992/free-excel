package org.jiang.office.excel.style;

import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.Getter;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * 单元格样式
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelCellStyle implements Cloneable {

    @Getter
    private boolean inherit = true;

    @Getter
    private int width;

    @Getter
    private int height;

    private LinkedList<BiConsumer<CellStyle, Font>> process = new LinkedList<>();

    /**
     * 设置是否继承父元素的样式
     * <br> (不包括 width,height)
     *
     * @param inherit 是否继承
     * @return ExcelCellStyle
     */
    public ExcelCellStyle inherit(boolean inherit) {
        this.inherit = inherit;
        return this;
    }

    /**
     * 字体处理
     *
     * @param fun 处理函数
     * @return ExcelCellStyle
     */
    public ExcelCellStyle font(Consumer<Font> fun) {
        process.push((cellStyle, font) -> fun.accept(font));
        return this;
    }

    /**
     * 样式处理
     *
     * @param fun 处理函数
     * @return ExcelCellStyle
     */
    public ExcelCellStyle style(Consumer<CellStyle> fun) {
        process.push((cellStyle, font) -> fun.accept(cellStyle));
        return this;
    }

    public void decorate(CellStyle cellStyle, Font font) {
        for (BiConsumer<CellStyle, Font> consumer : process) {
            consumer.accept(cellStyle, font);
        }
    }

    /**
     * 设置高度
     *
     * @param width 宽度
     * @return ExcelCellStyle
     */
    public ExcelCellStyle width(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置高度
     *
     * @param height 高度
     * @return ExcelCellStyle
     */
    public ExcelCellStyle height(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置字体加粗
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle fontBold() {
        return this.fontBold(true);
    }

    /**
     * 设置字体加粗
     *
     * @param bold 是否加粗
     * @return ExcelCellStyle
     */
    public ExcelCellStyle fontBold(boolean bold) {
        return this.font(font -> font.setBold(bold));
    }

    /**
     * 设置字体名称
     *
     * @param name 字体名称
     * @return ExcelCellStyle
     */
    public ExcelCellStyle fontName(String name) {
        return this.font(font -> font.setFontName(name));
    }

    /**
     * 设置字体高度
     *
     * @param height 字体名称
     * @return ExcelCellStyle
     */
    public ExcelCellStyle fontHeight(int height) {
        return this.font(font -> font.setFontHeightInPoints((short) height));
    }

    /**
     * 设置字体颜色
     *
     * @param color 字体颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle fontColor(IndexedColors color) {
        return this.font(font -> font.setColor(color.getIndex()));
    }

    /**
     * 设置文本换行
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle wrapText() {
        return this.wrapText(true);
    }

    /**
     * 设置文本换行
     *
     * @param wrap 是否换行
     * @return ExcelCellStyle
     */
    public ExcelCellStyle wrapText(boolean wrap) {
        return this.style(style -> style.setWrapText(wrap));
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle backgroundColor(IndexedColors color) {
        return this.style(style -> {
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        });
    }

    /**
     * 设置水平排列方式为：居中
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle horizontalCenter() {
        return this.horizontalAlign(HorizontalAlignment.CENTER);
    }

    /**
     * 设置水平排列方式为：靠左
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle horizontalLeft() {
        return this.horizontalAlign(HorizontalAlignment.LEFT);
    }

    /**
     * 设置水平排列方式为：靠右
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle horizontalRight() {
        return this.horizontalAlign(HorizontalAlignment.RIGHT);
    }

    /**
     * 设置水平排列方式
     *
     * @param alignment 排列方式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle horizontalAlign(HorizontalAlignment alignment) {
        return this.style(style -> style.setAlignment(alignment));
    }

    /**
     * 设置垂直排列方式为：顶部
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle verticalTop() {
        return this.verticalAlign(VerticalAlignment.TOP);
    }

    /**
     * 设置垂直排列方式为：居中
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle verticalCenter() {
        return this.verticalAlign(VerticalAlignment.CENTER);
    }

    /**
     * 设置垂直排列方式为：底部
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle verticalBottom() {
        return this.verticalAlign(VerticalAlignment.BOTTOM);
    }

    /**
     * 设置垂直排列方式
     *
     * @param alignment 排列方式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle verticalAlign(VerticalAlignment alignment) {
        return this.style(style -> style.setVerticalAlignment(alignment));
    }

    /**
     * 设置上边框样式
     *
     * @param borderStyle 边框样式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderTop(BorderStyle borderStyle) {
        return this.style(style -> style.setBorderTop(borderStyle));
    }

    /**
     * 设置下边框样式
     *
     * @param borderStyle 边框样式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderBottom(BorderStyle borderStyle) {
        return this.style(style -> style.setBorderBottom(borderStyle));
    }

    /**
     * 设置左边框样式
     *
     * @param borderStyle 边框样式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderLeft(BorderStyle borderStyle) {
        return this.style(style -> style.setBorderLeft(borderStyle));
    }

    /**
     * 设置右边框样式
     *
     * @param borderStyle 边框样式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderRight(BorderStyle borderStyle) {
        return this.style(style -> style.setBorderRight(borderStyle));
    }

    /**
     * 设置上边框颜色
     *
     * @param colors 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderColorTop(IndexedColors colors) {
        return this.style(style -> style.setTopBorderColor(colors.getIndex()));
    }

    /**
     * 设置下边框颜色
     *
     * @param colors 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderColorBottom(IndexedColors colors) {
        return this.style(style -> style.setBottomBorderColor(colors.getIndex()));
    }

    /**
     * 设置左边框颜色
     *
     * @param colors 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderColorLeft(IndexedColors colors) {
        return this.style(style -> style.setLeftBorderColor(colors.getIndex()));
    }

    /**
     * 设置右边框颜色
     *
     * @param colors 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderColorRight(IndexedColors colors) {
        return this.style(style -> style.setRightBorderColor(colors.getIndex()));
    }

    /**
     * 设置全部边框颜色
     *
     * @param colors 颜色
     * @return ExcelCellStyle
     */
    public ExcelCellStyle borderColor(IndexedColors colors) {
        this.borderColorTop(colors);
        this.borderColorBottom(colors);
        this.borderColorLeft(colors);
        this.borderColorRight(colors);
        return this;
    }

    /**
     * 设置全部边框样式
     *
     * @param borderStyle 边框样式
     * @return ExcelCellStyle
     */
    public ExcelCellStyle border(BorderStyle borderStyle) {
        this.borderTop(borderStyle);
        this.borderBottom(borderStyle);
        this.borderLeft(borderStyle);
        this.borderRight(borderStyle);
        return this;
    }

    /**
     * 启用默认边框样式和颜色
     *
     * @return ExcelCellStyle
     */
    public ExcelCellStyle border() {
        this.border(BorderStyle.THIN);
        this.borderColor(IndexedColors.BLACK1);
        return this;
    }

    @Override
    public ExcelCellStyle clone() {
        try {
            ExcelCellStyle clone = (ExcelCellStyle) super.clone();
            clone.process = new LinkedList<>(clone.process);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
