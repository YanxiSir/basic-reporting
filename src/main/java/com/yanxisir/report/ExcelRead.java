package com.yanxisir.report;


import com.google.common.base.Splitter;
import com.yanxisir.report.core.exception.ExcelException;
import com.yanxisir.report.utils.CellUtils;
import com.yanxisir.report.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: yanxi
 * date : 16/12/22
 */
public class ExcelRead extends BaseExcelRead {

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";

    private static final int DEFAULT_CACHE_SIZE = 100;

    /**
     * 一个sheet页,获取指定列内容
     *
     * @param is             excel输入流
     * @param suffix         excel后缀(xls|xlsx)
     * @param col            要获取指定列数数据
     * @param skipHeaderSize 跳过开始行数
     * @return 返回字符串列表
     * @throws IOException IO异常
     */
    public static List<String> getAppointedColValueOneSheet(InputStream is, String suffix, int col, int skipHeaderSize) throws IOException {
        return getAppointedColValue(is, suffix, 1, col, skipHeaderSize, DEFAULT_CACHE_SIZE);
    }

    /**
     * 获取指定列内容(去重)
     *
     * @param is             excel输入流
     * @param suffix         excel后缀(xls|xlsx)
     * @param sheetIndex     第几个sheet页
     * @param col            指定列
     * @param skipHeaderSize 跳过开始行数
     * @param cacheSize      缓存保存最大多少行数据
     * @return 返回字符串列表
     * @throws IOException IO异常
     */
    public static List<String> getAppointedColValue(InputStream is, String suffix, int sheetIndex, int col, int skipHeaderSize, int cacheSize) throws IOException {
        return getAppointedColValue(is, suffix, sheetIndex, col, skipHeaderSize, cacheSize, true);
    }

    /**
     * 获取指定列内容
     *
     * @param is             excel输入流
     * @param suffix         excel后缀(xls|xlsx)
     * @param sheetIndex     第几个sheet页
     * @param col            指定列
     * @param skipHeaderSize 跳过开始行数
     * @param cacheSize      缓存保存最大多少行数据
     * @param noRepeat       不能有重复数据
     * @return 返回字符串列表
     * @throws IOException IO异常
     */
    public static List<String> getAppointedColValue(InputStream is, String suffix, int sheetIndex, int col, int skipHeaderSize, int cacheSize, boolean noRepeat) throws IOException {
        //逻辑第一页:1;数组第一页:0
        col--;
        sheetIndex--;
        if (StringUtils.isEmpty(suffix) || is == null) {
            throw new ExcelException("suffix or is cannot be null");
        }
        Workbook workbook = null;
        if ("xls".equalsIgnoreCase(suffix)) {
            workbook = new HSSFWorkbook(is);
        } else if ("xlsx".equalsIgnoreCase(suffix)) {
            workbook = new XSSFWorkbook(is);
        } else {
            throw new ExcelException("suffix only support xls and xlsx");
        }
        if (workbook == null) {
            return null;
        }

        Sheet sheet = null;
        try {
            sheet = workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            throw new ExcelException("excel sheet is not have " + (sheetIndex + 1) + " pages!", e);
        }
        int begin = sheet.getFirstRowNum();
        int end = sheet.getLastRowNum();

        List<String> valueList = new ArrayList<String>();
        for (int i = begin + skipHeaderSize; i <= end; i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(col) == null) {
                continue;
            }
            Cell cell = row.getCell(col);
            String value = CellUtils.getCellValueString(cell);
            if (!noRepeat || (noRepeat && !valueList.contains(value))) {
                valueList.add(value);
            }
        }
        return valueList;
    }

    /**
     * 获取多列数据(不去重)
     *
     * @param is             输入流
     * @param suffix         后缀
     * @param sheetIndex     第几个sheet页
     * @param pos            第几列开始
     * @param colLen         取多少列
     * @param skipHeaderSize 跳过头部几行
     * @param cacheSize      缓存留几行
     * @return 返回字符串列表
     * @throws IOException IO异常
     */
    public static Map<Integer, List<String>> getMultiColValue(InputStream is, String suffix, int sheetIndex, int pos, int colLen, int skipHeaderSize, int cacheSize) throws IOException {

        Map<Integer, List<String>> rowValueMap = new HashMap<>();
        //逻辑第一页:1;数组第一页:0
        pos--;
        sheetIndex--;
        if (StringUtils.isEmpty(suffix) || is == null) {
            throw new ExcelException("suffix or is cannot be null");
        }
        Workbook workbook = null;
        if ("xls".equalsIgnoreCase(suffix)) {
            workbook = new HSSFWorkbook(is);
        } else if ("xlsx".equalsIgnoreCase(suffix)) {
            workbook = new XSSFWorkbook(is);
//            workbook = new SXSSFWorkbook(new XSSFWorkbook(is), cacheSize);
        } else {
            throw new ExcelException("suffix only support xls and xlsx");
        }
        if (workbook == null) {
            return null;
        }

        Sheet sheet = null;
        try {
            sheet = workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            throw new ExcelException("excel sheet is not have " + (sheetIndex + 1) + " pages!", e);
        }
        int begin = sheet.getFirstRowNum();
        int end = sheet.getLastRowNum();

        for (int i = begin + skipHeaderSize; i <= end; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<String> rowList = new ArrayList<>();
            for (int m = pos; m < pos + colLen; m++) {
                Cell cell = row.getCell(m);
                if (cell != null) {
                    rowList.add(cell.toString());
                } else {
                    rowList.add(new String(""));
                }
            }
            rowValueMap.put(i, rowList);
        }
        return rowValueMap;
    }

    public static Integer getRowNums(InputStream is, String filenameWithSuffix, int sheetIndex) throws IOException {
        //逻辑第一页:1;数组第一页:0
        sheetIndex--;
        if (StringUtils.isEmpty(filenameWithSuffix) || is == null) {
            throw new ExcelException("filenameWithSuffix or is cannot be null");
        }
        List<String> fileNameStrs = Splitter.on(".").splitToList(filenameWithSuffix);
        String suffix = fileNameStrs.get(1);

        Workbook workbook = null;
        if ("xls".equalsIgnoreCase(suffix)) {
            workbook = new HSSFWorkbook(is);
        } else if ("xlsx".equalsIgnoreCase(suffix)) {
            workbook = new XSSFWorkbook(is);
//            workbook = new SXSSFWorkbook(new XSSFWorkbook(is), cacheSize);
        } else {
            throw new ExcelException("suffix only support xls and xlsx");
        }
        if (workbook == null) {
            return null;
        }

        Sheet sheet = null;
        try {
            sheet = workbook.getSheetAt(sheetIndex);
        } catch (Exception e) {
            throw new ExcelException("excel sheet is not have " + (sheetIndex + 1) + " pages!", e);
        }
//        int begin = sheet.getFirstRowNum();
//        int end = sheet.getLastRowNum();
        //Sheet类有bug，获取getLastRowNum()始终为 0，建议获取行数改用物理行数
        int rowNums = sheet.getPhysicalNumberOfRows();
//        return (end - begin + 1);
        return rowNums;

    }


}
