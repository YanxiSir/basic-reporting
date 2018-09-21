package com.yanxisir.report.utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * author: yanxi
 * date : 16/12/5
 */
public class FileUtils {

    /**
     * filePath+fileName=File
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return file对象
     * @throws IOException IO异常
     */
    public static File getFile(String filePath, String fileName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file1 = new File(filePath + fileName);
        if (!file1.exists() && !file1.isDirectory()) {
            file1.createNewFile();
        }
        return file1;
    }

    public static boolean delFile(File file) {
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    public static String size(File file) {
        if (file == null || !file.exists()) {
            return "";
        }
        long fileSize = file.length();
        DecimalFormat df = new DecimalFormat("#.00");
        String size = "";
        if (fileSize < 1024) {
            size = df.format((double) fileSize) + "BT";
        } else if (fileSize < 1048576) {
            size = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            size = df.format((double) fileSize / 1048576) + "MB";
        } else {
            size = df.format((double) fileSize / 1073741824) + "GB";
        }
        return size;
    }

    /**
     * 获取文件名
     *
     * @param values String数组
     * @return 拼接后的String
     */
    public static String buildFileName(String... values) {
        String name = "";
        for (String s : values) {
            name += s;
        }
        return name;
    }

    /**
     * 是否需要添加时间戳，区分文件写入，防止GC的问题。
     */
    public static String getfilepath(Object object) {
        String classpath = object.getClass().getResource("/").toString();
        String filepath = classpath.substring(5, classpath.length() - 8) + "download" + File.separator;
        return filepath;
    }
}
