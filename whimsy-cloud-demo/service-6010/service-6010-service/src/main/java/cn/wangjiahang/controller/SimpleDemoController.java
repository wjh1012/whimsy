package cn.wangjiahang.controller;

import cn.wangjiahang.service6000.api.Loadbalancer6000Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jh.wang
 * @since 2024/5/14
 */
@RestController
@RefreshScope
public class SimpleDemoController {

    private static final Logger log = LoggerFactory.getLogger(SimpleDemoController.class);
    @Value("${system.test-nacos:}")
    public String testNacos;

    @RequestMapping("test1")
    public String test1() {
        final String result = testNacos;

        log.info("[{}] SimpleDemoController /test1 result:{}", LocalDateTime.now(), result);
        return result;
    }

    @Autowired
    private Loadbalancer6000Api loadbalancer6000Api;

    @RequestMapping("test2")
    public String test2(@RequestParam(value = "error", defaultValue = "1") Integer error) {
        final String result = loadbalancer6000Api.testPort(error);

        log.info("[{}] SimpleDemoController /test2 result:{}", LocalDateTime.now(), result);

        return result;
    }

    @GetMapping("/message/read")
    public String read() {
        log.info("[{}] SimpleDemoController /message/read result:{}", LocalDateTime.now(), "this is message read");
        return "this is message read";
    }

    @GetMapping("/message/write")
    public String write() {
        log.info("[{}] SimpleDemoController /message/write result:{}", LocalDateTime.now(), "this is message write");
        return "this is message write";
    }

    @GetMapping("/message/delete")
    public String delete() {
        log.info("[{}] SimpleDemoController /message/delete result:{}", LocalDateTime.now(), "this is message delete");
        return "this is message delete";
    }

}
