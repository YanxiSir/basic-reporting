package com.yanxisir.report;


import com.yanxisir.report.core.excelfilter.CellFilter;
import com.yanxisir.report.core.exception.ExcelException;
import com.yanxisir.report.domain.excel.Left;
import com.yanxisir.report.domain.excel.Right;
import com.yanxisir.report.domain.excel.Top;
import com.yanxisir.report.utils.ExcelUtils;
import com.yanxisir.report.utils.StringUtils;
import com.yanxisir.report.utils.TableUtils;
import com.yanxisir.report.domain.excel.Cell;
import com.yanxisir.report.domain.excel.Content;
import com.yanxisir.report.domain.excel.Table;
import com.yanxisir.report.domain.excel.TerseTable;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.WritableWorkbookImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
@Slf4j
public class Excel extends BaseExcel {


    private static final int COLUMN_WIDTH = 14;


    /**
     * 下载excel,有 left+top+content
     *
     * @param left    表格左部分
     * @param top     表格头部
     * @param content 表格内容
     * @param title   sheet页名
     * @param os      输出流os
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelSimple(Left left, Top top, Content content, String title, OutputStream os, CellFilter... filters) throws IOException, ExcelException {
        Right right = new Right();
        right.setTop(top);
        right.setContent(content);
        TerseTable terseTable = new TerseTable(title, left, right);
        createExcelTerseTable(terseTable, os, filters);
    }

    /**
     * 下载excel,有 left+top+content
     *
     * @param left    表格左部分
     * @param top     表格头部
     * @param content 表格内容
     * @param title   sheet页名
     * @param file    直接输出到文件file
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelSimple(Left left, Top top, Content content, String title, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelSimple(left, top, content, title, fos, filters);
    }

    /**
     * 下载excel,只有右边content
     *
     * @param content 表格内容
     * @param os      输出流os
     * @param title   sheet页名
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelOnlyContent(Content content, OutputStream os, String title, CellFilter... filters) throws IOException, ExcelException {
        Right right = new Right();
        right.setContent(content);
        createExcelOnlyRight(right, title, os, filters);
    }

    /**
     * 下载excel,只有右边content
     *
     * @param content 表格内容
     * @param file    直接输出到文件file
     * @param title   sheet页名
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelOnlyContent(Content content, File file, String title, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelOnlyContent(content, fos, title, filters);
    }

    /**
     * 下载excel,只有右边部分
     *
     * @param right   top+content
     * @param title   sheet页名
     * @param os      输出流os
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelOnlyRight(Right right, String title, OutputStream os, CellFilter... filters) throws IOException, ExcelException {
        TerseTable terseTable = new TerseTable();
        terseTable.setTitle(title);
        terseTable.setRight(right);
        createExcelTerseTable(terseTable, os, filters);
    }

    /**
     * 下载excel,只有右边部分
     *
     * @param right   top+content
     * @param title   sheet页名
     * @param file    直接输出到文件file
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelOnlyRight(Right right, String title, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelOnlyRight(right, title, fos, filters);
    }


    /**
     * 构造excel,简洁table model
     *
     * @param terseTable 一个(left+top+content)
     * @param os         输出流os
     * @param filters    单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelTerseTable(TerseTable terseTable, OutputStream os, CellFilter... filters) throws IOException, ExcelException {
        createExcelTable(TableUtils.terseTableToTable(terseTable), os, filters);
    }

    /**
     * 构造excel,简洁table model
     *
     * @param terseTable 一个(left+top+content)
     * @param file       直接输出到文件file
     * @param filters    单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelTerseTable(TerseTable terseTable, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelTerseTable(terseTable, fos, filters);
    }

    /**
     * 构造excel文件,复杂table model
     *
     * @param table   负责table(多个left+多个top+多个content)
     * @param os      输出流os
     * @param filters 单元格过滤器
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelTable(Table table, OutputStream os, CellFilter... filters) throws ExcelException {
        WritableWorkbook workbook = null;
        try {
            workbook = new WritableWorkbookImpl(os, true, new WorkbookSettings());

            WritableSheet sheet = workbook.createSheet(table.getTitle(), 0);
            writeSingleSheet(table, sheet, filters);
            workbook.write();
        } catch (Exception e) {
            log.error("[basic-reporting excel write error]", e);
            throw new ExcelException("create reporting error", e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                log.error("[basic-reporting excel  workbook close error]", e);
            }
        }
    }

    /**
     * 构造excel文件,复杂table model
     *
     * @param table   负责table(多个left+多个top+多个content)
     * @param file    直接输出到文件file
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelTable(Table table, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelTable(table, fos, filters);
    }

    /**
     * 构造excel文件,multi简单table,多个sheet页
     *
     * @param terseTables 简单table
     * @param os          输出流os
     * @param filters     单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelMultiTerseTable(List<TerseTable> terseTables, OutputStream os, CellFilter... filters) throws IOException, ExcelException {
        List<Table> tables = new ArrayList<Table>();
        for (TerseTable terseTable : terseTables) {
            tables.add(TableUtils.terseTableToTable(terseTable));
        }
        createExcelMultiTable(tables, os, filters);
    }

    /**
     * 构造excel文件,multi简单table,多个sheet页
     *
     * @param terseTables 简单table
     * @param file        直接输出到文件file
     * @param filters     单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelMultiTerseTable(List<TerseTable> terseTables, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelMultiTerseTable(terseTables, fos, filters);
    }

    /**
     * 构造excel文件,multi复杂table,多个sheet页
     *
     * @param tables  负责table
     * @param os      输出流os
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelMultiTable(List<Table> tables, OutputStream os, CellFilter... filters) throws IOException, ExcelException {
        WritableWorkbook workbook = null;
        try {
            workbook = new WritableWorkbookImpl(os, true, new WorkbookSettings());

            int tableSize = tables.size();
            for (int i = tableSize - 1; i >= 0; i--) {
                WritableSheet sheet = workbook.createSheet(tables.get(i).getTitle(), i);
                writeSingleSheet(tables.get(i), sheet, filters);
            }
            workbook.write();
        } catch (Exception e) {
            log.error("[basic-reporting excel(multi) write error]", e);
            throw new ExcelException("create reporting(multi) error", e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                log.error("[basic-reporting excel  workbook(multi) close error]", e);
            }
        }
    }

    /**
     * 构造excel文件,multi复杂table,多个sheet页
     *
     * @param tables  负责table
     * @param file    直接输出到文件file
     * @param filters 单元格过滤器
     * @throws IOException    IO异常
     * @throws ExcelException Excel生成异常
     */
    public static void createExcelMultiTable(List<Table> tables, File file, CellFilter... filters) throws IOException, ExcelException {
        FileOutputStream fos = new FileOutputStream(file);
        createExcelMultiTable(tables, fos, filters);
    }

