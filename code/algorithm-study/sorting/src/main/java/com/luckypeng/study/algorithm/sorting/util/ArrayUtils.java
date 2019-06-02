package com.luckypeng.study.algorithm.sorting.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author coalchan
 * @date 2019/06/02
 */
public class ArrayUtils {
    private ArrayUtils() {}

    /**
     * 交换数组的两个数
     * @param array
     * @param i
     * @param j
     */
    public static  <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 打印数组，按逗号分隔
     * @param array
     * @param <T>
     */
    public static <T> void print(T[] array) {
        List<String> strArray = Stream.of(array).map(String::valueOf).collect(Collectors.toList());
        System.out.println(String.join(", ", strArray));
    }
}
