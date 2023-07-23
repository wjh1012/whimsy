package cn.wangjiahang.whimsy.jpa.simple;

import cn.wangjiahang.whimsy.jpa.simple.config.RabbitmqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author jh.wang
 * Create on 2023/5/21
 */

@Service
@Slf4j
public class Consume4ForHeaders {
    @RabbitListener(queues = {RabbitmqConstant.QUEUE_HEADERS_A})
    public void receiveMsgByA(Message message) {
        log.info("message for headers-queue-a --> {}", new String(message.getBody()));
    }

    @RabbitListener(queues = {RabbitmqConstant.QUEUE_HEADERS_B})
    public void receiveMsgByC(Message message) {
        log.info("message for headers-queue-b --> {}", new String(message.getBody()));
    }
}
