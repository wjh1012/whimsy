package cn.wangjiahang.whimsy.jpa.simple.config;

/**
 * @author jh.wang
 * Create on 2023/5/20
 */

public interface RabbitmqConstant {
    // exchange

    /**
     * 扇形交换机
     * fanout.exchange
     */
    String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 直连交换机
     * direct.exchange
     */
    String DIRECT_EXCHANGE = "direct.exchange";

    /**
     * 主题交换机
     * direct.exchange
     */
    String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * 头部交换机
     * headers.exchange
     */
    String HEADERS_EXCHANGE = "headers.exchange";

    // queue

    /**
     * 扇形交换机: fanout.exchange
     * 队列a: queue.fanout.a
     */
    String QUEUE_FANOUT_A = "queue.fanout.a";

    /**
     * 扇形交换机: fanout.exchange
     * 队列a: queue.fanout.b
     */
    String QUEUE_FANOUT_B = "queue.fanout.b";


    /**
     * 直连交换机: direct.exchange
     * 队列a: queue.direct.a
     */
    String QUEUE_DIRECT_A = "queue.direct.a";

    /**
     * 直连交换机: direct.exchange
     * 队列a: queue.direct.b
     */
    String QUEUE_DIRECT_B = "queue.direct.b";



    String QUEUE_TOPIC_A = "queue.topic.a";
    String QUEUE_TOPIC_B = "queue.topic.b";


    String QUEUE_HEADERS_A = "queue.headers.a";
    String QUEUE_HEADERS_B = "queue.headers.b";



    // routing

    /**
     * 直连交换机: direct.exchange
     * 队列a: queue.direct.a
     * 路由a: direct.routing.a
     */
    String DIRECT_ROUTING_A = "direct.routing.a";

    /**
     * 直连交换机: direct.exchange
     * 队列b: queue.direct.b
     * 路由b: direct.routing.b
     */
    String DIRECT_ROUTING_B = "direct.routing.b";


    String TOPIC_ROUTING_1 = "topic.routing.#";


    String TOPIC_ROUTING_2 = "topic.*";

    String TOPIC_ROUTING_3 = "topic.routing.b";

}
