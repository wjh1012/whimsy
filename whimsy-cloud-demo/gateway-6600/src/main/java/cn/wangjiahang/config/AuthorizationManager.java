package cn.wangjiahang.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authority -> this.customCheck(authority, authorizationContext))
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /* @Autowired
    private RedisTemplate<String,Object> redisTemplate; */

    public boolean customCheck(String authority, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();

        // todo 自定义逻辑 url级别
        // redisTemplate.

        return authority != null && !authority.isBlank() && !uri.toString().isBlank();
    }
}
