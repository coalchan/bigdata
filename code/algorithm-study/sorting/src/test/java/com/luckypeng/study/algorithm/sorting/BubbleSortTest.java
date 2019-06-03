package com.luckypeng.study.algorithm.sorting;

import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSortTest extends AbstractSort {

    @Test
    public void swapSort() {
        BubbleSort.swapSort(test);
        assertArrayEquals(refers, test);
    }

    @Test
    public void bubbleSort() {
        BubbleSort.bubbleSort(test);
        assertArrayEquals(refers, test);
    }
}