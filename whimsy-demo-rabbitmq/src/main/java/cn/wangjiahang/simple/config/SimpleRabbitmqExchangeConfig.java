package cn.wangjiahang.simple.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2023/5/20
 */
@Configuration
public class SimpleRabbitmqExchangeConfig {
    // ------------------------fanout

    /**
     * 定义一个 扇形交换机
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange(RabbitmqConstant.FANOUT_EXCHANGE).build();
    }

    /**
     * 定义一个 的队列A
     * @return  队列A
     */
    @Bean
    public Queue fanoutQueueA(){
        return new Queue(RabbitmqConstant.QUEUE_FANOUT_A);
    }

    /**
     * 定义一个 的队列B
     * @return  队列B
     */
    @Bean
    public Queue fanoutQueueB(){
        return new Queue(RabbitmqConstant.QUEUE_FANOUT_B);
    }

    /**
     * 将 队列A 绑定至 扇形交换机
     * @param fanoutExchange    扇形交换机
     * @param fanoutQueueA      队列A
     * @return                  绑定对象
     */
    @Bean
    public Binding bindingFanoutQueueA(FanoutExchange fanoutExchange, Queue fanoutQueueA){
        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
    }

    /**
     * 将 队列B 绑定至 扇形交换机
     * @param fanoutExchange    扇形交换机
     * @param fanoutQueueB      队列B
     * @return                  绑定对象
     */
    @Bean
    public Binding bindingFanoutQueueB(FanoutExchange fanoutExchange, Queue fanoutQueueB){
        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
    }



    // ------------------------direct

    /**
     * 定义一个 直连交换机
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitmqConstant.DIRECT_EXCHANGE);
    }


    @Bean
    public Queue directQueueA(){
        return new Queue(RabbitmqConstant.QUEUE_DIRECT_A);
    }

    @Bean
    public Queue directQueueB(){
        return new Queue(RabbitmqConstant.QUEUE_DIRECT_B);
    }

    @Bean
    public Binding bindingDirectQueueA(DirectExchange directExchange, Queue directQueueA){
        return BindingBuilder.bind(directQueueA).to(directExchange).with(RabbitmqConstant.DIRECT_ROUTING_A);
    }

    @Bean
    public Binding bindingDirectQueueB(DirectExchange directExchange, Queue directQueueB){
        return BindingBuilder.bind(directQueueB).to(directExchange).with(RabbitmqConstant.DIRECT_ROUTING_B);
    }


    // ------------------------topic


    /**
     * 定义一个 主题交换机
     * @return DirectExchange
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitmqConstant.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueueA(){
        return new Queue(RabbitmqConstant.QUEUE_TOPIC_A);
    }

    @Bean
    public Queue topicQueueB(){
        return new Queue(RabbitmqConstant.QUEUE_TOPIC_B);
    }

    @Bean
    public Binding bindingTopicQueueA(TopicExchange topicExchange, Queue topicQueueA){
        return BindingBuilder.bind(topicQueueA).to(topicExchange).with(RabbitmqConstant.TOPIC_ROUTING_1);
    }

    @Bean
    public Binding bindingTopicQueueB(TopicExchange topicExchange, Queue topicQueueB){
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with(RabbitmqConstant.TOPIC_ROUTING_2);
    }

    @Bean
    public Binding bindingTopicQueueC(TopicExchange topicExchange, Queue topicQueueB){
        return BindingBuilder.bind(topicQueueB).to(topicExchange).with(RabbitmqConstant.TOPIC_ROUTING_3);
    }



    // ------------------------headers


    /**
     * 定义一个 主题交换机
     * @return DirectExchange
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(RabbitmqConstant.HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headersQueueA(){
        return new Queue(RabbitmqConstant.QUEUE_HEADERS_A);
    }

    @Bean
    public Queue headersQueueB(){
        return new Queue(RabbitmqConstant.QUEUE_HEADERS_B);
    }

    @Bean
    public Binding bindingHeadersQueueA1(HeadersExchange headersExchange, Queue headersQueueA){
        return BindingBuilder.bind(headersQueueA).to(headersExchange).whereAll("factory").exist();
    }

    @Bean
    public Binding bindingHeadersQueueA2(HeadersExchange headersExchange, Queue headersQueueA){
        return BindingBuilder.bind(headersQueueA).to(headersExchange).where("factory").matches("666");
    }

    @Bean
    public Binding bindingHeadersQueueB(HeadersExchange headersExchange, Queue headersQueueB){
        return BindingBuilder.bind(headersQueueB).to(headersExchange).whereAll("factory").exist();
    }
}
