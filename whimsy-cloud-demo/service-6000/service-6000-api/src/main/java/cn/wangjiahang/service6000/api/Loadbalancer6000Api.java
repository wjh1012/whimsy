package cn.wangjiahang.service6000.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@FeignClient(name = "service-6000", fallback = Loadbalancer6000ApiFallback.class)
public interface Loadbalancer6000Api {
    @GetMapping(value = "/test/host")
    String testPort(@RequestParam(value = "error") Integer error);

    @GetMapping(value = "/save")
    String save(@RequestParam(value = "error") Integer error);
}
