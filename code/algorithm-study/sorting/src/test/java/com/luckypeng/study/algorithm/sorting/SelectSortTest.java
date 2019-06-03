package com.luckypeng.study.algorithm.sorting;

import org.junit.Test;

import static org.junit.Assert.*;

public class SelectSortTest {
    Integer[] refers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void selectSort() {
        Integer[] array = new Integer[]{9, 1, 5, 8, 3, 7, 4, 6, 2};
        SelectSort.selectSort(array);
        assertArrayEquals(refers, array);
    }
}