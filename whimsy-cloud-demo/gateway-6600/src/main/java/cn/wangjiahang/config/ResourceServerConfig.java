package cn.wangjiahang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Set;

/**
 * 资源服务器配置
 *
 * @author vains
 */
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class ResourceServerConfig {

    /**
     * 配置认证相关的过滤器链
     *
     * @param http Spring Security的核心配置类
     * @return 过滤器链
     */
    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http,
                                                             AuthorizationManager authorizationManager,
                                                             Oauth2CustomProperties oauth2CustomProperties) {
        // 禁用csrf与cors
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(ServerHttpSecurity.CorsSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);


        // 开启全局验证
        http.authorizeExchange((authorize) -> {
            if (oauth2CustomProperties.getIgnoreUrl().length > 0) {
                authorize.pathMatchers(oauth2CustomProperties.getIgnoreUrl()).permitAll();
            }
            authorize
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    .pathMatchers("/resource/message/read").hasAuthority("SCOPE_message.read")
                    .pathMatchers("/resource/message/write").hasAuthority("SCOPE_message.write")
                    .pathMatchers("/resource/message/delete").hasAuthority("SCOPE_message.delete")
                    // 全部需要认证
                    // .anyExchange().authenticated()
                    // 自定义 认证&鉴权 逻辑
                    .anyExchange().access(authorizationManager);
                }
        );

        // 开启OAuth2登录
        http.oauth2Login(Customizer.withDefaults());

        // 设置当前服务为资源服务，解析请求头中的token
        http.oauth2ResourceServer((resourceServer) -> resourceServer
                        // 使用jwt
                        // .jwt(jwt ->
                        //         jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                        .jwt(Customizer.withDefaults())

                // xhr请求未携带Token处理
                // .authenticationEntryPoint(this::authenticationEntryPoint)
                // 权限不足处理
                // .accessDeniedHandler(this::accessDeniedHandler)
                // Token解析失败处理
                // .authenticationFailureHandler(this::failureHandler)

        );


        return http.build();
    }

}

