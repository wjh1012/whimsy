package cn.wangjiahang.service6000.config;

import cn.wangjiahang.service6000.api.Loadbalancer6000ApiFallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@Configuration
public class FallbackConfig {

    @Bean
    @ConditionalOnMissingBean
    public Loadbalancer6000ApiFallback loadbalancer6000ApiFallback() {
        return new Loadbalancer6000ApiFallback();
    }
}
