package com.training.algorithm.stack;

/**
 * @ClassName ArrayStack
 * @Description 基于数组实现顺序栈
 * @Author caroline
 * @Version 1.0
 * @Date 2023/12/30 15:09
 */
public class ArrayStack {

    /**
     * 栈中元素
     */
    private String[] items;

    /**
     * 元素个数
     */
    private int count;

    /**
     * 数组大小
     */
    private int n;

    public ArrayStack(int n) {
        this.items = new String[n];
        this.count = 0;
        this.n = n;
    }

    /**
     * @Description 入栈操作
     */
    public boolean push (String item) {
        // 数组空间不够了，插入失败
        if (count == n) {
            return false;
        }

        items[count] = item;
        count++;
        return true;
    }
    
    /**
     * @Description 出栈操作
     */
    public String pop () {
        // 栈为空，直接返回null
        if (count == 0) {
            return null;
        }
        return items[--count];
    }
}
