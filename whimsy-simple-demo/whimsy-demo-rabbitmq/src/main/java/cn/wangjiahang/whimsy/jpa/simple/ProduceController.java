package cn.wangjiahang.whimsy.jpa.simple;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jh.wang
 * Create on 2023/5/21
 */

@RestController
@RequiredArgsConstructor
public class ProduceController {
    private final RabbitTemplate rabbitTemplate;

    /**
     * @param type  rabbitmq交换机名
     * @param count 发送次数
     */
    @GetMapping("produce")
    public void produce(@RequestParam String type, String routingKey, Integer count) {
        for (int i = 0; i < (count == null ? 5 : count); i++) {
            rabbitTemplate.convertAndSend(type, routingKey, "打卡 " + DateUtil.date() + i);
        }
    }

    /**
     * @param type  rabbitmq交换机名
     * @param count 发送次数
     */
    @GetMapping("produce/topic")
    public void produceTopic(@RequestParam String type, String routingKey, Integer count) {
        for (int i = 0; i < (count == null ? 5 : count); i++) {
            rabbitTemplate.convertAndSend(type, routingKey, routingKey + "  " + DateUtil.date() + i);
        }
    }

    /**
     * @param type  rabbitmq交换机名
     * @param count 发送次数
     */
    @GetMapping("produce/headers")
    public void produceHeaders(@RequestParam String type, String factory, Integer count) {
        final Message message = MessageBuilder.withBody("hello".getBytes()).setHeader("factory", factory).build();
        for (int i = 0; i < (count == null ? 5 : count); i++) {
            rabbitTemplate.convertAndSend(type, "", message);
        }
    }
}
