package org.jiang.office.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import org.jiang.office.excel.element.ExcelPage;
import org.jiang.office.excel.element.ExcelPiece;
import org.jiang.office.excel.element.ExcelTextCell;
import org.jiang.office.excel.exporter.ExcelSimpleExporter;
import org.jiang.tools.text.RandomUtils;
import org.jiang.tools.text.StringUtils;
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
        ExcelPage page = ExcelPage.vertical();
        for (int i = 0; i < 100; i++) {
            ExcelPiece rowPiece = ExcelPiece.horizontal();
            for (int j = 0; j < 100; j++) {
                rowPiece.add(ExcelTextCell.of(RandomUtils.generate(1000)));
            }
            page.add(rowPiece);
        }
        File file = new File("test.xlsx");
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        ExcelSimpleExporter.of(page).write(Files.newOutputStream(file.toPath()));
    }


}
