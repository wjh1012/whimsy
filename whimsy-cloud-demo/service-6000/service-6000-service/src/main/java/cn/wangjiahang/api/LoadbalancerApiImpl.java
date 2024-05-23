package cn.wangjiahang.api;

import cn.wangjiahang.entity.TestSeata;
import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@RestController
public class LoadbalancerApiImpl implements Loadbalancer6000Api {
    @Value("${spring.application.name} ${server.port}")
    private String port;

    @Override
    public String testPort(@RequestParam(value = "error") Integer error) {
        if (error != null && error.equals(1)) {
            throw new IllegalArgumentException("手动异常");
        }
        return port;
    }

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(Integer error) {
        final TestSeata testSeata = new TestSeata();
        testSeata.setMoney(BigDecimal.valueOf(new Random().nextDouble()));
        final boolean insert = testSeata.insert();

        if (error != null && error.equals(1)) {
            throw new IllegalArgumentException("手动异常");
        }
        if (error != null && error.equals(2)) {
            Thread.sleep(5000);
        }

        return insert ? "保存成功" : "保存失败";
    }
}
