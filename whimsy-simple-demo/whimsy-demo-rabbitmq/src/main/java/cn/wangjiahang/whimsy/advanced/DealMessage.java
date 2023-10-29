package cn.wangjiahang.whimsy.advanced;

import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

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

    @RabbitListener(queues = "${business.rabbitmq.demo.queue}", ackMode = "MANUAL")
    @SneakyThrows
    public void deal(Message message, Channel channel) {
        long startTime = System.currentTimeMillis();

        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String sourceKey = message.getMessageProperties().getMessageId();

        try {
            // do thing...
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("xxx处理成功, 消息业务id: {}, 耗时: {}毫秒", sourceKey, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error(StrUtil.format("xxx处理失败, 消息业务id: {}, 消息内容: {}", sourceKey, body), e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
