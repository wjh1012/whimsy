package cn.wangjiahang.whimsy.shorturl.bloomfilter.strategy;

import cn.wangjiahang.whimsy.shorturl.bloomfilter.MyBloomFilter;

/**
 * @author jh.wang
 * @since 2024/4/28
 */

public class EmptyBloomFilter implements MyBloomFilter {

    @Override
    public boolean check(String sign) {
        return false;
    }

    @Override
    public boolean put(String sign) {
        return true;
    }
}
