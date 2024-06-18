package cn.wangjiahang.auth;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author jh.wang
 * @since 2024/5/24
 */
@Component
public class AuthConfig implements ReactiveClientRegistrationRepository {


    @Override
    public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
        return null;
    }
}
