package com.training.test;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author zhangliujie
 * @Description
 * @date 2020/1/9.
 */
public class Base64Test {

    public static boolean isBase64Encode(String content) {
        String pattern = "^[a-zA-Z0-9/+]*={0,2}$";
        return Pattern.matches(pattern, content);
    }

    private static void wsParse() {

        String val5 = "";

        byte[] bytes = Base64.decodeBase64(val5);
        try {
            OutputStream out = new FileOutputStream("C:\\Users\\huayu\\Desktop\\aaa.doc");
            InputStream is = new ByteArrayInputStream(bytes);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            is.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(ByteConvertUtil.byteToString(bytes, "utf-8"));
    }

}
