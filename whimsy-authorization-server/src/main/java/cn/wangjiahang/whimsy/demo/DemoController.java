package cn.wangjiahang.whimsy.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jh.wang
 * @since 2023/6/4
 */
@RestController
public class DemoController {

    @GetMapping("ping")
    public String demo(){
        return "ok";
    }

}
