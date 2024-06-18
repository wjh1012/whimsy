package day;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.dialect.console.ConsoleLog;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jh.wang
 * @since 2024/6/3
 */
public class Day20240603 {

    @Test
    @SneakyThrows
    public void lockSupportTest() {

        Thread thread1 = new Thread(() -> {
            Console.log("线程[{}] 执行 \r\nLockSupport.park() 阻塞当前线程[{}]", Thread.currentThread().getName(), Thread.currentThread().getName());
            LockSupport.park();
            Console.log("线程[{}] 被释放", Thread.currentThread().getName());

            final int millis = 3000;
            ThreadUtil.sleep(millis);

            Console.log("{}ms 后 线程[{}] 执行完成", millis, Thread.currentThread().getName());
        }, "t1");


        Thread thread2 = new Thread(() -> {

            final int millis = 2000;
            ThreadUtil.sleep(millis);

            Console.log("{}ms 后 线程[{}] LockSupport.unpark() => {}", millis, Thread.currentThread().getName(), thread1.getName());
            LockSupport.unpark(thread1);

        }, "t2");


        thread1.start();
        thread2.start();

        thread1.join();
    }
}
