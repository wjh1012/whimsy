package cn.wangjiahang.whimsy.jpa.controller;

import cn.wangjiahang.whimsy.jpa.bo.RegisterClientDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author jh.wang
 * @since 2023/6/4
 */
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final RegisteredClientRepository registeredClientRepository;
    private final UserDetailsManager jdbcUserDetailsManager;

    @PostMapping("register/client")
    public void registerClient(@RequestBody @Validated RegisterClientDto registerClient){

        final List<ClientAuthenticationMethod> clientAuthenticationMethods = registerClient
                .getClientAuthenticationMethod().stream().map(ClientAuthenticationMethod::new).toList();

        final List<AuthorizationGrantType> authorizationGrantTypes = registerClient
                .getAuthorizationGrantType().stream().map(AuthorizationGrantType::new).toList();

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(registerClient.getClientId())
                .clientSecret(registerClient.getClientSecret())
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(types -> types.addAll(authorizationGrantTypes))
                .redirectUri(registerClient.getRedirectUri())
                .postLogoutRedirectUri(registerClient.getPostLogoutRedirectUri())
                .scopes(scopes -> scopes.addAll(registerClient.getScope()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        registeredClientRepository.save(registeredClient);
    }


    @PostMapping("register/user")
    public void registerUser(){

    }

}
