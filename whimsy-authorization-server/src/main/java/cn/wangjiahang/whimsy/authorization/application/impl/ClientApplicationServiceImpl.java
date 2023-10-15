package cn.wangjiahang.whimsy.authorization.application.impl;

import cn.hutool.core.util.IdUtil;
import cn.wangjiahang.whimsy.authorization.application.ClientApplicationService;
import cn.wangjiahang.whimsy.authorization.application.command.RegisterClientCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author jh.wang
 * @since 2023/10/15
 */
@RequiredArgsConstructor
@Service
public class ClientApplicationServiceImpl implements ClientApplicationService {
    private final RegisteredClientRepository registeredClientRepository;

    @Override
    public void createClient(RegisterClientCreateCommand registerClientCommand) {
        final List<ClientAuthenticationMethod> clientAuthenticationMethods = registerClientCommand
                .getClientAuthenticationMethod().stream().map(ClientAuthenticationMethod::new).toList();

        final List<AuthorizationGrantType> authorizationGrantTypes = registerClientCommand
                .getAuthorizationGrantType().stream().map(AuthorizationGrantType::new).toList();

        RegisteredClient registeredClient = RegisteredClient.withId(IdUtil.getSnowflakeNextIdStr())
                .clientId(registerClientCommand.getClientId())
                .clientSecret(registerClientCommand.getClientSecret())
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(types -> types.addAll(authorizationGrantTypes))
                .redirectUri(registerClientCommand.getRedirectUri())
                .postLogoutRedirectUri(registerClientCommand.getPostLogoutRedirectUri())
                .scopes(scopes -> scopes.addAll(registerClientCommand.getScope()))
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        registeredClientRepository.save(registeredClient);
    }
}
