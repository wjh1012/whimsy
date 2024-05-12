package day;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * @author jh.wang
 * @since 2024/5/11
 */

public class Day20240511 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<?>[] array = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .map(i -> {
                    return CompletableFuture.runAsync(() -> {
                        ThreadUtil.sleep(1000);
                        System.out.println("i: " + i);
                    });
                }).toArray(CompletableFuture[]::new);

        System.out.println("main doing other");

        final CompletableFuture<Void> completableFuture = CompletableFuture.allOf(array);

        completableFuture.thenRun(() -> {
            System.out.println("then run");
        }).thenAccept(action -> {
            System.out.println("then accept");
        }).get();

        System.out.println("main doing other");
    }
}
