package com.yanxisir.report.utils;

import java.util.Collection;
import java.util.Map;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }

    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty();
    }

    public static boolean isNotEmpty(Map m) {
        return !isEmpty(m);
    }

    public static int size(Collection c) {
        return c != null ? c.size() : 0;
    }

    public static int size(Map m) {
        return m != null ? m.size() : 0;
    }
}
