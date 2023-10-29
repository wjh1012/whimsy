package cn.wangjiahang.whimsy.advanced;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author jh.wang
 * Create on 2023/5/28
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class SendMessage {
    private final RabbitmqProperties properties;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("send")
    public String send(Integer size){
        for (int i = 0; i <( size == null ? 1 : size); i++) {
            final Map<Object, Object> data = MapUtil.builder()
                    .put("u", RandomUtil.randomNumbers(5))
                    .put("t", String.valueOf(System.currentTimeMillis()))
                    .put("s", RandomUtil.randomBoolean())
                    .build();

            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                log.info("correlationData：{} , ack:{}", correlationData.getId(), ack);
                if (!ack) {
                    System.out.println("进行对应的消息补偿机制");
                }
            });
            rabbitTemplate.convertAndSend(properties.demo.exchange, properties.demo.routing, data);
        }

        return "{}";
    }
}
