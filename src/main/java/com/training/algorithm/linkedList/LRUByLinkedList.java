package com.training.algorithm.linkedList;

import org.springframework.util.ObjectUtils;

import java.util.Scanner;

/*
 *@description
 * 基于单链表实现LRU(Least Recently Used)
 * 1、维护一个有序单链表
 * 2、如果此数据之前已经被缓存到单链表中了，遍历得到数据对应的结点，并将其从原来的位置删除，再插入到链表的头部
 * 3、如果此数据没有被缓存，则分为两种情况：
 *  3.1 如果此时缓存未满，则直接将此数据插入到链表的头部
 *  3.2 如果此时缓存已满，则将链表尾结点删除，再将数据插入到链表的头部
 *@author zhangliujie
 *@version 1.0
 *@date 2023/12/10 15:32
 */
public class LRUByLinkedList<T> {

    /**
     * 默认链表容量
     */
    private final static Integer DEFAULT_CAPACITY = 10;

    /**
     * 头结点
     */
    private SingleNode headNode;

    /**
     * 链表长度
     */
    private Integer length;

    /**
     * 链表容量
     */
    private Integer capacity;

    public LRUByLinkedList () {
        this.headNode = new SingleNode<>();
        this.capacity = DEFAULT_CAPACITY;
        this.length = 0;
    }

    public LRUByLinkedList (Integer capacity) {
        this.headNode = new SingleNode<>();
        this.capacity = capacity;
        this.length = 0;
    }

    /**
     * 插入数据
     */
    public void add (T data) {
        SingleNode preNode = findPreNode(data);
        // 链表中已存在，删除链表中的结点，插入到链表头部
        if (!ObjectUtils.isEmpty(preNode)) {
            deleteElem(preNode);
        }  else if (length >= this.capacity) {
            // 不存在，判断链表容量是否已满，如果链表已满，则删除尾部结点
            deleteElemAtEnd();
        }
        insertElemAtBegin(data);
    }

    /**
     * @description 获取查找到元素的前一个结点
     * @param
     */
    private SingleNode findPreNode(T data) {
        SingleNode node = headNode;
        while (node.getNext() != null) {
            if (data.equals(node.getNext().getElement())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * @description 将数据插入到头部结点
     * @param
     */
    private void insertElemAtBegin(T data) {
        SingleNode nextNode = headNode.getNext();
        headNode.setNext(new SingleNode(data, nextNode));
        length++;
    }

    /**
     * @description 删除尾部结点
     * @param
     */
    private void deleteElemAtEnd() {
        SingleNode node = headNode;
        // 空链表直接返回
        if (node.getNext() == null) {
            return;
        }

        // 获取倒数第二个结点
        while (node.getNext().getNext() != null) {
            node = node.getNext();
        }

        SingleNode temp = node.getNext();
        node.setNext(null);
        temp = null;
        length--;
    }

    /**
     * @description 删除preNode的下一个结点
     * @param
     */
    private void deleteElem(SingleNode preNode) {
        SingleNode temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp = null;
        length--;
    }

    private void printAll() {
        SingleNode node = headNode.getNext();
        while (node != null) {
            System.out.print(node.getElement() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUByLinkedList<Object> list = new LRUByLinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            list.add(scanner.nextInt());
            list.printAll();
        }
    }


}
