package cn.wangjiahang.whimsy.advanced.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jh.wang
 * Create on 2023/5/28
 */
@Configuration
@RequiredArgsConstructor
public class AdvancedRabbitmqExchangeConfig {
    private final RabbitmqProperties properties;

    // ########### demo start ###########

    @Bean
    public DirectExchange demoExchange(){
        return ExchangeBuilder.directExchange(properties.demo.exchange).build();
    }

    /**
     * 转发到 死信队列，配置参数
     */
    private Map<String, Object> deadQueueArgs() {
        Map<String, Object> map = new HashMap<>();
        // 绑定该队列到私信交换机
        map.put("x-dead-letter-exchange", properties.demo.dead.exchange);
        map.put("x-dead-letter-routing-key", properties.demo.dead.routing);
        return map;
    }

    @Bean
    public Queue demoQueue(){
        return QueueBuilder.durable(properties.demo.queue).withArguments(deadQueueArgs()).build();
    }

    @Bean
    public Binding demoExchangeQueue(DirectExchange demoExchange, Queue demoQueue){
        return BindingBuilder.bind(demoQueue).to(demoExchange).with(properties.demo.routing);
    }


    // ########### demo end ###########

    // ########### demo-dead start ###########

    @Bean
    public DirectExchange demoDeadExchange() {
        return ExchangeBuilder.directExchange(properties.demo.dead.exchange).build();
    }

    @Bean
    public Queue demoDeadQueue(){
        return QueueBuilder.durable(properties.demo.dead.queue).build();
    }

    @Bean
    public Binding demoDeadExchangeQueue(DirectExchange demoDeadExchange, Queue demoDeadQueue){
        return BindingBuilder.bind(demoDeadQueue).to(demoDeadExchange).with(properties.demo.dead.routing);
    }

    // ########### demo-dead end ###########


}


