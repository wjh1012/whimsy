package cn.wangjiahang;

import cn.hutool.core.lang.Console;
import cn.wangjiahang.test02.SubscribableConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.channel.SubscribableRedisChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author jh.wang
 * @since 2024/6/7
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class Test02 {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SubscribableRedisChannel test02SubscribableRedisChannel;

    @Test
    public void aTest() throws InterruptedException {
        final int count = 2;
        final ExecutorService service = Executors.newFixedThreadPool(count);
        final CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            service.execute(() -> {
                final String message = "%s send message: %s".formatted(Thread.currentThread().getName(), UUID.randomUUID().toString());
                final GenericMessage<String> genericMessage = new GenericMessage<>(message);
                test02SubscribableRedisChannel.send(genericMessage);
                countDownLatch.countDown();

                Console.log(message);
            });
        }

        countDownLatch.await();

        // 原生
        redisTemplate.convertAndSend(SubscribableConfig.TOPIC_NAME_DEMO02, "redisTemplate.convertAndSend()");
    }


    @AfterEach
    public void after() {
        redisTemplate.delete(SubscribableConfig.TOPIC_NAME_DEMO02);
        Console.log("----------after-----------");
    }


}
