package cn.wangjiahang.whimsy.shorturl.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2024/4/30
 */
@Slf4j
@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        RedissonClient redissonClient;

        Config config = new Config();
        // starter依赖进来的redisson要以redis://开头，其他不用
        String url = "redis://"+ redisProperties.getHost() + ":" + redisProperties.getPort();
        config.useSingleServer().setAddress(url)
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase());

        try {
            redissonClient = Redisson.create(config);
            return redissonClient;
        } catch (Exception e) {
            log.error("RedissonClient init redis url:[{}], Exception:", url, e);
            return null;
        }
    }
}
