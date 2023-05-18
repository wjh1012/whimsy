package cn.wangjiahang;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jh.wang
 * @since 2023/5/18
 */
@SpringBootApplication
@EnableRabbit
public class RabbitMqDemoApplication {
    public static void main(String[] args)  {
        SpringApplication.run(RabbitMqDemoApplication.class, args);
    }
}
