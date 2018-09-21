package com.yanxisir.report.domain.excel;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/6
 */
public class Top {
    private List<Object> topRowList;

    public Top() {
    }

    public Top(List<Object> topRowList) {
        this.topRowList = topRowList;
    }

    public List<Object> getTopRowList() {
        return topRowList;
    }

    public void setTopRowList(List<Object> topRowList) {
        this.topRowList = topRowList;
    }
}
