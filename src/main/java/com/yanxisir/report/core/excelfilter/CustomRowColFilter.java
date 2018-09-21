package com.yanxisir.report.core.excelfilter;

import com.yanxisir.report.domain.excel.Cell;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class CustomRowColFilter implements CellFilter {

    private List<Cell> cells;

    public CustomRowColFilter() {
    }

    public CustomRowColFilter(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public boolean execute(List<Cell> list, String field, Object value) {
        return false;
    }
}
