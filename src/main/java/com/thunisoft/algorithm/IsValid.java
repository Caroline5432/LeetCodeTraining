package com.thunisoft.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangliujie
 * @Description
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * @date 2020/1/9.
 */
public class IsValid {

    public static boolean isValid(String s) {
        if (s == null || s.equals("")) {
            return true;
        }

        int length = s.length();

        if (length % 2 == 1) {
            return false;
        }

        Map<String, String> convertMap = new HashMap<>();
        convertMap.put("(", ")");
        convertMap.put("[", "]");
        convertMap.put("{", "}");

        for (int i = 0; i < length; i++) {
            
        }
        return true;
    }

    public static void main(String[] args) {
        // System.out.println(isValid("()[]{}"));

    }

}
