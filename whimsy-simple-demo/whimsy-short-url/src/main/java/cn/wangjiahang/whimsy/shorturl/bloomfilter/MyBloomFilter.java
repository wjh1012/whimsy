package cn.wangjiahang.whimsy.shorturl.bloomfilter;

/**
 * @author jh.wang
 * @since 2024/4/28
 */

public interface MyBloomFilter {

    boolean check(String sign);

    boolean put(String sign);
}
