package cn.wangjiahang.whimsy.shorturl.controller;

import cn.wangjiahang.whimsy.shorturl.service.UrlMappingService;
import cn.wangjiahang.whimsy.shorturl.common.Result;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("url")
@Validated
public class UrlMappingController {
    private final UrlMappingService urlMappingService;

    @PostMapping("createShortUrl")
    public Result<String> createShortUrl(@URL(message = "错误的url") String url) {
        return Result.ok(urlMappingService.createShortUrl(url));
    }

    @GetMapping(value="/{key}")
    public RedirectView redirect (@PathVariable String key) {
        return new RedirectView(urlMappingService.getLongUrl(key));
    }

}
