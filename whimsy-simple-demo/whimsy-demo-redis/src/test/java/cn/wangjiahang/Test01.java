package cn.wangjiahang;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author jh.wang
 * @since 2024/6/7
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Test01 {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @Autowired
    private RedissonClient redissonClient;

    final String LOCK_KEY = "whimsy-demo-redis:lock-tmp:";

    @Test
    public void aTest() {
        String submitUser = "001";
        String submitValueHash = "712ed5b8-cc25-4bce-a5b3-e4047727fe0b";
        int expired = 2000;

        final CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            doASubmit(submitUser, submitValueHash, expired);
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            Console.log(Thread.currentThread().getName() + ": 设置延迟执行");

            // ThreadUtil.sleep(500); // 获取锁失败
            ThreadUtil.sleep(expired + 1000); // 获取锁成功
            doASubmit(submitUser, submitValueHash, expired);
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> all = CompletableFuture.allOf(task1, task2);
        // 使执行完再结束test线程
        all.join();
    }

    private void doASubmit(String submitUser, String submitValueHash, int expired) {
        Console.log(Thread.currentThread().getName() + ": 执行");


        // 以提交用户为 固定字符串 + 方法名 + 用户id 作为key, 过期时间为1s
        // 保证该方法同一用户 在 1s 内, 相同内容仅能提交一次
        final Duration timeout = Duration.ofMillis(expired);
        Console.log(Thread.currentThread().getName() + ": 过期时间" + timeout);

        final boolean submitNo = Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(LOCK_KEY + "aTest:%s".formatted(submitUser), submitValueHash, timeout));

        if (submitNo) {
            Console.log(Thread.currentThread().getName() + ": 获取锁失败");
            throw new RuntimeException("提交频繁，请歇会再试哦~");
        }

        Console.log(Thread.currentThread().getName() + ": 获取到锁");
    }


    @Test
    public void bTest() {
        String submitUser = "001";
        String submitValueHash = "712ed5b8-cc25-4bce-a5b3-e4047727fe0b";
        int expired = 5000;

        final CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            submitB(submitUser, submitValueHash, expired, () -> {
                Console.log(Thread.currentThread().getName() + ": 获取到锁");

                ThreadUtil.sleep(expired);
                Console.log(Thread.currentThread().getName() + ": 执行完成 耗时: " + expired);
            });
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            submitB(submitUser, submitValueHash, expired, () -> {
                Console.log(Thread.currentThread().getName() + ": 获取到锁");

                ThreadUtil.sleep(expired);
                Console.log(Thread.currentThread().getName() + ": 执行完成 耗时: " + expired);
            });
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> all = CompletableFuture.allOf(task1, task2);
        // 使执行完再结束test线程
        all.join();
    }

    private void submitB(String submitUser, String submitValueHash, int expired, Runnable runnable) {
        try {
            redisLockRegistry.executeLocked("bTest:%s:%s".formatted(submitUser, submitValueHash), Duration.ofMillis(expired), () -> {
                runnable.run();
            });
        } catch (Exception e) {
            Console.error(e, Thread.currentThread().getName() + ": 获取锁失败");
            throw new RuntimeException("提交频繁，请歇会再试哦~");
        }
    }


    @Test
    public void cTest() {
        String submitUser = "001";
        String submitValueHash = "712ed5b8-cc25-4bce-a5b3-e4047727fe0b";
        int expired = 2000;

        final CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            Console.log(Thread.currentThread().getName() + ": 开始执行");

            cSubmit(submitUser, submitValueHash, expired, () -> {
                Console.log(Thread.currentThread().getName() + ": 获取到锁");

                final int millis = 2500;
                ThreadUtil.sleep(millis);

                Console.log(Thread.currentThread().getName() + ": 执行完成 耗时: " + millis);
            });
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            Console.log(Thread.currentThread().getName() + ": 开始执行");

            cSubmit(submitUser, submitValueHash, expired, () -> {
                Console.log(Thread.currentThread().getName() + ": 获取到锁");

                final int millis = 1500;
                ThreadUtil.sleep(millis);

                Console.log(Thread.currentThread().getName() + ": 执行完成 耗时: " + millis);
            });
        }).handle((v, throwable) -> {
            if (throwable instanceof RuntimeException runtimeException) {
                Console.error(throwable, Thread.currentThread().getName() + ": " + runtimeException.getMessage());
            }
            return null;
        });

        final CompletableFuture<Void> all = CompletableFuture.allOf(task1, task2);
        // 使执行完再结束test线程
        all.join();
    }

    private void cSubmit(String submitUser, String submitValueHash, int expired, Runnable runnable) {
        final RLock lock = redissonClient.getLock("cTest:%s:%s".formatted(submitUser, submitValueHash));

        try {
            if (lock.tryLock(expired, TimeUnit.MILLISECONDS)) {
                runnable.run();
            } else {
                Console.error(Thread.currentThread().getName() + ": 获取锁失败 lock.tryLock");
            }
        } catch (Exception e) {
            Console.error(e, Thread.currentThread().getName() + ": 获取锁失败 error");
        } finally {
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
