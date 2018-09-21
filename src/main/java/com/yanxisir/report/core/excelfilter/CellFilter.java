package com.yanxisir.report.core.excelfilter;

import com.yanxisir.report.domain.excel.Cell;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public interface CellFilter {

    boolean execute(List<Cell> list, String field, Object value);
}
