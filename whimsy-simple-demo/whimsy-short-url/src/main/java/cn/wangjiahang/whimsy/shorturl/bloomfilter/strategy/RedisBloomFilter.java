package cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy;

import cn.wangjiahang.whimsy.shorturl.bloomfilter.MyBloomFilter;
import lombok.Getter;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author jh.wang
 * @since 2024/4/28
 */

public class RedisBloomFilter implements MyBloomFilter {

    private static final String DEFAULT_KEY_NAME = "short-url:bloom-filter";

    @Value("${bloom-filter.expected-insertions}")
    private int expectedInsertions;
    @Value("${bloom-filter.fpp}")
    private double fpp;
    @Value("${bloom-filter.redis-key:}")
    private String redisKey;

    @Autowired
    private RedissonClient redissonClient;

    @Getter
    private RBloomFilter<String> bloomFilter;

    @PostConstruct
    public void init() {
        final String key = redisKey == null || redisKey.isBlank() ? DEFAULT_KEY_NAME : redisKey;

        bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(expectedInsertions, fpp);
    }

    @Override
    public boolean check(String sign) {
        return bloomFilter.contains(sign);
    }

    @Override
    public boolean put(String sign) {
        return bloomFilter.add(sign);
    }
}
