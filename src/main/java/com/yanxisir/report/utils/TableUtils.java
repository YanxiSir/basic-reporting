package com.yanxisir.report.utils;

import com.yanxisir.report.domain.excel.Left;
import com.yanxisir.report.domain.excel.Right;
import com.yanxisir.report.domain.excel.Table;
import com.yanxisir.report.domain.excel.TerseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yanxi
 * date : 16/12/9
 */
public class TableUtils {

    /**
     * terseTable转复杂table
     *
     * @param terseTable 简单table对象
     * @return 复杂table对象
     */
    public static Table terseTableToTable(TerseTable terseTable) {
        Table table = new Table();
        List<Left> leftList = new ArrayList<Left>();
        List<Right> rightList = new ArrayList<Right>();
        if (terseTable.getLeft() != null)
            leftList.add(terseTable.getLeft());
        if (terseTable.getRight() != null)
            rightList.add(terseTable.getRight());
        table.setTitle(terseTable.getTitle());
        table.setLefts(leftList);
        table.setRights(rightList);
        return table;
    }
}
