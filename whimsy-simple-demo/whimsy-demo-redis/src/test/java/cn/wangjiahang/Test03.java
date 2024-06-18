package cn.wangjiahang;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jh.wang
 * @since 2024/6/7
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Test03 {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    final int count = 100;
    String key = "whimsy-demo-redis:scope";
    ZSetOperations<String, Object> zSet;

    ExecutorService pool = Executors.newFixedThreadPool(4);

    @BeforeEach
    public void before() {
        zSet = redisTemplate.opsForZSet();

        for (int i = 0; i < count; i++) {
            zSet.add(key, "i" + i, RandomUtil.randomLong());
        }

        Console.log("----------before----------");
    }

    @AfterEach
    public void after() {
        redisTemplate.delete(key);
        Console.log("----------after-----------");
    }

    @Test
    public void aTest() {
        final CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            final Long i4 = zSet.reverseRank(key, "i4");
            Console.log("i4 reverseRank: " + i4);
        }, pool);

        final CompletableFuture<Void> task5 = CompletableFuture.runAsync(() -> {
            final Double i4 = zSet.score(key, "i4");
            Console.log("i4 score: " + i4);
        }, pool);

        final CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            final List<Object> members = zSet.randomMembers(key, 100);
            Console.log("randomMembers: {}", members);
        }, pool);

        final CompletableFuture<Void> task3 = CompletableFuture.runAsync(() -> {
            final List<ZSetOperations.TypedTuple<Object>> typedTuples = zSet.randomMembersWithScore(key, 10);
            Console.log("randomMembersWithScore: {}", typedTuples);
        }, pool);

        // 获取排行榜的前N名玩家
        final CompletableFuture<Void> task4 = CompletableFuture.runAsync(() -> {
            final Set<ZSetOperations.TypedTuple<Object>> typedTuples = zSet.reverseRangeWithScores(key, 0, 9);
            Console.log("reverseRangeWithScores: {}", typedTuples);
        }, pool);

        CompletableFuture.allOf(task1, task2, task3, task4, task5).join();
    }

}
