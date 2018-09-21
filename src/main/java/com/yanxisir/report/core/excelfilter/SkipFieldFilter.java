package com.yanxisir.report.core.excelfilter;

import com.yanxisir.report.domain.excel.Cell;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class SkipFieldFilter implements CellFilter {

    private List<String> fieldList;

    public SkipFieldFilter() {
    }

    public SkipFieldFilter(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    @Override
    public boolean execute(List<Cell> list, String field, Object value) {
        if (CollectionUtils.isNotEmpty(fieldList)) {
            if (fieldList.contains(field)) return true;
        }
        return false;
    }
}
