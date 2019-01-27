package com.luckypeng.study.jvm.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用 -verbose:class 打印加载的类
 * 可以看到在第15次时，额外加载了不少类
 */
public class Inflation {
    public static void run(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = Inflation.class;
        Method method = clazz.getMethod("run", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }
}
