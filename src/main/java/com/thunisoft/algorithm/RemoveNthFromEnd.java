package com.thunisoft.algorithm;

import java.util.regex.Pattern;

/**
 * @author zhangliujie
 * @Description 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * @date 2019/12/7.
 */
public class RemoveNthFromEnd {

    // 两次遍历算法
    // 删除从列表开始数起的第 （L-n+1）个结点
    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        //哑结点，简化极端情况，如列表中只有一个结点，或者是要删除第一个结点
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // 计算列表长度
        int length = 0;
        ListNode first = head;
        while (first != null) {
            length ++;
            first = first.next;
        }

        // 删掉第 （L-n+1）个结点
        length -= n;
        first = dummy;
        while (length > 0) {
            length --;
            first = first.next;
        }
        first.next = first.next.next;

        // first dummy 操作的是同一个列表，first让指针向后走，删掉第 （L-n+1）个结点，dummy 的指针就还在最前端
        return dummy.next;
    }

    /**
     * 一次遍历算法
     * 使用两个指针，第一个指针向前移动 n+1 个结点，第二个指针在第1个结点，那么两个指针间隔 n个结点，
     * 两个指针同时移动，当第一个指针指向最后一个结点时，第二个指针指向倒数第n个结点
     * 将第二个指针的next 执行head的next.next个结点
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode first = dummy;
        ListNode second = dummy;

        // 第一个指针向前移动n+1个结点
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }

        // 将第一个指针指向最后一个结点
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        System.out.println(removeNthFromEnd2(head, 2));

    }

}
