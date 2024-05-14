package cn.wangjiahang.controller;

import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import cn.wangjiahang.service6010.api.Loadbalancer6010Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private Loadbalancer6000Api loadbalancer6000Api;

    @RequestMapping("test2")
    public String test2(@RequestParam(value = "error") Integer error) {
        return loadbalancer6000Api.testPort(error);
    }
}