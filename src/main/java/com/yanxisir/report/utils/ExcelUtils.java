package com.yanxisir.report.utils;


import com.yanxisir.report.service.SXSSFExcelService;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class ExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);


    /**
     * 文字居左
     *
     * @param font WritableFont
     * @return WritableCellFormat
     * @throws WriteException 异常
     */
    public static WritableCellFormat cellFormatOnTextLeft(WritableFont font) throws WriteException {
        return cellFormat(font, Border.ALL, BorderLineStyle.THIN, VerticalAlignment.CENTRE, Alignment.LEFT);
    }

    /**
     * 文字居中
     *
     * @param font WritableFont
     * @return WritableCellFormat
     * @throws WriteException 异常
     */
    public static WritableCellFormat cellFormatOnTextCenter(WritableFont font) throws WriteException {
        return cellFormat(font, Border.ALL, BorderLineStyle.THIN, VerticalAlignment.CENTRE, Alignment.CENTRE);
    }

    public static WritableCellFormat cellFormat(WritableFont font, Border border, BorderLineStyle borderLineStyle, VerticalAlignment verticalAlignment, Alignment alignment) throws WriteException {
        WritableCellFormat format = new WritableCellFormat(font);
        format.setBorder(border, borderLineStyle);//边界线条
        format.setVerticalAlignment(verticalAlignment);//垂直对齐
        format.setAlignment(alignment);//水平对齐
        format.setWrap(true);//文字是否换行
        format.setShrinkToFit(true);
        return format;
    }

    public static void dealWithObjListToExcel(SXSSFExcelService excelService, List<List<Object>> listToExcel) throws Exception {
        try {
            for (List<Object> excelRow : listToExcel) {
                excelService.createRow(excelRow);
            }
            excelService.write();
        } catch (IOException e) {
            LOGGER.error("Failed to flush or write the output stream.", e);
        } finally {
            excelService.close();
        }
    }
}
