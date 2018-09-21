package com.yanxisir.report.service;

import com.yanxisir.report.BaseExcel;
import com.yanxisir.report.core.excelfilter.CellFilter;
import com.yanxisir.report.domain.excel.Cell;
import com.yanxisir.report.utils.TypeUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: yanxi
 * date : 16/12/12
 */
public class SXSSFExcelService extends BaseExcel {

    private SXSSFWorkbook wb;
    private OutputStream os;
    private Sheet sh;
    private Map<String, Sheet> shMap = new HashMap<>();
    private int rowAccessWindowSize = -1;
    private String sheetName = "";

    private int rowNum = 0;
    private Map<String, Integer> shRowNumMap = new HashMap<>();

    /**
     * 带参构造函数
     *
     * @param rowAccessWindowSize 缓存中允许最大数据行数
     * @param sheetName           sheet页名
     * @param os                  内容输出流,写入地方
     */
    public SXSSFExcelService(int rowAccessWindowSize, String sheetName, OutputStream os) {
        this.rowAccessWindowSize = rowAccessWindowSize;
        this.sheetName = sheetName;
        this.wb = new SXSSFWorkbook(rowAccessWindowSize);
        this.sh = wb.createSheet(sheetName);
        this.os = os;
    }

    public SXSSFExcelService(int rowAccessWindowSize, List<String> sheetNameList, OutputStream os) {
        this.rowAccessWindowSize = rowAccessWindowSize;
        this.wb = new SXSSFWorkbook(rowAccessWindowSize);
        this.os = os;
        for (String sheetName : sheetNameList) {
            this.shMap.put(sheetName, wb.createSheet(sheetName));
            this.shRowNumMap.put(sheetName, 0);
        }
    }

    /**
     * 写某一行记录
     *
     * @param obj     要写的对象(可以是list)
     * @param filters 过滤器,用来自定义要写入数据策略
     * @return 是否写成功
     * @throws Exception 写记录异常
     */
    public boolean createRow(Object obj, CellFilter... filters) throws Exception {
        List<Cell> cells = objToList(obj, filters);
        int length = cells.size();
        Row row = sh.createRow(rowNum);
        for (int cellNum = 0; cellNum < length; cellNum++) {
            Cell cell = cells.get(cellNum);
            org.apache.poi.ss.usermodel.Cell poiCell = row.createCell(cellNum);
            Object value = cell.getValue();
            if (value == null) {
                poiCell.setCellValue("");
            } else if (TypeUtils.isDouble(value)) {
                poiCell.setCellValue((Double) value);
            } else if (TypeUtils.isFloat(value)) {
                poiCell.setCellValue((Float) value);
            } else if (TypeUtils.isInteger(value)) {
                poiCell.setCellValue((Integer) value);
            } else if (TypeUtils.isLong(value)) {
                poiCell.setCellValue((Long) value);
            } else if (TypeUtils.isShort(value)) {
                poiCell.setCellValue((Short) value);
            } else {
                poiCell.setCellValue(String.valueOf(value));
            }
        }
        rowNum++;
        return true;
    }

    public boolean createRow(String sheetName, Object obj, CellFilter... filters) throws Exception {

        List<Cell> cells = objToList(obj, filters);
        int length = cells.size();
        Integer rowLen = shRowNumMap.get(sheetName);
        Row row = shMap.get(sheetName).createRow(rowLen);
        for (int cellNum = 0; cellNum < length; cellNum++) {
            Cell cell = cells.get(cellNum);
            org.apache.poi.ss.usermodel.Cell poiCell = row.createCell(cellNum);
            Object value = cell.getValue();
            if (value == null) {
                poiCell.setCellValue("");
            } else if (TypeUtils.isDouble(value)) {
                poiCell.setCellValue((Double) value);
            } else if (TypeUtils.isFloat(value)) {
                poiCell.setCellValue((Float) value);
            } else if (TypeUtils.isInteger(value)) {
                poiCell.setCellValue((Integer) value);
            } else if (TypeUtils.isLong(value)) {
                poiCell.setCellValue((Long) value);
            } else if (TypeUtils.isShort(value)) {
                poiCell.setCellValue((Short) value);
            } else {
                poiCell.setCellValue(String.valueOf(value));
            }
        }
        rowLen++;
        shRowNumMap.put(sheetName, rowLen);
        return true;
    }

    /**
     * 刷新某一行记录
     *
     * @throws IOException IO异常
     */
    public void flush() throws IOException {
        ((SXSSFSheet) sh).flushRows();
    }

    /**
     * 提交缓存里已写入的行数内容到os
     *
     * @throws IOException IO异常
     */
    public void write() throws IOException {
        wb.write(os);
    }

    /**
     * 关闭workbook句柄,操作结束得调用close方法
     *
     * @throws IOException IO异常
     */
    public void close() throws IOException {
        this.rowNum = 0;
        if (wb != null) {
            wb.dispose();
        }
        if (os != null) {
            os.close();
        }

    }

    public SXSSFWorkbook getWb() {
        return wb;
    }

    public void setWb(SXSSFWorkbook wb) {
        this.wb = wb;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public Sheet getSh() {
        return sh;
    }

    public void setSh(Sheet sh) {
        this.sh = sh;
    }

    public int getRowAccessWindowSize() {
        return rowAccessWindowSize;
    }

    public void setRowAccessWindowSize(int rowAccessWindowSize) {
        this.rowAccessWindowSize = rowAccessWindowSize;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }
}
