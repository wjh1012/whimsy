package cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy;

import cn.wangjiahang.whimsy.shorturl.bloomfilter.MyBloomFilter;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
public class MemoryBloomFilter implements MyBloomFilter {
    @Value("${bloom-filter.expected-insertions}")
    private int expectedInsertions;
    @Value("${bloom-filter.fpp}")
    private double fpp;

    @Getter
    private BloomFilter<String> bloomFilter;

    @PostConstruct
    public void init() {
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
    }

    @Override
    public boolean enabled() {
        return true;
    }

    @Override
    public boolean check(String sign) {
        return bloomFilter.mightContain(sign);
    }

    @Override
    public boolean put(String sign) {
        return bloomFilter.put(sign);
    }


}
