package com.training.algorithm.linkedList;

/*
 *@description 单链表
 *@author caroline
 *@version 1.0
 *@date 2023/12/10 15:59
 */
public class SingleNode<T> {

    private T element;

    private SingleNode next;

    /**
     * 链表长度
     */
    private Integer length = 0;

    public SingleNode (T element) {
        this.element = element;
        this.length++;
    }

    public SingleNode (T element, SingleNode next) {
        this.element = element;
        this.length++;
        this.next = next;
        if (next != null) {
            this.length++;
        }
    }

    public SingleNode () {
        this.next = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public SingleNode getNext() {
        return next;
    }

    public void setNext(SingleNode next) {
        this.next = next;
        if (next != null) {
            this.length++;
        }
    }

    public Integer getLength() {
        return length;
    }
}
