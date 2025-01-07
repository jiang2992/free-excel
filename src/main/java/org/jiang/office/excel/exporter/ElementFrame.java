package org.jiang.office.excel.exporter;

import java.util.Arrays;
import java.util.LinkedList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jiang.office.excel.element.ExcelCell;
import org.jiang.office.excel.element.ExcelContainer;
import org.jiang.office.excel.element.ExcelElement;

/**
 * excel元素结构
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class ElementFrame {

    private ExcelElement element;

    private int startRow;

    private int startCol;

    private int height;

    private int width;

    private ElementFrame parent;

    private LinkedList<ElementFrame> children;

    private CellMerge merge;

    public ElementFrame(ExcelElement element) {
        this.element = element;
    }

    /**
     * 添加子节点
     *
     * @param frames 子节点
     */
    public void add(ElementFrame... frames) {
        if (children == null) {
            children = new LinkedList<>();
        }
        children.addAll(Arrays.asList(frames));
    }

    /**
     * 添加高度
     *
     * @param height 高度
     */
    public void addHeight(int height) {
        this.height += height;
    }

    /**
     * 添加宽度
     *
     * @param width 宽度
     */
    public void addWidth(int width) {
        this.width += width;
    }

    /**
     * 判断元素是否是单元格
     *
     * @return 是否单元格
     */
    public boolean isCell() {
        return this.element instanceof ExcelCell;
    }

    protected void setPosition(int startRow, int startCol) {
        this.startRow = startRow;
        this.startCol = startCol;
    }

    /**
     * 更新整棵树元素的宽高
     *
     * @param widthCapacity  宽度容量
     * @param heightCapacity 高度容量
     * @param widthFill      宽度是否填满
     * @param heightFill     高度是否填满
     */
    protected void updateSize(int widthCapacity, int heightCapacity, boolean widthFill, boolean heightFill) {
        this.width = widthCapacity = widthFill ? widthCapacity : this.width;
        this.height = heightCapacity = heightFill ? heightCapacity : this.height;
        if (this.isCell()) {
            if (this.height == 1 && this.width == 1) {
                return;
            }
            this.merge = new CellMerge(this.startRow, this.startRow + height - 1, this.startCol, this.startCol + width - 1);
        }
        if (this.children == null) {
            return;
        }
        ExcelContainer container = (ExcelContainer) element;
        int startRow = this.startRow;
        int startCol = this.startCol;
        for (ElementFrame child : this.children) {
            boolean isLast = child == this.children.getLast();
            child.setPosition(startRow, startCol);
            if (container.isHorizontal()) {
                child.updateSize(widthCapacity, heightCapacity, isLast, true);
                startCol += child.getWidth();
                widthCapacity -= child.getWidth();
            } else {
                child.updateSize(widthCapacity, heightCapacity, true, isLast);
                startRow += child.getHeight();
                heightCapacity -= child.getHeight();
            }
        }
    }

    /**
     * 单元格合并信息
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CellMerge {

        /**
         * 开始行
         */
        private int startRow;

        /**
         * 结束行
         */
        private int endRow;

        /**
         * 开始列
         */
        private int startColumn;

        /**
         * 结束列
         */
        private int endColumn;

    }

}
