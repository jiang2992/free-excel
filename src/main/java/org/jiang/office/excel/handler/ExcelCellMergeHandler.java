package org.jiang.office.excel.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jiang.office.excel.exporter.ElementFrame;

/**
 * 单元格合并处理器
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelCellMergeHandler implements ExcelCellHandler {

    @Override
    public void invoke(ElementFrame frame, Cell cell) {
        Sheet sheet = cell.getRow().getSheet();
        if (frame.getMerge() == null) {
            return;
        }
        int endRow = frame.getStartRow() + frame.getHeight() - 1;
        int endCol = frame.getStartCol() + frame.getWidth() - 1;

        // 被合并部分需要创建空单元格，否则边框等样式不生效
        for (int row = frame.getStartRow(); row <= endRow; row++) {
            for (int col = frame.getStartCol(); col <= endCol; col++) {
                if (row == frame.getStartRow() && col == frame.getStartCol()) {
                    continue;
                }
                Cell emptyCell = sheet.getRow(row).createCell(col);
                emptyCell.setCellValue(false);
                emptyCell.setCellStyle(cell.getCellStyle());
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(
                frame.getStartRow(), endRow,
                frame.getStartCol(), endCol
        ));
    }

}
