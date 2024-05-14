package cn.wangjiahang.api;

import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
