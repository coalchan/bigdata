package com.luckypeng.study.algorithm.sorting;

import org.junit.Test;

import static org.junit.Assert.*;

public class StraightInsertionSortTest extends AbstractSort {

    @Test
    public void sort() {
        StraightInsertionSort.sort(test);
        assertArrayEquals(refers, test);
    }
}