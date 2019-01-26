package com.luckypeng.study.jvm.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class AppleTest {
    private static final int AGE = 12;

    /**
     * 反射调用
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private Object callWithReflect()
            throws ClassNotFoundException,
            IllegalAccessException,
            InstantiationException,
            NoSuchMethodException,
            InvocationTargetException {
        Class clazz = Class.forName("com.luckypeng.study.jvm.reflect.Apple");
        Constructor constructor = clazz.getConstructor(int.class);
        Object apple = constructor.newInstance(AGE);
        Method method = clazz.getMethod("getPrice");
        return method.invoke(apple);
    }

    @Test
    public void testGetPrice() throws Exception{
        Apple apple = new Apple(AGE);
        assertEquals(apple.getPrice(), callWithReflect());
    }

}