package com.yanxisir.report.utils;

import com.yanxisir.report.core.exception.ObjectTypeException;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class ObjectUtils {


    /**
     * 自定义对象list转Object list
     *
     * @param obj 自定义对象list转Object对象list
     * @return Object对象list
     */
    public static List<Object> customListToObjectList(Object obj) {
        List<Object> objects = new ArrayList<Object>();
        if (!(obj instanceof List)) {
            throw new ObjectTypeException("obj is not list");
        }
        List<Object> objList = (List) obj;
        for (Object o : objList) {
            objects.add(o);
        }
        return objects;
    }
}
