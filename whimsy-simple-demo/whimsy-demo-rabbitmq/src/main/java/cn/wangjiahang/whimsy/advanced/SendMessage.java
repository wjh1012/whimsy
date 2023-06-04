package cn.wangjiahang.whimsy.advanced;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
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
    private final Gson gson;

    @PostMapping("send")
    public Map<String, String> send(){
        final Map<Object, Object> data = MapUtil.builder().put("u", RandomUtil.randomNumbers(5)).put("t", String.valueOf(System.currentTimeMillis())).build();

//        final String jsonData = gson.toJson(data);
        final Clock clock = Clock.builder().t(System.currentTimeMillis()).u(RandomUtil.randomNumbers(5)).build();
        rabbitTemplate.convertAndSend(properties.clock.exchange, properties.clock.routing, clock);

        // log.info("打卡: {}", jsonData)
        return MapUtil.builder("code", "200").build();
    }

    @Data
    @Builder
    static class Clock {
        String u;
        Long t;
    }
}
