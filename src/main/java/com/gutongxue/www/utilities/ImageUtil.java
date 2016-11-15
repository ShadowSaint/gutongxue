package com.gutongxue.www.utilities;

import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Created by Shadow on 2016/11/9.
 */
public class ImageUtil {
    private static final String IMG_DOWNLOAD_DIR =  File.separator + "gutongxue"+File.separator+"images"+ File.separator ;

    public static String downloadFromUrl(String url) {
        try {
            String fileName=TimeUtil.getTodayByFormat("yyyyMMddHHmmssSSS")+".gif";
            URL httpurl = new URL(url);
            File f = new File(IMG_DOWNLOAD_DIR + fileName);
            FileUtils.copyURLToFile(httpurl, f);
            return IMG_DOWNLOAD_DIR + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 加密
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
