package cn.wangjiahang.test02;

import cn.hutool.core.lang.Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.channel.SubscribableRedisChannel;

/**
 * @author jh.wang
 * @since 2024/6/7
 */
@Configuration
public class SubscribableConfig {

    public static final String TOPIC_NAME_DEMO02 = "whimsy-demo-redis:test02";

    @Bean(name = "test02SubscribableRedisChannel")
    public SubscribableRedisChannel subscribableRedisChannel(RedisConnectionFactory factory) {
        final SubscribableRedisChannel channel = new SubscribableRedisChannel(factory, TOPIC_NAME_DEMO02);

        channel.subscribe(message -> {
            final Object payload = message.getPayload();
            Console.log("收到消息(payload): {}", payload);
        });

        return channel;
    }
}
