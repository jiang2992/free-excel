package org.jiang.office.excel.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * excel容器
 *
 * @author Bin
 * @since 1.0.0
 */
public abstract class ExcelContainer extends ExcelElement {

    public static final boolean DIRECTION_HORIZONTAL = true;
    public static final boolean DIRECTION_VERTICAL = false;

    private boolean direction = DIRECTION_HORIZONTAL;

    @Getter
    private List<ExcelElement> children;

    public ExcelContainer() {
    }

    /**
     * 创建一个指定排序方向的块元素
     *
     * @param direction 排序方向
     */
    public ExcelContainer(boolean direction) {
        this.setDirection(direction);
    }

    /**
     * 获取当前的排序方向
     *
     * @return 排序方向
     */
    public boolean getDirection() {
        return this.direction;
    }

    /**
     * 设置容器的排序方向
     *
     * @param direction 排序方向
     */
    protected void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * 判断当前是否是水平排序
     *
     * @return 是否水平排序
     */
    public boolean isHorizontal() {
        return this.direction == DIRECTION_HORIZONTAL;
    }

    /**
     * 判断当前是否是垂直排序
     *
     * @return 是否垂直排序
     */
    public boolean isVertical() {
        return this.direction == DIRECTION_VERTICAL;
    }

    /**
     * 添加子元素
     *
     * @param element 子元素
     */
    protected void addElement(ExcelElement element) {
        if (children == null) {
            children = new ArrayList<>();
        }
        if (element != null) {
            children.add(element);
        }
    }

    /**
     * 添加子元素
     *
     * @param elements 子元素
     */
    protected void addElement(ExcelElement... elements) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.addAll(Arrays.stream(elements).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    /**
     * 替换子元素
     *
     * @param id      元素id
     * @param element 新元素
     * @return 结果
     */
    public boolean replace(String id, ExcelElement element) {
        if (children == null) {
            return false;
        }
        for (int i = 0; i < children.size(); i++) {
            ExcelElement child = children.get(i);
            if (child.getId() != null && child.getId().equals(id)) {
                children.set(i, element);
                return true;
            }
            if (child instanceof ExcelContainer) {
                if (((ExcelContainer) child).replace(id, element)) {
                    return true;
                }
            }
        }
        return false;
    }

}
