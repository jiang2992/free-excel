package org.jiang.office.excel.element;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel图片单元格
 *
 * @author Bin
 * @since 1.0.2
 */
@Getter
@Setter
public class ExcelImageCell extends ExcelCell {

    private byte[] pictureData;

    private int pictureType = Workbook.PICTURE_TYPE_JPEG;

    private int dx1 = 0;
    private int dy1 = 0;
    private int dx2 = 0;
    private int dy2 = 0;

    public ExcelImageCell(byte[] pictureData) {
        this.pictureData = pictureData;
    }

    public static ExcelImageCell of(byte[] pictureData) {
        return new ExcelImageCell(pictureData);
    }

    /**
     * 设置图片类型
     * <p>
     * 枚举值 PICTURE_TYPE_EMF、PICTURE_TYPE_WMF、PICTURE_TYPE_PNG、PICTURE_TYPE_DIB、PICTURE_TYPE_PICT、PICTURE_TYPE_JPEG
     * <p>
     * 默认为 Workbook.PICTURE_TYPE_JPEG
     *
     * @param pictureType 图片类型
     * @return ExcelImageCell
     * @see org.apache.poi.ss.usermodel.Workbook
     */
    public ExcelImageCell pictureType(int pictureType) {
        this.pictureType = pictureType;
        return this;
    }

    /**
     * 设置图片偏移量
     *
     * @param dx1 图片左上角相对于单元格的偏移量x
     * @param dy1 图片左上角相对于单元格的偏移量y
     * @param dx2 图片右下角相对于单元格的偏移量x
     * @param dy2 图片右下角相对于单元格的偏移量y
     * @return ExcelImageCell
     */
    public ExcelImageCell offset(int dx1, int dy1, int dx2, int dy2) {
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
        return this;
    }

    @Override
    public void updateCell(Cell cell, DataFormat dataFormat) {
        if (this.pictureData == null || this.pictureData.length == 0) {
            ExcelTextCell textCell = new ExcelTextCell("[unknown picture]");
            textCell.updateCell(cell, dataFormat);
            return;
        }
        Sheet sheet = cell.getRow().getSheet();
        Workbook workbook = sheet.getWorkbook();
        int index = workbook.addPicture(this.pictureData, this.pictureType);
        Drawing<?> drawingPatriarch = sheet.getDrawingPatriarch();
        if (drawingPatriarch == null) {
            drawingPatriarch = sheet.createDrawingPatriarch();
        }
        int col = cell.getColumnIndex();
        int row = cell.getRowIndex();
        ClientAnchor anchor = drawingPatriarch.createAnchor(dx1, dy1, dx2, dy2, col, row, col + 1, row + 1);
        drawingPatriarch.createPicture(anchor, index);
    }

}
