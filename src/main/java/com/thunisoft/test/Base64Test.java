package com.thunisoft.test;

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

}
