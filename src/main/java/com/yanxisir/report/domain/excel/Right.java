package com.yanxisir.report.domain.excel;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class Right {
    private Top top;
    private Content content;

    public Right() {
    }

    public Right(Top top, Content content) {
        this.top = top;
        this.content = content;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
