package org.jiang.office.excel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import org.jiang.office.excel.element.ExcelImageCell;
import org.jiang.office.excel.element.ExcelPage;
import org.jiang.office.excel.element.ExcelPiece;
import org.jiang.office.excel.element.ExcelTextCell;
import org.jiang.office.excel.exporter.ExcelSimpleExporter;
import org.jiang.office.excel.style.ExcelCellStyle;
import org.jiang.tools.data.EasyData;
import org.jiang.tools.text.RandomUtils;
import org.junit.Test;

/**
 * excel 导出测试
 *
 * @author Bin
 * @since 2025/1/7 15:41
 */
public class ExcelExportTests {

    @Test
    public void test() throws IOException {
        ExcelCellStyle style = new ExcelCellStyle().width(6000).height(120).horizontalCenter().verticalCenter();
        ExcelPage page = ExcelPage.vertical();
        page.style(style);
        for (int i = 0; i < 10; i++) {
            ExcelPiece rowPiece = ExcelPiece.horizontal();
            for (int j = 0; j < 10; j++) {
                rowPiece.add(ExcelTextCell.of(RandomUtils.generate(10)));
            }
            page.add(rowPiece);
        }

        EasyData image = EasyData.of(new URL("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"));
        ExcelPiece rowPiece = ExcelPiece.horizontal();
        for (int j = 0; j < 10; j++) {
            rowPiece.add(ExcelImageCell.of(image));
        }
        page.add(rowPiece);

        File file = new File("test.xlsx");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        ExcelSimpleExporter.of(page).write(Files.newOutputStream(file.toPath()));
    }

}
