# 常见 OOM 异常

除了 PC 寄存器（或者称为程序计数器）之外，JVM 的其他几个内存区域都会有发生 `OutOfMemorryError` （简称 `OOM` ）异常的可能。

## Java 堆溢出

Java堆用于存储对象实例，只要不断创建对象，切保证 GC Roots 到对象之间有可达路径来避免垃圾回收机制清除这些对象，那么对象数量达到最大堆的容量限制后就会产生内存溢出异常。

```java
/**
 * 堆内存溢出
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 生成的 .jprof 文件可以用 jhat [file] 命令，会在本地启动一个 webservice，以便进一步定位问题
 * @author coalchan
 */
public class HeapOOM {
    static class OOM {}

    public static void main(String[] args) {
        List<OOM> list = new ArrayList<>();
        while (true) {
            list.add(new OOM());
        }
    }
}
```

运行结果：

```
java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid2680.hprof ...
Heap dump file created [27772696 bytes in 0.090 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
```

解决思路：

分析堆转储快照文件，最简单的可以使用jdk自带的jhat命令分析，也可以使用更加高级的一些可视化工具。以上实例中，通过运行`jhat java_pid2680.hprof`，访问`http://localhost:7000`可以看到如下页面信息：
![](../img/jvm-heap-jhat.png)

这里便可以进一步定位问题了。

实际问题可能比这个要复杂，一般首先是要确认出现了**内存泄漏**（Memory Leak）还是**内存溢出**（Memory Overflow）。

1. 内存泄漏

   通过工具查看泄漏对象到GC Roots的引用链，就能知道是什么路径导致不能回收。

2. 内存溢出

   查看堆参数（ `Xms` 和 `Xmx` ）能否调大，另外看看某些对象是不是存储周期过长或者不必要等。

## 虚拟机栈和本地方法栈溢出

栈容量可以由JVM参数 `-Xss` 来设置，JVM 定义了两种异常：

1. 如果线程请求的栈深度大于 JVM 所允许的最大深度，则抛出 `StackOverflowError` 异常；
2. 如果 JVM 在扩展栈时无法申请到足够的内存空间，则抛出 `OutOfMemoryError` 异常。

下面通过一个无限递归调用的例子来模拟第一种情况（第二种情况实际很慢模拟出来）：

```java
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
```

运行结果：

```
Connected to the target VM, address: '127.0.0.1:56330', transport: 'socket'
栈深度: 1079
Exception in thread "main" java.lang.StackOverflowError
```

注意该区域占用的是堆外内存，实践中每个进程的最大内存是有限的，从中减去最大堆内存（ `Xmx` 的值），再减去 MaxPermSize（最大方法区容量），程序计数器占用的很小暂且忽略，剩下的就是虚拟机栈和本地方法栈的了。

所以当出现该错误的时候，先考虑是否操作系统有线程数的限制，这个时候可能就需要调整最大文件数。然后考虑是不是堆内存太大，导致实际分配给每个线程使用的栈容量过小，这个时候可能需要**减小**最大堆内存设置或者**减小**最大栈设置，从而换取更多的线程。

## 方法区溢出

方法区用于存储 Class 相关的信息，如类名、访问修饰符、字段描述、方法描述等。需要注意的是**字符串常量池**从 Java8 之后已经由堆内存进行管理，并且取消了永久代（相关的JVM参数 `-XX:PermSize` 和 `-XX:MaxPermSize` 也已经无用）。

下面利用 CGLib 来创建大量的 Class，从而导致方法区内存溢出。

```java
/**
 * VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
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
```

运行结果：

```
Exception in thread "main" net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InvocationTargetException-->null
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:345)
	at net.sf.cglib.proxy.Enhancer.generate(Enhancer.java:492)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:114)
	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:291)
	at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:480)
	at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:305)
	at com.luckypeng.study.jvm.oom.MethodAreaOOM.main(MethodAreaOOM.java:18)
Caused by: java.lang.reflect.InvocationTargetException
	at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:459)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:336)
	... 6 more
Caused by: java.lang.OutOfMemoryError: Metaspace
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
	... 11 more
```

 ## 直接内存溢出

在使用 ByteBuffer 中的 allocateDirect() 的时候会用到，很多 javaNIO（如 netty）的框架中被封装为其他的方法，出现该问题时会抛出 `java.lang.OutOfMemoryError: Direct buffer memory` 异常。