    /**
     * =============================== private method ====================================
     */

    /**
     * 绘制表格,画单个sheet页
     *
     * @param table   复杂table
     * @param sheet   单个sheet页
     * @param filters 单元格过滤器
     * @throws Exception Exception
     */
    private static void writeSingleSheet(Table table, WritableSheet sheet, CellFilter... filters) throws Exception {

        sheet.getSettings().setDefaultColumnWidth(COLUMN_WIDTH);

        int leftColLen = 0;
        //左菜单
        if (CollectionUtils.isNotEmpty(table.getLefts())) {

            for (int col = 0; col < table.getLefts().size(); col++) {
                Left left = table.getLefts().get(col);
                leftColLen = writeLeft(sheet, left, leftColLen, col, filters);
            }

        }
        int rightLen = 0;
        int rightMaxColLen = 0;

        //右main(topmenu+content)
        if (CollectionUtils.isNotEmpty(table.getRights())) {
            rightLen = table.getRights().size();
            for (int tmpRigSize = 0; tmpRigSize < rightLen; tmpRigSize++) {
                Right right = table.getRights().get(tmpRigSize);
                rightMaxColLen = writeRight(sheet, right, leftColLen, tmpRigSize, rightMaxColLen, filters);
            }
        }

    }

    /**
     * 画左菜单栏
     *
     * @param sheet      sheet对象
     * @param left       left bean
     * @param leftColLen left offset
     * @param curPos     当前第几个left
     * @param filters    单元格过滤器
     * @return 画完后left offset
     * @throws WriteException 写异常
     * @throws Exception      异常
     */
    private static int writeLeft(WritableSheet sheet, Left left, int leftColLen, int curPos, CellFilter... filters) throws WriteException, Exception {
        WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
        WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10);
        WritableCellFormat formatCenter = ExcelUtils.cellFormatOnTextCenter(boldFont);
        //左边栏每一列
        int curRow = 0;//记录当前画到哪行了
        int curCol = leftColLen;
        int curColMaxLen = 1;
        List<Cell> leftList = objToList(left.getLeftCol(), filters);
        for (int row = 0; row < leftList.size(); row++) {
            Cell cell = leftList.get(row);
            int rowLen = cell.getRowNum();
            int colLen = cell.getColNum();
            String text = "";
            if (cell.getValue() != null) {
                text = String.valueOf(cell.getValue());
            }
            if (curColMaxLen < colLen) {
                curColMaxLen = colLen;
            }
            Label label = new Label(curCol, curRow, text, formatCenter);
            sheet.addCell(label);
            if (rowLen > 1 || colLen > 1) {
                sheet.mergeCells(curCol, curRow, curCol + colLen - 1, curRow + rowLen - 1);
            }
            curRow += rowLen;
            curCol = leftColLen;
        }
        return leftColLen + curColMaxLen;
    }

    /**
     * 画右边栏
     *
     * @param sheet          sheet对象
     * @param right          right bean
     * @param leftLen        left offset
     * @param curPos         当前第几个右边
     * @param rightMaxColLen 当前right最大列占比(多少列),右边最长列
     * @param filters        单元格过滤器
     * @return 画完后top offset
     * @throws WriteException 写异常
     * @throws Exception      异常
     */
    private static int writeRight(WritableSheet sheet, Right right, int leftLen, int curPos, int rightMaxColLen, CellFilter... filters) throws WriteException, Exception {
        WritableFont boldFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
        WritableFont normalFont = new WritableFont(WritableFont.TIMES, 10);
        WritableCellFormat formatCenter = ExcelUtils.cellFormatOnTextCenter(boldFont);
        WritableCellFormat formatLeft = ExcelUtils.cellFormatOnTextLeft(normalFont);
        //记录top所占行数
        int topLen = 0;
        int curRow = 0;
        int curCol = leftLen;

        Top top = right.getTop();
        //top
        if (top != null && CollectionUtils.isNotEmpty(top.getTopRowList())) {

            for (int row = 0; row < top.getTopRowList().size(); row++) {
                List<Cell> rightTopList = objToList(top.getTopRowList().get(row), filters);
                int curRowLen = 1;
                int curColLen = 1;
                //偏移量
                curCol += curPos * rightMaxColLen;//记录当前画到哪列了
                for (int col = 0; col < rightTopList.size(); col++) {
                    Cell cell = rightTopList.get(col);
                    int rowLen = cell.getRowNum();
                    int colLen = cell.getColNum();
                    String text = "";
                    if (cell.getValue() != null) {
                        text = String.valueOf(cell.getValue());
                    }
                    if (rowLen > curRowLen) {
                        curRowLen = rowLen;
                    }
                    if (colLen > curColLen) {
                        curColLen = colLen;
                    }
                    Label label = new Label(curCol, curRow, text, formatCenter);
                    sheet.addCell(label);
                    if (rowLen > 1 || colLen > 1) {
                        sheet.mergeCells(curCol, curRow, curCol + colLen - 1, curRow + rowLen - 1);
                    }
                    curCol += colLen;
                }
                rightMaxColLen = curCol - (leftLen + curPos * rightMaxColLen);
                curRow += curRowLen;
                curCol = leftLen;
                topLen += curRowLen;
            }
        }
        Content content = right.getContent();
        //content
        if (CollectionUtils.isNotEmpty(content.getContentRowList())) {
            curRow = topLen;
            curCol = leftLen;
            for (int row = 0; row < content.getContentRowList().size(); row++) {
                List<Cell> rightContentList = objToList(content.getContentRowList().get(row), filters);
                curCol += curPos * rightMaxColLen;//记录当前画到哪列了
                for (int col = 0; col < rightContentList.size(); col++) {
                    Cell cell = rightContentList.get(col);
                    String text = "";
                    if (cell.getValue() != null) {
                        text = String.valueOf(cell.getValue());
                    }
                    String linkUrl = cell.getHyperlink();
                    if (StringUtils.isNotEmpty(linkUrl)) {
                        WritableHyperlink hyperlink = new WritableHyperlink(curCol, curRow, new URL(linkUrl));
                        sheet.addHyperlink(hyperlink);
                    }
                    Label label = new Label(curCol, curRow, text, formatLeft);
                    sheet.addCell(label);

                    curCol++;
                }
                curRow++;
                curCol = leftLen;
            }
        }
        return rightMaxColLen;
    }
}
