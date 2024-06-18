package day;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jh.wang
 * @since 2024/6/4
 */
public class Day20240604 {

    @Test
    @SneakyThrows
    public void lockSupportTest() {
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(
                0,
                10,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                new ThreadPoolExecutor.CallerRunsPolicy());



    }
}
