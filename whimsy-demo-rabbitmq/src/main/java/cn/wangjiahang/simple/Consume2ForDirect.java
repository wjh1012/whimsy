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
public class Consume2ForDirect {
    @RabbitListener(queues = {RabbitmqConstant.QUEUE_DIRECT_B})
    public void receiveMsgByB(Message message) {
        log.info("message for direct-b --> {}", new String(message.getBody()));
    }

    @RabbitListener(queues = {RabbitmqConstant.QUEUE_DIRECT_A})
    public void receiveMsgByA(Message message) {
        log.info("message for direct-a --> {}", new String(message.getBody()));
    }
}
