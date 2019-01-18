package com.luckypeng.study.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * VM Args: -XX:MetaspaceSize=1M -XX:MaxMetaspaceSize=1M
 * @author chenzhipeng
 * @date 2019/1/18 18:24
 */
public class MethodAreaOOM {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MethodAreaOOM.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invoke(obj, args1));
            enhancer.create();
        }
    }
}
