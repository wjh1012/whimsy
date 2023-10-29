package cn.wangjiahang.whimsy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.wangjiahang.whimsy.advanced.config.RabbitmqProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jh.wang
 * @since 2023/10/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HealthClockConcurrentTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Autowired
    private Gson gson;

    @Test
    public void clockTest(){
        String clock = "{\"deptId\":400045,\"factory\":\"20001\",\"employeeNo\":\"41075\",\"clockTime\":\"2023-10-29 10:28:38\",\"healthAnswers\":[{\"questionId\":\"1564652806864928\",\"questionName\":\"体质状况\",\"questionType\":\"1\",\"orderNum\":1,\"answerList\":[{\"answerId\":\"1564652808962112\",\"standardAnswer\":\"头痛\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564652808962144\",\"standardAnswer\":\"恶心、拉肚\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564652808962176\",\"standardAnswer\":\"出汗过多\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564652808962208\",\"standardAnswer\":\"肌肉酸痛\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"0\"},{\"answerId\":\"1564652808962240\",\"standardAnswer\":\"感觉过度疲劳\",\"answerRule\":\"3\",\"orderNum\":5,\"isRule\":\"0\"},{\"answerId\":\"1564652808962272\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":6,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1564653582811168\",\"questionName\":\"手指\",\"questionType\":\"1\",\"orderNum\":2,\"answerList\":[{\"answerId\":\"1564653582811232\",\"standardAnswer\":\"起床时，屈伸困难\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564653582811264\",\"standardAnswer\":\"始业前，屈伸困难\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564653582811296\",\"standardAnswer\":\"屈伸感觉不舒服\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564653582811328\",\"standardAnswer\":\"屈伸时有疼痛感\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"0\"},{\"answerId\":\"1564653582811360\",\"standardAnswer\":\"早上有麻木现象\",\"answerRule\":\"3\",\"orderNum\":5,\"isRule\":\"0\"},{\"answerId\":\"1564653582811392\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":6,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1564653809303584\",\"questionName\":\"颈、肩、腕\",\"questionType\":\"1\",\"orderNum\":3,\"answerList\":[{\"answerId\":\"1564653809303648\",\"standardAnswer\":\"肩腕有沉重感\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564653809303680\",\"standardAnswer\":\"肘弯曲时有疼痛感\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564653809303712\",\"standardAnswer\":\"腕活动时有疼痛感\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564653809303744\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1564654039990304\",\"questionName\":\"胸、上背\",\"questionType\":\"1\",\"orderNum\":4,\"answerList\":[{\"answerId\":\"1564654039990368\",\"standardAnswer\":\"深呼吸胸背有疼痛感\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564654039990400\",\"standardAnswer\":\"举手时胸背有疼痛感\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564654039990432\",\"standardAnswer\":\"胸背部个别部位感到不适\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564654039990464\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1564654184693792\",\"questionName\":\"腰、下背\",\"questionType\":\"1\",\"orderNum\":5,\"answerList\":[{\"answerId\":\"1564654184693856\",\"standardAnswer\":\"早上腰部有沉重感\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564654184693888\",\"standardAnswer\":\"前屈时困难\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564654184693920\",\"standardAnswer\":\"活动腰部有疼痛感\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564654184693952\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1564654293745696\",\"questionName\":\"下肢、皮肤\",\"questionType\":\"1\",\"orderNum\":6,\"answerList\":[{\"answerId\":\"1564654295842848\",\"standardAnswer\":\"足部麻木\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"0\"},{\"answerId\":\"1564654295842880\",\"standardAnswer\":\"屈膝疼痛、行走不便\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"0\"},{\"answerId\":\"1564654295842912\",\"standardAnswer\":\"湿疹等皮肤炎症\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"},{\"answerId\":\"1564654295842944\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":4,\"isRule\":\"1\"}],\"answer\":[\"无异常\"]},{\"questionId\":\"1566495628853281\",\"questionName\":\"Q2:你玩原神吗\",\"questionType\":\"2\",\"orderNum\":7,\"answerList\":[{\"answerId\":\"1566495628853345\",\"standardAnswer\":\"oh\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"1\"},{\"answerId\":\"1566495628853377\",\"standardAnswer\":\"my\",\"answerRule\":\"3\",\"orderNum\":2,\"isRule\":\"1\"},{\"answerId\":\"1566495628853409\",\"standardAnswer\":\"gosh\",\"answerRule\":\"3\",\"orderNum\":3,\"isRule\":\"0\"}],\"answer\":[\"oh\",\"my\"]},{\"questionId\":\"1569029254807585\",\"questionName\":\"备注(无异常时，请勿填写)\",\"questionType\":\"4\",\"orderNum\":8,\"answerList\":[{\"answerId\":\"1569029254807649\",\"standardAnswer\":\"无异常\",\"answerRule\":\"3\",\"orderNum\":1,\"isRule\":\"1\"}],\"answer\":[\"1111\"]}]}";

        final JsonObject json = gson.fromJson(clock, JsonObject.class);
        Console.log("开始时间: {}", DateUtil.date().toString("yyyy-MM-dd HH:mm:ss.SSS"));

        for (int i = 0; i < 20; i++) {
            json.addProperty("businessId", IdUtil.objectId());
            final Message message = new Message(json.toString().getBytes());
            rabbitTemplate.send(rabbitmqProperties.clock.exchange, rabbitmqProperties.clock.routing, message);
        }



        Console.log("结束时间: {}", DateUtil.date().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
