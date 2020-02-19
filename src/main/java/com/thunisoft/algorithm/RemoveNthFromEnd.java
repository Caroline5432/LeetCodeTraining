package com.thunisoft.algorithm;

import java.util.regex.Pattern;

/**
 * @author zhangliujie
 * @Description ����һ������ɾ������ĵ����� n ���ڵ㣬���ҷ��������ͷ��㡣
 * @date 2019/12/7.
 */
public class RemoveNthFromEnd {

    // ���α����㷨
    // ɾ�����б�ʼ����ĵ� ��L-n+1�������
    public static ListNode removeNthFromEnd1(ListNode head, int n) {
        //�ƽ�㣬�򻯼�����������б���ֻ��һ����㣬������Ҫɾ����һ�����
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // �����б���
        int length = 0;
        ListNode first = head;
        while (first != null) {
            length ++;
            first = first.next;
        }

        // ɾ���� ��L-n+1�������
        length -= n;
        first = dummy;
        while (length > 0) {
            length --;
            first = first.next;
        }
        first.next = first.next.next;

        // first dummy ��������ͬһ���б�first��ָ������ߣ�ɾ���� ��L-n+1������㣬dummy ��ָ��ͻ�����ǰ��
        return dummy.next;
    }

    /**
     * һ�α����㷨
     * ʹ������ָ�룬��һ��ָ����ǰ�ƶ� n+1 ����㣬�ڶ���ָ���ڵ�1����㣬��ô����ָ���� n����㣬
     * ����ָ��ͬʱ�ƶ�������һ��ָ��ָ�����һ�����ʱ���ڶ���ָ��ָ������n�����
     * ���ڶ���ָ���next ִ��head��next.next�����
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode first = dummy;
        ListNode second = dummy;

        // ��һ��ָ����ǰ�ƶ�n+1�����
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }

        // ����һ��ָ��ָ�����һ�����
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
