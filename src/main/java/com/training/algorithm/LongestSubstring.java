package com.training.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/6.
 */
public class LongestSubstring {

    public static int lengthOfLongestSubstring (String s) {

        int n = s.length();
        int ans = 0;
        //时间复杂度 O(n^3)
        /*for (int i = 0;i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if(allUnique(s, i, j)) ans = Math.max(ans, j - i);
            }
        }*/
        //时间复杂度 O(2n),每个字符将被 i 和 j 访问两次
       /* Set<Character> set = new HashSet<>();
        int i = 0, j = 0;
        while (i < n && j < n) {
            if(!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }*/
        //时间复杂度 O(n),如果 s[j]在 [i, j)范围内有与 j'重复的字符，我们不需要逐渐增加 i。我们可以直接跳过 [i，j']范围内的所有元素，并将 i变为 j'+ 1
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0, j = 0; j < n; j++) {
            if(map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    public static boolean allUnique (String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if(set.contains(ch)) {
                return false;
            }
            set.add(ch);
        }
        return true;
    }

    public static void main(String[] args) {
        int ans = lengthOfLongestSubstring("abcdcef");
        System.out.println(ans);
    }

}
