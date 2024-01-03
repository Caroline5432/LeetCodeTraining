package com.training.algorithm.Queue;

import com.training.algorithm.linkedList.SingleNode;

/**
 * @ClassName LinkedListQueue
 * @Description 链表实现队列
 * @Author caroline
 * @Version 1.0
 * @Date 2024/1/3 13:36
 */
public class LinkedListQueue {

    private SingleNode head = null;

    private SingleNode tail = null;

    // 入队
    public void enqueue (String element) {
        SingleNode<String> newNode = new SingleNode<>(element, null);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = tail.getNext();
        }
    }

    // 出队
    public String dequeue () {
        if (head == null) {
            return null;
        }
        String result = (String)head.getElement();
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        return result;
    }

    public void printAll () {
        SingleNode node = head;
        while (node != null) {
            System.out.print(node.getElement() + " ");
            node = node.getNext();
        }
        System.out.println();
    }
}
