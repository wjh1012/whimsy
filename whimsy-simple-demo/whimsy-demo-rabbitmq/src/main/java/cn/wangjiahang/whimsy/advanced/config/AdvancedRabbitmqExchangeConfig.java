package cn.wangjiahang.whimsy.advanced.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * Create on 2023/5/28
 */
@Configuration
@RequiredArgsConstructor
public class AdvancedRabbitmqExchangeConfig {
    private final RabbitmqProperties properties;

    // clock

    @Bean
    public DirectExchange clockExchange(){
        return ExchangeBuilder.directExchange(properties.clock.exchange).build();
    }

    @Bean
    public Queue clockQueue(){
        return QueueBuilder.durable(properties.clock.queue).build();
    }

    @Bean
    public Binding clockExchangeQueue(DirectExchange clockExchange, Queue clockQueue){
        return BindingBuilder.bind(clockQueue).to(clockExchange).with(properties.clock.routing);
    }

    // notice

    @Bean
    public DirectExchange noticeExchange(){
        return ExchangeBuilder.directExchange(properties.notice.exchange).build();
    }

    @Bean
    public Queue noticeQueue(){
        return QueueBuilder.durable(properties.notice.queue).build();
    }

    @Bean
    public Binding noticeExchangeQueue(DirectExchange noticeExchange, Queue noticeQueue){
        return BindingBuilder.bind(noticeQueue).to(noticeExchange).with(properties.notice.routing);
    }

    // todo

    @Bean
    public DirectExchange todoExchange(){
        return ExchangeBuilder.directExchange(properties.todo.exchange).build();
    }

    @Bean
    public Queue todoQueue(){
        return QueueBuilder.durable(properties.todo.queue).build();
    }

    @Bean
    public Binding todoExchangeQueue(DirectExchange todoExchange, Queue todoQueue){
        return BindingBuilder.bind(todoQueue).to(todoExchange).with(properties.todo.routing);
    }
}


