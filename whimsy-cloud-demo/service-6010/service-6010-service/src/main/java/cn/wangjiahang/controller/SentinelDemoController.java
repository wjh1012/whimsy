package cn.wangjiahang.controller;

import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jh.wang
 * @since 2024/5/15
 */
@RestController
@RequestMapping("sentinel")
public class SentinelDemoController {

    @Autowired
    private Loadbalancer6000Api loadbalancer6000Api;

    @RequestMapping("test")
    @SentinelResource(value = "remoteInvokeTest", blockHandler = "blockHandler")
    public String test2(@RequestParam(value = "error", defaultValue = "1") Integer error) {
        return loadbalancer6000Api.testPort(error);
    }

    public String blockHandler(Integer error) {
        return "降级";
    }

}
