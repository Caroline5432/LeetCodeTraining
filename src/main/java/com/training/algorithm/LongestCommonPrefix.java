package com.training.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/10/15.
 */
public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0 ) {
            return "";
        }
        String ans = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() < ans.length()) {
                ans = ans.substring(0, strs[i].length());
            }
            for (int j = 0; j < ans.length(); j++) {
                if(strs[i].charAt(j) != ans.charAt(j)) {
                    ans = ans.substring(0, j);
                    break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] strs = {};
        System.out.println(longestCommonPrefix(null));
    }

}
