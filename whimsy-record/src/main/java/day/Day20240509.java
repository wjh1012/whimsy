package day;


import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @author jh.wang
 * @since 2024/5/9
 */

public class Day20240509 {

    @Test
    public void testSemaphore() throws InterruptedException {
        int maxConcurrent = 3; // 最大并发线程数
        final int threadCount = 10;

        // 控制 @Test 在线程执行完的时候结束
        CountDownLatch latch = new CountDownLatch(threadCount);

        Semaphore semaphore = new Semaphore(maxConcurrent);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("线程 " + Thread.currentThread().getName() + " 获得许可，正在执行... ");

                    final int millis = new Random().nextInt(1000, 2000);
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程 " + Thread.currentThread().getName() + " 释放许可...");
                    semaphore.release();
                    latch.countDown();
                }
            }, i + "").start();
        }

        latch.await();
    }

    @Test
    public void testCountDownLatch() throws InterruptedException {
        final int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    final int millis = new Random().nextInt(1000, 5000);
                    Thread.sleep(millis);

                    System.out.println(Thread.currentThread().getName() + " 执行 " + millis);
                    latch.countDown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, i + "").start();
        }

        latch.await();
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount, () -> {
            System.out.println("All threads have reached the barrier");
        });

        for (int i = 0; i < 8; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 执行");

                    Thread.sleep(2000);
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread().getName() + " 执行 ok");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, i + "").start();
        }


    }


}
