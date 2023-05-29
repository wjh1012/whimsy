package cn.wangjiahang.advanced.config;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jh.wang
 * @since 2023/5/28
 */
@Data
@Component
@ConfigurationProperties(prefix = "business.rabbitmq")
public class RabbitmqProperties {
    public Properties clock;
    public Properties todo;
    public Properties notice;

    @Setter
    public static class Properties {
        public String exchange;
        public String queue;
        public String routing;
    }
}
