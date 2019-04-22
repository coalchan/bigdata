package com.luckypeng.study.algorithm.leetcode;

/**
 * @author coalchan
 * @date 2019/04/22
 */
public class ArrayAndList {
    private ArrayAndList() {}

    public static ListNode swapPairs(ListNode head) {
        if (head==null || head.next==null) {
            return head;
        }

        ListNode empty = new ListNode(-1);

        empty.next = head;

        ListNode pre1 = empty;
        ListNode pre2 = head;

        while (pre2 != null && pre2.next != null) {
            ListNode nextStart = pre2.next.next;

            pre2.next.next = pre2;

            pre1.next = pre2.next;
            pre2.next = nextStart;

            pre1 = pre2;
            pre2 = nextStart;

        }

        return empty.next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
