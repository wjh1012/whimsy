package cn.wangjiahang.whimsy.shorturl.bloomfilter;

import cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy.EmptyBloomFilter;
import cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy.MemoryBloomFilter;
import cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy.RedisBloomFilter;
import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@Configuration
@Getter
public class BloomFilterConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("${bloom-filter.enabled:false} && '${bloom-filter.type:}'.equalsIgnoreCase('memory')")
    public MyBloomFilter memoryBloomFilter() {
        return new MemoryBloomFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnExpression("${bloom-filter.enabled:false} && '${bloom-filter.type:}'.equalsIgnoreCase('redis')")
    public MyBloomFilter redisBloomFilter() {
        return new RedisBloomFilter();
    }

    /**
     * 需要放在最后
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MyBloomFilter emptyBloomFilter() {
        return new EmptyBloomFilter();
    }

}
