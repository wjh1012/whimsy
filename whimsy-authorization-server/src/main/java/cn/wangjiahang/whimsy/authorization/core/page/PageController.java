package cn.wangjiahang.whimsy.authorization.core.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jh.wang
 * @since 2023/10/22
 */
@Controller
public class PageController {
    @RequestMapping({"index", ""})
    public String index() {
        return "index";
    }
}
