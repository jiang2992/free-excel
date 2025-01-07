package org.jiang.office.excel.handler;

import org.apache.poi.ss.usermodel.Sheet;
import org.jiang.office.excel.element.ExcelPage;
import org.jiang.office.excel.exporter.ElementFrame;

/**
 * 页面处理器
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExcelPageHandler implements ExcelCellHandler {

    @Override
    public void complete(ElementFrame rootFrame, Sheet sheet) {
        if (!(rootFrame.getElement() instanceof ExcelPage)) {
            return;
        }
        ExcelPage page = (ExcelPage) rootFrame.getElement();

        // 冻结处理
        if (page.getFreezeCol() > 0 || page.getFreezeRow() > 0) {
            sheet.createFreezePane(page.getFreezeCol(), page.getFreezeRow());
        }

    }

}
