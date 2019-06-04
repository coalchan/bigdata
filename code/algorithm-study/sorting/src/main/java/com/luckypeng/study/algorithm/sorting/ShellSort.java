package com.luckypeng.study.algorithm.sorting;

/**
 * @author coalchan
 * @date 2019/06/04
 */
public class ShellSort {
    /**
     * 希尔排序
     * 将所有元素分组，分组的依据是相隔 k 个元素，对于组内的元素进行插入排序。
     * 然后依次缩减增量（将 k 减小）继续分组并排序，直到 k=1 则整体有序。
     * @param array
     */
    public static void sort(Integer[] array) {
        int increment = array.length;

        int temp;
        while (increment > 1) {
            increment = increment / 3 + 1;

            for (int i = increment; i < array.length; i++) {
                if (array[i - increment] > array[i]) {
                    temp = array[i];
                    int j;
                    for (j=i-increment; j>=0 && array[j] > temp; j-=increment) {
                        array[j+increment] = array[j];
                    }
                    array[j+increment] = temp;
                }
            }
        }
    }
}
