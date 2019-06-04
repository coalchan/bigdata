package com.luckypeng.study.algorithm.sorting;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShellSortTest extends AbstractSort {

    @Test
    public void sort() {
        ShellSort.sort(test);
        assertArrayEquals(refers, test);
    }
}