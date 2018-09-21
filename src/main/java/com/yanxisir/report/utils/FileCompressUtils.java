package com.yanxisir.report.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * author: yanxi
 * mis: tanlianfang
 * date : 17/9/13
 */
public class FileCompressUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileCompressUtils.class);

    public static File zip(File sFile) {
        if (sFile == null || !sFile.exists()) {
            return null;
        }
        String zipPathName = sFile.getPath() + ".zip";
        String zipName = sFile.getName() + ".zip";
        int buf_size = 1024;
        byte[] buffer = new byte[buf_size];
        InputStream in = null;
        ZipOutputStream zout = null;
        File oFile = new File(zipPathName);
        try {
            in = new FileInputStream(sFile);
            zout = new ZipOutputStream(new FileOutputStream(oFile));
            zout.setLevel(9);
            ZipEntry zipEntry = new ZipEntry(sFile.getName());
            zout.putNextEntry(zipEntry);
            int len;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                zout.write(buffer, 0, len);
            }
            zout.finish();
            LOG.info(String.format("文件名:%s,压缩前:%s,压缩后:%s", zipName, FileUtils.size(sFile), FileUtils.size(oFile)));
            sFile.delete();
            return oFile;
        } catch (Exception e) {
            LOG.error("file compress error", e);
            oFile.delete();
            return sFile;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (zout != null) {
                    zout.close();
                }
            } catch (Exception e) {
                LOG.error("stream close error", e);
            }
        }

    }
}
