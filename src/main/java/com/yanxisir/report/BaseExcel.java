package com.yanxisir.report;

import com.yanxisir.report.core.annotation.CellFormat;
import com.yanxisir.report.core.annotation.IgnoreField;
import com.yanxisir.report.core.excelfilter.CellFilter;
import com.yanxisir.report.utils.StringUtils;
import com.yanxisir.report.domain.excel.Cell;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author: yanxi
 * date : 16/12/16
 */
public class BaseExcel {

    /**
     * Object转list
     *
     * @param obj     Object对象
     * @param filters 过滤器
     * @return Cell对象列表
     * @throws Exception 转换异常
     */
    protected static List<Cell> objToList(Object obj, CellFilter... filters) throws Exception {
        List<Cell> list = new ArrayList<Cell>();
        if (obj instanceof List) {
            //obj本身是list
            List tmpList = (List) obj;
            for (Object o : tmpList) {
                if (o instanceof Cell) {
                    list.add((Cell) o);
                } else {
                    list.add(new Cell(o));
                }
            }
            return list;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method method = pd.getReadMethod();
            Object value = method.invoke(obj);

            //IgnoreField 注解
            if (field.getAnnotation(IgnoreField.class) != null) {
                continue;
            }

            boolean handled = false;
            for (CellFilter filter : filters) {
                handled = filter.execute(list, field.getName(), value);
            }
            //如果处理过,继续
            if (handled) {
                continue;
            }

            CellFormat format = field.getAnnotation(CellFormat.class);
            Cell cell;
            if ("Cell".equalsIgnoreCase(field.getType().getSimpleName())) {
                cell = (Cell) value;
            } else {
                if (value == null) {
                    value = "";
                }
                cell = new Cell(value);
            }
            if (format != null) {
                cell.setRowNum(format.row());
                cell.setColNum(format.col());
                String hyperlink = format.hyperlink();
                String hyperlinkRef = format.hyperlinkRef();
                if ((StringUtils.isEmpty(hyperlink) && StringUtils.isNotEmpty(hyperlinkRef))) {
                    Field refField = clazz.getDeclaredField(hyperlinkRef);
                    if (refField != null) {
                        PropertyDescriptor pdRef = new PropertyDescriptor(refField.getName(), clazz);
                        Object ref = pdRef.getReadMethod().invoke(obj);
                        hyperlink = String.valueOf(ref);
                    }
                }
                cell.setHyperlink(hyperlink);
            }
            list.add(cell);
        }
        return list;
    }
}
