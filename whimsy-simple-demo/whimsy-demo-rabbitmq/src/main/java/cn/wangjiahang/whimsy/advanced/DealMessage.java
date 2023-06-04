package cn.wangjiahang.whimsy.advanced;

import cn.hutool.core.thread.ThreadUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author jh.wang
 * Create on 2023/5/28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DealMessage {
    public final RabbitmqProperties properties;
    public final Gson gson;

    @RabbitListener(queues = "${business.rabbitmq.clock.queue}", replyContentType = "application/json", ackMode = "MANUAL")
    public void clock1(Clock msg, Message message, Channel channel) throws IOException {
        ThreadUtil.sleep(500);
        log.info("clock1处理打卡: {}-{}", msg.t, msg.u);

        doThing(msg, message, channel);
    }

    @RabbitListener(queues = "${business.rabbitmq.clock.queue}", replyContentType = "application/json", ackMode = "MANUAL")
    public void clock2(@Payload Clock msg, Message message, Channel channel) throws IOException {
        ThreadUtil.sleep(450);
        log.info("clock2处理打卡: {}-{}", msg.t, msg.u);

        doThing(msg, message, channel);
    }

    private void doThing(@Payload Clock msg, Message message, Channel channel) throws IOException {
        try {
            if (msg.u.contains("5")) {
                throw new RuntimeException();
            }
            // do thing...
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 消息被处理过
            if (message.getMessageProperties().getRedelivered()) {
                log.warn("【{}】的该消息被拒绝过，不再放回到队列：{}", msg.u, gson.toJson(msg));
                // 被拒绝一次之后，不再放到队列中
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
            log.warn("【{}】打卡的消息第一次被拒绝，再放回到队列：{}", msg.u, gson.toJson(msg));
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

    @Data
    static class Clock {
        String u;
        Long t;
    }
}
