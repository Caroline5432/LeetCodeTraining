package com.thunisoft.algorithm;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/12/7.
 */
public class RemoveNthFromEnd {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        return null;
    }

    public static void main(String[] args) {
        String wjbs = "eyJmaWxlS2V5Ijoic3RvcmFnZTI6ZHpqei5mdHBTdG9yYWdlXzc1MF8xOnRlc3QuanBnOmF0dHJpYnV0ZXNbX2ZpbGVfbmFtZTp0ZXN0LmpwZyxfZmlsZV9zaXplOjQ1Njk5MixfcGF0aDpob21lL253ZnMvcGh5X3Rlc3QvXSIsImZ5aWQiOjc1MH0=";
        System.out.println(isBase64Encode(wjbs));
    }

    public static boolean isBase64Encode(String content) {
        String pattern = "^[a-zA-Z0-9/+]*={0,2}$";
        return Pattern.matches(pattern, content);
    }

}
