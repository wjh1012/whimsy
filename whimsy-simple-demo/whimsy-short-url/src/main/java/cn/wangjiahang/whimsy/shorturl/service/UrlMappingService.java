package cn.wangjiahang.whimsy.shorturl.service;

import cn.wangjiahang.whimsy.shorturl.bloomfilter.MyBloomFilter;
import cn.wangjiahang.whimsy.shorturl.domain.UrlMapping;
import cn.wangjiahang.whimsy.shorturl.repository.UrlMappingRepository;
import cn.wangjiahang.whimsy.shorturl.util.ShortUrlGenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@Service
@RequiredArgsConstructor
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;
    private final MyBloomFilter bloomFilter;

    public String createShortUrl(String longUrl) {
        return checkByRepository(longUrl, ShortUrlGenUtil.genShortUrl(longUrl));
    }

    public String checkByRepository(String longUrl, String sign) {
        final String shortUrl = ShortUrlGenUtil.buildAllUrl(sign);

        final boolean exists = bloomFilter.check(shortUrl);

        // 布隆过滤器标记为存在 或 未启用布隆过滤器 => 去数据库检查
        if (exists || !bloomFilter.enabled()) {
            final Integer countByShortUrl = urlMappingRepository.getCountByShortUrl(shortUrl);
            if (countByShortUrl != null && countByShortUrl > 0) {
                return shortUrl;
            }
        }

        final UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setLongUrl(longUrl);
        try {
            urlMappingRepository.save(urlMapping);
        } catch (DataIntegrityViolationException e) {
            bloomFilter.put(shortUrl);
            return checkByRepository(longUrl, ShortUrlGenUtil.genShortUrl(longUrl) + UUID.randomUUID().toString().substring(0, 2));
        }

        bloomFilter.put(shortUrl);

        return shortUrl;
    }

    public String getLongUrl(String key) {
        final UrlMapping url = urlMappingRepository.getByShortUrl(ShortUrlGenUtil.buildAllUrl(key));
        return url == null ? "" : url.getLongUrl();
    }
}
