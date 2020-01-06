package com.thunisoft.algorithm;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/4.
 */
public class ListNodeTest {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        //生成ListNode 链表对象，链表的值为0，没有指向的节点
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            int sum = carry + x + y;
            carry = sum / 10;

            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if(l1 != null) {
                l1 = l1.next;
            }
            if(l2 != null) {
                l2 = l2.next;
            }
        }
        if(carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummyHead.next;
    }

   /* public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode result =  addTwoNumbers(l1, l2);
        System.out.println(result);
    }*/

}
