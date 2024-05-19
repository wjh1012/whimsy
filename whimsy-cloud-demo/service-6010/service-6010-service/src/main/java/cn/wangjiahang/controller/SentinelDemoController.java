package cn.wangjiahang.controller;

import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author jh.wang
 * @since 2024/5/15
 */
@RestController
@RequestMapping("sentinel")
public class SentinelDemoController {

    private static final Logger log = LoggerFactory.getLogger(SentinelDemoController.class);
    @Autowired
    private Loadbalancer6000Api loadbalancer6000Api;

    @RequestMapping("test")
    @SentinelResource(value = "remoteInvokeTest", blockHandler = "blockHandler")
    public String test2(@RequestParam(value = "error", defaultValue = "1") Integer error) {
        final String result = loadbalancer6000Api.testPort(error);

        log.info("[{}] SentinelDemoController /sentinel/test2 result:{}", result, LocalDateTime.now());

        return result;
    }

    public String blockHandler(Integer error) {
        return "降级";
    }

}
