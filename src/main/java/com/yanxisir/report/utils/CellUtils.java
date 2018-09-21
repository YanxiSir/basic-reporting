package com.yanxisir.report.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.DecimalFormat;

/**
 * author: yanxi
 * date : 17/3/10
 */
public class CellUtils {

    public static String getCellValueString(Cell cell) {
        String value = "";
        if (cell == null) {
            return value;
        }
        if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            value = df.format(cell.getNumericCellValue());
        } else {
            value = cell.toString();
        }
        return value;
    }
}
