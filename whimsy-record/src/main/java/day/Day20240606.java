package day;

import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jh.wang
 * @since 2024/6/6
 */
public class Day20240606 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        final Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "执行");
            final long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                if (atomicInteger.compareAndSet(i, i + 1)) {
                    System.out.println(Thread.currentThread().getName() + ":" + atomicInteger.get());
                    // 若放开下行注释, t1线程获取锁时间加长, 会导致t2线程认为拿不到锁 进而忽略自旋[自适应自旋]
                    // ThreadUtil.sleep(1);

                    if (i == 99) {
                        atomicInteger.compareAndSet(100, -1);
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + "执行结束, 耗时: " + (System.currentTimeMillis() - start));
        }, "t1");
        t1.start();

        final Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "执行");
            final long start = System.currentTimeMillis();
            do {
                System.out.println(Thread.currentThread().getName() + ":" + atomicInteger.get());
            } while (atomicInteger.compareAndSet(-1, 1));
            System.out.println(Thread.currentThread().getName() + "执行结束, 耗时: " + (System.currentTimeMillis() - start) + ", 执行状态: " + (atomicInteger.get() == 1 ? "成功" : "失败"));
        }, "t2");
        t2.start();
    }
}
