package com.luckypeng.study.algorithm.sorting;

/**
 * 直接插入排序
 * @author coalchan
 * @date 2019/06/03
 */
public class StraightInsertionSort {
    /**
     * 从第二个元素开始，把前面的元素当成有序的集合，按照大小关系插在其中合适的位置
     * @param array
     */
    public static void sort(Integer[] array) {
        int temp;
        for (int i = 1; i < array.length; i++) {
            if (array[i-1] > array[i]) {
                temp = array[i];
                int j;
                for (j = i-1; j >= 0 && array[j] > temp; j--) {
                    array[j+1] = array[j];
                }
                array[j+1] = temp;
            }
        }
    }
}
