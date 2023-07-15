package cn.wangjiahang.whimsy.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author jh.wang
 * @since 2023/6/4
 */
@Data
public class RegisterClientDto {
    @NotBlank(message = "client id 不能为空!")
    private String clientId;
    @NotBlank(message = "client secret 不能为空!")
    private String clientSecret;
    @NotEmpty(message = "client authentication method 不能为空!")
    private Set<String> clientAuthenticationMethod;
    @NotEmpty(message = "authorization grant type 不能为空!")
    private Set<String> authorizationGrantType;
    @NotBlank(message = "redirect uri 不能为空!")
    @URL(message = "redirect uri 不合法!")
    private String redirectUri;
    @NotBlank(message = "post logout redirect uri 不能为空!")
    @URL(message = "post logout redirect uri 不合法!")
    private String postLogoutRedirectUri;
    @NotEmpty(message = "scope 不能为空!")
    private Set<String> scope;
}
