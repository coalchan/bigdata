package com.luckypeng.study.algorithm.sorting;

import org.junit.Test;

import static org.junit.Assert.*;

public class BubbleSortTest {
    Integer[] refers = {1, 2, 3, 4, 5, 6, 7, 8, 9};


    @Test
    public void swapSort() {
        Integer[] array = new Integer[]{9, 1, 5, 8, 3, 7, 4, 6, 2};
        BubbleSort.swapSort(array);
        assertArrayEquals(refers, array);
    }

    @Test
    public void bubbleSort() {
        Integer[] array = new Integer[]{9, 1, 5, 8, 3, 7, 4, 6, 2};
        BubbleSort.bubbleSort(array);
        assertArrayEquals(refers, array);
    }
}