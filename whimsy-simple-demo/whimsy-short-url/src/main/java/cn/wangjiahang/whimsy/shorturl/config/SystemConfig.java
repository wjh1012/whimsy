package cn.wangjiahang.whimsy.shorturl.config;

import cn.wangjiahang.whimsy.shorturl.util.ShortUrlGenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@Configuration
public class SystemConfig {
    @Value("${domain}")
    private String domain;

    @PostConstruct
    public void init() {
        // 初始化 短链系统 baseUrl
        ShortUrlGenUtil.domain = domain;
    }
}
