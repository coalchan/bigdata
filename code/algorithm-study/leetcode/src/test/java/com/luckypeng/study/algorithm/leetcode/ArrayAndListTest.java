package com.luckypeng.study.algorithm.leetcode;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayAndListTest {

    @Test
    public void swapPairs() {
        ListNode next = new ListNode(1);
        ListNode first = next;
        for (int i = 2; i <= 6; i++) {
            next.next = new ListNode(i);
            next = next.next;
        }

        ArrayAndList.swapPairs(first);
    }
}