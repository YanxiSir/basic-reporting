package com.yanxisir.report.utils;

/**
 * author: yanxi
 * date : 16/12/6
 */
public class StringUtils {

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
}
