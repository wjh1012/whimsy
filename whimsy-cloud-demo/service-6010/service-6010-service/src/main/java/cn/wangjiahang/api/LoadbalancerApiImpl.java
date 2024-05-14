package cn.wangjiahang.api;

import cn.wangjiahang.service6010.api.Loadbalancer6010Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@RestController
public class LoadbalancerApiImpl implements Loadbalancer6010Api {
    @Value("${spring.application.name} ${server.port}")
    private String port;

    @Override
    public String testPort() {
        return port;
    }
}
