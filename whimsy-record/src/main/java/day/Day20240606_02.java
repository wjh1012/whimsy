package day;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jh.wang
 * @since 2024/6/6
 */
public class Day20240606_02 {
    public static void main(String[] args) {
        final ReadWriteLock lock = new ReentrantReadWriteLock();

        final boolean b = lock.writeLock().tryLock();

    }
}
