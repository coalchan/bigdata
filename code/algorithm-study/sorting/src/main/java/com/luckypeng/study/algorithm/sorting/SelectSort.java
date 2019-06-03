package com.luckypeng.study.algorithm.sorting;

import com.luckypeng.study.algorithm.sorting.util.ArrayUtils;

/**
 * 简单选择排序
 * @author coalchan
 * @date 2019/06/02
 */
public class SelectSort {
    /**
     * 每次选择最小的元素放在前面
     * @param array
     */
    public static void selectSort(Integer[] array) {
        int min;
        for (int i = 0; i < array.length; i++) {
            min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[min] > array[j]) {
                    min = j;
                }
            }
            if (i != min) {
                ArrayUtils.swap(array, i, min);
            }
        }
    }
}
