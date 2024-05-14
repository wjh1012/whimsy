package cn.wangjiahang.service6010.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@FeignClient(name = "service-6010-service")
public interface Loadbalancer6010Api {
    @GetMapping(value = "/test/host")
    String testPort();
}
