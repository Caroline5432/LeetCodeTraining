package com.training.algorithm.linkedList;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 *@description
 * 基于数组实现LRU
 * 1、维护一个有序数组
 * 2、若缓存中有指定数据，将原来的数据删除，整体数据右移，将数据插入头部
 * 3、若缓存中没有指定数据，则分为两种情况
 *  3.1 若缓存已满，则删除最后一个数据，整体右移，将最新数据插入到头部
 *  3.2 若缓存未满，则整体右移，将最新数据插入到头部
 *@author caroline
 *@version 1.0
 *@date 2023/12/10 15:39
 */
public class LRUByArray<T> {

    /**
     * 默认数组容量
     */
    private final static Integer DEFAULT_CAPACITY = 8;

    /**
     * 数组长度
     */
    private Integer count;

    /**
     * 数组容量
     */
    private Integer capacity;

    /**
     * 数组中的数据
     */
    private T[] value;

    private Map<T, Integer> holder;

    public LRUByArray() {
        this(DEFAULT_CAPACITY);
    }

    public LRUByArray(Integer capacity) {
        this.capacity = capacity;
        this.count = 0;
        this.value = (T[]) new Object[capacity];
        this.holder = new HashMap<>(capacity);
    }

    /**
     * @description 数组中增加数据
     * @param
     */
    private void addData(T data) {
        if (data == null) {
            throw new IllegalArgumentException("数组中不支持null");
        }

        Integer index = holder.get(data);

        // 若缓存中存在该数据，则将原来的数据删除，数组右移
        if (index != null) {
            rightShift(index);
            count--;
        } else {
            //数组右移
            rightShift(count);
        }
        value[0] = data;
        holder.put(data, 0);
        count++;
    }

    /**
     * @description 将数组右移到指定位置
     * @param
     */
    private void rightShift(Integer index) {
        if (index.equals(0)) {
            return;
        }
        // 如果缓存满了，则删除最后一个元素
        if (index.equals(capacity)) {
            index = capacity - 1;
            count--;
        }
        for (int i = index; i > 0; i--) {
            value[i] = value[i-1];
            holder.put(value[i], i);
        }
    }

    @Override
    public String toString() {
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < count; i++) {
            sb.append(value[i]);
            sb.append(" ");
       }
       return sb.toString();
    }

    public static void main(String[] args) {
        LRUByArray array = new LRUByArray<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            array.addData(sc.nextInt());
            System.out.println(array);
        }
    }

}
