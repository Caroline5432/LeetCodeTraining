package com.thunisoft.algorithm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author zhangliujie
 * @Description
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * @date 2020/1/28.
 */
public class IsValid {

    private static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() % 2 == 1) {
            return false;
        }
        HashMap<Character, Character> mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put(']', '[');
        mappings.put('}', '{');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //If the current character is a closing bracket
            if (mappings.containsKey(c)) {
                //Get the top element of the stack. If the stack is empty,set a dummy value of "#"
                char topElement = stack.empty() ? '#' : stack.pop();
                //If the mappings for this bracket doesn't match the stack's top element , return false
                if (topElement != mappings.get(c)) {
                    return false;
                }
            } else {
                //If it was an opening bracket, push to the stack.
                stack.push(c);
            }
        }
        // If the stack still contains elements, then it is an invalid expression
        return stack.empty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("(){[}]"));
    }

}
