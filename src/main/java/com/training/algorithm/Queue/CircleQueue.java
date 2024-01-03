package com.training.algorithm.Queue;

/**
 * @ClassName ArrayQueue
 * @Description 数组实现循环队列
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/3 13:06
 */
public class CircleQueue {

    private String[] items;

    private int n = 0;

    /**
     * 队头下标
     */
    private int head = 0;

    /**
     * 队尾下标
     */
    private int tail = 0;

    public CircleQueue(int capacity) {
        this.items = new String[capacity];
        this.n = capacity;
    }

    /**
     * @Description 入队
     * @Param 元素
     */
    public boolean enqueue (String item) {
        if ((tail + 1) % n == head) {
            // 队满
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * @Description 出队
     */
    public String dequeuq () {
        // 队列中元素为空
        if (head == tail) {
            return null;
        }
        String result = items[head];
        head = (head + 1) % n;
        return result;
    }
}
