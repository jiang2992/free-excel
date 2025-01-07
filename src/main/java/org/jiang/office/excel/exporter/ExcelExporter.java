package org.jiang.office.excel.exporter;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel导出器基类
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
public abstract class ExcelExporter implements Closeable {

    protected Workbook workbook;

    /**
     * 将数据写到输出流中
     *
     * @param outputStream 输出流
     * @throws IOException IO异常
     */
    public abstract void write(OutputStream outputStream) throws IOException;

    @Override
    public void close() throws IOException {
        if (workbook == null) {
            return;
        }
        workbook.close();
    }

}
