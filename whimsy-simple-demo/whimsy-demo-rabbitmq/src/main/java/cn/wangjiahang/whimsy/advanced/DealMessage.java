package cn.wangjiahang.whimsy.advanced;

import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import cn.wangjiahang.whimsy.advanced.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.advanced.message.service.ReceiveMessageService;
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
    public final ReceiveMessageService receiveMessageService;

    @RabbitListener(queues = "${business.rabbitmq.delete-notice.queue}", ackMode = "MANUAL")
    @SneakyThrows
    public void deal(Message message, Channel channel) {
        long startTime = System.currentTimeMillis();

        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        String sourceKey = message.getMessageProperties().getMessageId();

        try {
            // do thing...

            // 保存履历
            receiveMessageService.saveSuccessMessage(sourceKey, po ->{
                po.setContent(body);
                po.setSourceKey(sourceKey);
                po.setType(ReceiveMessageType.DELETE_NOTICE);
            });
            // do thing...
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("xxx处理成功, 消息业务id: {}, 耗时: {}毫秒", sourceKey, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error(StrUtil.format("xxx处理失败, 消息业务id: {}, 消息内容: {}", sourceKey, body), e);
            receiveMessageService.saveFailMessage(sourceKey, po ->{
                po.setContent(body);
                po.setType(ReceiveMessageType.DELETE_NOTICE);
                po.setSourceKey(sourceKey);
                po.setRemark(e.getMessage());
            });
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
