package com.yanxisir.report.utils;

import com.yanxisir.report.core.exception.ObjectTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: yanxi
 * date : 16/12/16
 */
public class TypeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TypeUtils.class);

    /**
     * 判断是否是基本类型
     * int, double, float, long, short, boolean, byte, char, void
     *
     * @param obj object
     * @return 是否是基本类型
     */
    public static boolean isWrapOrBasicClass(Object obj) {
        Class<?> clazz = obj.getClass();
        return obj.getClass().isPrimitive() || clazz == String.class
                || clazz == Integer.class || clazz == Double.class
                || clazz == Float.class || clazz == Long.class
                || clazz == Boolean.class || clazz == Byte.class
                || clazz == Void.class;
    }


    public static boolean isByte(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Byte.TYPE || clazz == Byte.class;
    }

    public static boolean isShort(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Short.TYPE || clazz == Short.class;
    }

    public static boolean isInteger(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Integer.TYPE || clazz == Integer.class;
    }

    public static boolean isLong(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Long.TYPE || clazz == Long.class;
    }

    public static boolean isFloat(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Float.TYPE || clazz == Float.class;
    }

    public static boolean isDouble(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Double.TYPE || clazz == Double.class;
    }

    public static boolean isBoolean(Object obj) {
        Class<?> clazz = obj.getClass();
        return clazz == Boolean.TYPE || clazz == Boolean.class;
    }


    public static final Double castToDouble(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Number) {
            return Double.valueOf(((Number) obj).doubleValue());
        } else if (obj instanceof String) {
            String strVal = obj.toString();
            return strVal.length() == 0 ? null : Double.valueOf(Double.parseDouble(strVal));
        } else {
            throw new ObjectTypeException("can not cast to double, value : " + obj);
        }
    }
}
