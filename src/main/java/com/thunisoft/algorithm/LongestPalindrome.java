package com.thunisoft.algorithm;

import com.alibaba.druid.sql.visitor.functions.Char;

import javax.sound.midi.Soundbank;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/16.
 */
public class LongestPalindrome {

    //最长公共子串 时间复杂度 O(n^2) 空间复杂度 O(n^2)
    public static String longestPalindrome1(String s) {
        if (s.equals("")) {
            return "";
        }
        String origin = s;
        String reverse = new StringBuffer(s).reverse().toString();
        int length = s.length();
        int[][] arr = new int[length][length];
        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if(origin.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i-1][j-1] + 1;
                    }
                }
                if (arr[i][j] > maxLen) {
                    //判断下标是否对应
                    if ((length - j - 1) == (i - arr[i][j] + 1)) {
                        maxLen = arr[i][j];
                        maxEnd = i;
                    }
                }
            }
        }

        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    //优化最长公共子串 时间复杂度 O(n^2) 空间复杂度 O(n)
    public static String longestPalindrome2(String s) {
        if (s.equals("")) {
            return "";
        }
        String origin = s;
        String reverse = new StringBuffer(s).reverse().toString();
        int length = s.length();
        int[] arr = new int[length];
        int maxLen = 0;
        int maxEnd = 0;
        for (int i = 0; i < length; i++) {
            for (int j = length - 1; j >= 0; j--) {
                if(origin.charAt(i) == reverse.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[j] = 1;
                    } else {
                        arr[j] = arr[j-1] + 1;
                    }
                }
                if (arr[j] > maxLen) {
                    //判断下标是否对应
                    if ((length - j - 1) == (i - arr[j] + 1)) {
                        maxLen = arr[j];
                        maxEnd = i;
                    }
                }
            }
        }

        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    //暴力求解 O(n^3)
    public static String longestPalindrome3(String s) {
        String ans = "";
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j <= len; j++) {
                String test = s.substring(i, j);
                if(isPalindrome(test) && test.length() > max) {
                    ans = s.substring(i, j);
                    max = Math.max(max, ans.length());
                }
            }
        }
        return ans;
    }
    public static boolean isPalindrome(String s) {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if(s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    //中心扩展 2n-1个中心 时间复杂度 O(n^2) 空间复杂度 O(n)
    public static String longestPalindrome4(String s) {
        if (s == null || s.equals("")) {
            return "";
        }
        int start = 0, end =0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i+1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end+1);
    }

    public static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R-L-1;
    }

    //Manacher's Algorithm 马拉车算法
    public static String longestPalindrome5(String s) {
        String ns = preProcess(s);
        int n = ns.length();
        int[] arr = new int[n];
        int C = 0, R = 0;
        for (int i = 1; i < n-1; i ++) {
            int i_mirror = 2 * C - i;
            if (R > i) {
                arr[i] = Math.min(R - i, arr[i_mirror]);
            } else {
                arr[i] = 0;
            }
            while (ns.charAt(i + 1 + arr[i]) == ns.charAt(i - 1 - arr[i])) {
                arr[i] ++;
            }
            if (i + arr[i] > R) {
                C = i;
                R = i + arr[i];
            }

        }
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n-1; i++) {
            if (arr[i] > maxLen) {
                maxLen = arr[i];
                centerIndex = i;
            }
        }

        int start = (centerIndex - maxLen) / 2;
        return s.substring(start, start + maxLen);
    }
    public static String preProcess(String s) {
        if (s == null || s.equals("")) {
            return "^$";
        }
        String ret = "^";
        for (int i = 0; i < s.length(); i++) {
            ret += "#" + s.charAt(i);
        }
        ret += "#$";
        return ret;
    }

    public static void main(String[] args) {
        String s = "babad";
        System.out.println(longestPalindrome4(s));
    }

}
