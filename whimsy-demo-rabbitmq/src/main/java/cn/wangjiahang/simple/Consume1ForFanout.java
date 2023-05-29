package cn.wangjiahang.simple;

import cn.wangjiahang.simple.config.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author jh.wang
 * @since 2023/5/21
 *
 */
@Service
@Slf4j
public class Consume1ForFanout {
    @RabbitListener(queues = {RabbitmqConstant.QUEUE_FANOUT_B, RabbitmqConstant.QUEUE_FANOUT_A})
    public void receiveMsgByAB(Message message) {
        log.info("message for fanout-a,b --> {}", new String(message.getBody()));
    }

    @RabbitListener(queues = {RabbitmqConstant.QUEUE_FANOUT_B})
    public void receiveMsgByB(Message message) {
        log.info("message for fanout-b --> {}", new String(message.getBody()));
    }

    @RabbitListener(queues = {RabbitmqConstant.QUEUE_FANOUT_A})
    public void receiveMsgByA(Message message) {
        log.info("message for fanout-a --> {}", new String(message.getBody()));
    }
}
