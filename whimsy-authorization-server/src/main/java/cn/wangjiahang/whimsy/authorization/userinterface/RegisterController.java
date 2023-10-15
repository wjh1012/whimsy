package cn.wangjiahang.whimsy.authorization.userinterface;

import cn.wangjiahang.whimsy.authorization.application.ClientApplicationService;
import cn.wangjiahang.whimsy.authorization.application.command.RegisterClientCreateCommand;
import cn.wangjiahang.whimsy.authorization.core.result.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author jh.wang
 * @since 2023/6/4
 */
@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class RegisterController {

    private final ClientApplicationService clientApplicationService;

    @PostMapping("register")
    public ApiResponse<?> registerClient(@RequestBody @Validated RegisterClientCreateCommand registerClientCommand){
        clientApplicationService.createClient(registerClientCommand);
        return ApiResponse.success();
    }

    @PostMapping("user")
    public void registerUser(){
//        jdbcUserDetailsManager.createUser();
    }

}
