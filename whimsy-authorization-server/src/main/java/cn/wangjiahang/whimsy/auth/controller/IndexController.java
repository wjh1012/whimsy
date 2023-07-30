package cn.wangjiahang.whimsy.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jh.wang
 * @since 2023/7/23
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index.html", "index"})
    public String index1() {
        return "index";
    }
}
