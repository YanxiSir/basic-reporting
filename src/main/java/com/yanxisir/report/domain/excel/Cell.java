package com.yanxisir.report.domain.excel;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class Cell {
    private Object value;
    private int colNum = 1;
    private int rowNum = 1;
    private String hyperlink = "";

    public Cell() {
    }

    public Cell(Object value) {
        this.value = value;
    }

    public Cell(Object value, int colNum, int rowNum) {
        this.value = value;
        this.colNum = colNum;
        this.rowNum = rowNum;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

}
