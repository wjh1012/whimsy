package day;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jh.wang
 * @since 2024/6/6
 */
public class Day20240606_01 {
    public static void main(String[] args) {
        final Lock lock = new ReentrantLock(true);

        for (int i = 0; i < 10; i++) {
            ThreadUtil.sleep(5);
            new Thread(() -> {
                boolean tryLock = false;
                try {
                    while (!tryLock) {
                        if ((lock.tryLock())) {
                            tryLock = true;
                            System.out.println(Thread.currentThread().getName() + " 获取到锁");
                            ThreadUtil.sleep(10);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    if (tryLock) {
                        lock.unlock();
                    }
                }
            }, "thread" + i).start();
        }

    }
}
