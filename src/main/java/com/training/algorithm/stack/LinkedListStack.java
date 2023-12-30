package com.training.algorithm.stack;

import com.training.algorithm.linkedList.SingleNode;

/**
 * @ClassName LinkedListStack
 * @Description 基于链表实现链式栈
 * @Author caroline
 * @Version 1.0
 * @Date 2023/12/30 15:26
 */
public class LinkedListStack {

    private SingleNode top = null;

    private int size;

    /**
     * 入栈操作
     */
    public void push (String value) {
        SingleNode<String> newNode = new SingleNode<>(value, null);
        if (top == null) {
            top = newNode;
        } else {
            newNode.setNext(top);
            top = newNode;
        }
        this.size++;
    }

    /**
     * 出栈操作
     */
    public String pop () {
        if (top == null) {
            return null;
        }
        String value = (String)top.getElement();
        SingleNode nextNode = top.getNext();
        top = nextNode;
        if (this.size > 0) {
            this.size--;
        }
        return value;
    }

    /**
     * 打印全部元素
     */
    public void pringAll () {
        SingleNode node = top;
        while (node != null) {
            System.out.print(node.getElement() + " ");
            node = node.getNext();
        }
        System.out.println();
    }

    public void clear() {
        this.top = null;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }
}
