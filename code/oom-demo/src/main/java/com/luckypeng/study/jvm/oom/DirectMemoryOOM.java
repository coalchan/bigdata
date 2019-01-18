package com.luckypeng.study.jvm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * TODO 未模拟成功，待测
 * VM Args: -Xmx20m -XX:MaxDirectMemorySize=10m
 * @author coalchan
 */
public class DirectMemoryOOM {
    private static int _1MB = 1024 * 1024;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);

        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
