package cn.wangjiahang.whimsy.jpa;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author jh.wang
 * Create on 2023/5/18
 */
@SpringBootApplication
@EnableRabbit
@EnableScheduling
@EnableSpringUtil
public class RabbitMqDemoProduceApplication7000 {
    public static void main(String[] args)  {
        SpringApplication.run(RabbitMqDemoProduceApplication7000.class, args);
    }
}
