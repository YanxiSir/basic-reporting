package com.yanxisir.report.domain.excel;

import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class Table {
    private String title;
    private List<Left> lefts;
    private List<Right> rights;

    public Table() {
    }

    public Table(List<Left> lefts, List<Right> rights) {
        this.lefts = lefts;
        this.rights = rights;
    }

    public Table(String title, List<Left> lefts, List<Right> rights) {
        this.title = title;
        this.lefts = lefts;
        this.rights = rights;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Left> getLefts() {
        return lefts;
    }

    public void setLefts(List<Left> lefts) {
        this.lefts = lefts;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }
}
