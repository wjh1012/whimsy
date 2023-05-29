package cn.wangjiahang.simple;

import cn.wangjiahang.simple.config.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author jh.wang
 * @since 2023/5/21
 */

@Service
@Slf4j
public class Consume3ForTopic {
    @RabbitListener(queues = {RabbitmqConstant.QUEUE_TOPIC_A})
    public void receiveMsgByA(Message message) {
        log.info("message for topic-queue-a --> {}", new String(message.getBody()));
    }

    @RabbitListener(queues = {RabbitmqConstant.QUEUE_TOPIC_B})
    public void receiveMsgByC(Message message) {
        log.info("message for topic-queue-b --> {}", new String(message.getBody()));
    }
}
