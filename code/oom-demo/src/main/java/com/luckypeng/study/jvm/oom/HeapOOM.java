package com.luckypeng.study.jvm.oom;

import java.util.ArrayList;
import java.util.List;

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
