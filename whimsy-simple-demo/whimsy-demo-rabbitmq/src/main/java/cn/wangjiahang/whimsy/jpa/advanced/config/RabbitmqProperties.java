package cn.wangjiahang.whimsy.jpa.advanced.config;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author jh.wang
 * Create on 2023/5/28
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "business.rabbitmq")
public class RabbitmqProperties {
    public Properties demo;

    @PostConstruct
    public void init(){
        this.demo.dead = SpringUtil.getBean("demoDeadProperties", DeadProperties.class);
    }

    @Data
    public static class Properties {
        public String exchange;
        public String queue;
        public String routing;
        public DeadProperties dead;
    }

    @Data
    @Configuration("demoDeadProperties")
    @ConfigurationProperties(prefix = "business.rabbitmq.demo.dead")
    public static class DeadProperties {
        public String exchange;
        public String queue;
        public String routing;
    }
}
