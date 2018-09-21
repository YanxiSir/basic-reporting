package com.yanxisir.report.domain.excel;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/6
 */
public class Content {
    private List<Object> contentRowList;

    public Content() {
    }

    public Content(List<Object> contentRowList) {
        this.contentRowList = contentRowList;
    }

    public List<Object> getContentRowList() {
        return contentRowList;
    }

    public void setContentRowList(List<Object> contentRowList) {
        this.contentRowList = contentRowList;
    }
}
