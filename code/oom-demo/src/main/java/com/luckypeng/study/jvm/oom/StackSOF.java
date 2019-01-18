package com.luckypeng.study.jvm.oom;

/**
 * 栈溢出: StackOverflowError
 * VM Args: -Xss128k
 * @author chenzhipeng
 * @date 2019/1/18 11:13
 */
public class StackSOF {
    private static int stackLength = 0;

    static void stack() {
        stackLength++;
        stack();
    }

    public static void main(String[] args) {
        try {
            stack();
        } catch (Throwable e) {
            System.out.println("栈深度: " + stackLength);
            throw e;
        }
    }
}
