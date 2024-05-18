package cn.wangjiahang.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jh.wang
 * @since 2024/5/17
 */
@RestController
public class TokenController {

    private static final Logger log = LoggerFactory.getLogger(TokenController.class);
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("authToken")
    public ResponseEntity<String> authCode2(@RequestParam("code") String code) throws URISyntaxException {

        final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.set("grant_type", "authorization_code");
        body.set("client_id", "messaging-client");
        body.set("client_secret", "123456");
        body.set("redirect_uri", "http://127.0.0.1:6200/authToken");
        body.set("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            return restTemplate.postForEntity(new URI("http://127.0.0.1:6200/oauth2/token"), requestEntity, String.class);
        } catch (RestClientException e) {
            log.error("", e);
            return ResponseEntity.ok(code);
        }
    }

    @RequestMapping("authCode")
    public String authCode(@RequestParam("code") String code) {

        final HashMap<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", "messaging-client");
        body.put("client_secret", "123456");
        body.put("redirect_uri", "http://127.0.0.1:6200/authCode");
        body.put("code", code);

        return String.format("http://127.0.0.1:6200/oauth2/token?grant_type=%s&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                body.get("grant_type"),
                body.get("client_id"),
                body.get("client_secret"),
                body.get("redirect_uri"),
                body.get("code")
        );
    }
}
