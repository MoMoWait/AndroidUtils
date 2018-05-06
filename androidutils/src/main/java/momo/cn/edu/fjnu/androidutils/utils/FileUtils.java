package momo.cn.edu.fjnu.androidutils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

/**
 * 文件操作工具
 * @author gaofei
 */

public class FileUtils {
    private FileUtils(){

    }

    /**
     * 获取文件的MD5值
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        MessageDigest digest;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
