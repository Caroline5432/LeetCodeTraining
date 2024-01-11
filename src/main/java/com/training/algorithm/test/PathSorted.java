package com.training.algorithm.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 一个文件（ url_path_statistics.txt） 中存储了本站点下各路径被访问的次数
 *
 */
public class PathSorted {

    /**
     * 1. 请编程找出被访问次数最多的10个路径
     * 思路：
     *  1.1 读取文件中的每一行记录，然后使用一个HashMap存储每个路径被访问的次数
     *  1.2 使用Java 8的Stream API，对HashMap的访问次数进行降序排序
     *  1.3 取前十个路径作为结果
     */
    public static void main(String[] args) {
        String filePath = "/Users/zhangliujie/Downloads/url_path_statistics.txt";
        Map<String, Integer> pathCountMap = new HashMap<>(10);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String path = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    if (pathCountMap.size() < 10) {
                        pathCountMap.put(path, pathCountMap.getOrDefault(path, 0) + count);
                    } else {
                        replaceMinValue (pathCountMap, path, count);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("被访问次数最多的10个路径：");
        Iterator<Map.Entry<String, Integer>> iterator = pathCountMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public static void replaceMinValue (Map<String, Integer> pathCountMap, String path, Integer count) {
        Integer minValue = 0;
        String minPath = "";

        Iterator<Map.Entry<String, Integer>> iterator = pathCountMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            Integer currentValue = entry.getValue();
            String currentPath = entry.getKey();
            if (currentValue < minValue || minValue == 0) {
                minValue = currentValue;
                minPath = currentPath;
            }
        }

        if (count > minValue) {
            pathCountMap.remove(minPath);
            pathCountMap.put(path, count);
        }
    }
}
