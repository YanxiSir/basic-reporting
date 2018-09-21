package com.yanxisir.report.domain.excel;

/**
 * author: yanxi
 * date : 16/12/9
 */
public class TerseTable {
    private String title;
    private Left left;
    private Right right;

    public TerseTable() {
    }

    public TerseTable(String title, Right right) {
        this.title = title;
        this.right = right;
    }

    public TerseTable(String title, Left left, Right right) {
        this.title = title;
        this.left = left;
        this.right = right;
    }

    public TerseTable(Left left, Right right) {
        this.left = left;
        this.right = right;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Left getLeft() {
        return left;
    }

    public void setLeft(Left left) {
        this.left = left;
    }

    public Right getRight() {
        return right;
    }

    public void setRight(Right right) {
        this.right = right;
    }
}
