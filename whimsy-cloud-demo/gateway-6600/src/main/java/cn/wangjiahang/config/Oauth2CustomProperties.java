package cn.wangjiahang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2024/5/19
 */
@Configuration
@ConfigurationProperties("spring.security.custom")
public class Oauth2CustomProperties {
    private String[] ignoreUrl = new String[]{};


    public String[] getIgnoreUrl() {
        return ignoreUrl;
    }
    public void setIgnoreUrl(String[] ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
