package cn.wangjiahang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@RestController
@RefreshScope
public class DemoController {

    @Value("${system.test-nacos:}")
    public String testNacos;

    @RequestMapping("test1")
    public String test1() {
        return testNacos;
    }


}
