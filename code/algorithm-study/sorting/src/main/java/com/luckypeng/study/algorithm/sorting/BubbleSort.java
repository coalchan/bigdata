package com.luckypeng.study.algorithm.sorting;

import com.luckypeng.study.algorithm.sorting.util.ArrayUtils;

/**
 * 冒泡排序
 * @author coalchan
 * @date 2019/06/02
 */
public class BubbleSort {
    /**
     * 简单交换排序
     * 依次拿元素跟后面所有的元素做比较，大小相反则交换
     * @param array
     */
    public static void swapSort(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    ArrayUtils.swap(array, i, j);
                }
            }
        }
    }

    /**
     * 冒泡排序
     * 从末尾开始，两两比较，如果大小相反，则交换，这样小的就一直往前走，滚到最前面就是最小的
     * @param array
     */
    public static void bubbleSort(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = array.length - 2; j >= i; j--) {
                if (array[j] > array[j + 1]) {
                    ArrayUtils.swap(array, j, j+1);
                }
            }
        }
    }
}
