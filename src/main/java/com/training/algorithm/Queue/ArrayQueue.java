package com.training.algorithm.Queue;

/**
 * @ClassName ArrayQueue
 * @Description 数组实现队列
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/3 13:06
 */
public class ArrayQueue {

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

    public ArrayQueue (int capacity) {
        this.items = new String[capacity];
        this.n = capacity;
    }

    /**
     * @Description 入队
     * @Param 元素
     */
    public boolean enqueue (String item) {
        if (tail == n) {
            // 队列已满，入队失败
            if (head == 0) {
                return false;
            }
            // 数据搬移
            for (int i = head; i < tail; i++) {
                items[i-head] = items[i];
            }
            // 数据搬移后，更新head、tail
            head = 0;
            tail -= head;
        }
        items[tail++] = item;
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
        head--;
        return result;
    }
}
