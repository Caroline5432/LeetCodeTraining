package com.training.algorithm.linkedList;

/*
 *@description 基于单链表实现回文串判断
 *@author caroline
 *@version 1.0
 *@date 2023/12/10 15:40
 */
public class PalindromeBySinglelyLinkedList {

    private SingleNode headNode;

    /**
     * 判断是否为回文串
     */
    public boolean palindrome() {
        if (headNode == null) {
            return false;
        }
        if (headNode.getNext() == null) {
            System.out.println("只有一个元素，是回文串");

        }

        SingleNode p = headNode;
        SingleNode q = headNode;
        // 找中点
        while (q.getNext() != null && q.getNext().getNext() != null) {
            p = p.getNext();
            q = q.getNext().getNext();
        }
        System.out.println("中间节点是：" + p.getElement());

        SingleNode left = null;
        SingleNode right = null;

        if (q.getNext() == null) {
            // 奇数个节点
            left = p.getNext();
            right = inverseLinkedList(p).getNext();
        } else {
            left = p.getNext();
            right = inverseLinkedList(p);
        }

        return equalsLinkedList(left, right);
    }

    /**
     * @description 比较两个单链表是否相等
     * @param
     */
    private boolean equalsLinkedList(SingleNode left, SingleNode right) {
        boolean result = true;
        SingleNode leftTemp = left;
        SingleNode rightTemp = right;

        if ((leftTemp == null || rightTemp == null) || !leftTemp.getLength().equals(rightTemp.getLength())) {
            return false;
        }

        while (leftTemp != null && rightTemp != null) {
            if (!leftTemp.getElement().equals(rightTemp.getElement())) {
                result = false;
                break;
            }
            leftTemp = leftTemp.getNext();
            rightTemp = rightTemp.getNext();
        }
        return result;
    }

    /**
     * @description 反转单链表
     * @param
     */
    private SingleNode inverseLinkedList(SingleNode p) {
        SingleNode pre = null;
        SingleNode next = null;
        SingleNode result = headNode;

        // todo 此处条件判断存在bug，例如 3 2 3 2 3，目前没想到解决方法
        while (!result.getElement().equals(p.getElement())) {
            next = result.getNext();
            result.setNext(pre);
            pre = result;
            result = next;
        }

        result.setNext(pre);
        return result;
    }

    //链表尾部顺序插入
    public void insertTail(Object value){

        SingleNode newNode = new SingleNode(value, null);
        //空链表，可以插入新节点作为head，也可以不操作
        if (headNode == null){
            headNode = newNode;
            return;
        }
        SingleNode q = headNode;
        while(q.getNext() != null){
            q = q.getNext();
        }
        q.setNext(newNode);
    }

    public static void main(String[] args) {
        PalindromeBySinglelyLinkedList linkedList = new PalindromeBySinglelyLinkedList();

        /*String dataStr[] = {"a", "b", "c", "d", "a"};

        for (int i = 0; i < dataStr.length; i++) {
            linkedList.insertTail(dataStr[i]);
        }*/

        int data[] = {3,2,3,2,3};
        for (int i = 0; i < data.length; i++) {
            linkedList.insertTail(data[i]);
        }

        System.out.println(linkedList.palindrome());

    }

}
