package cn.wangjiahang.whimsy.authorization.domain.client;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * @author jh.wang
 * @since 2023/9/3
 */
@Builder
@Getter
@ToString
@Entity
@Table(name = "oauth2_registered_client")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OAuth2RegisteredClient {
    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100, nullable = false)
    private String clientId;

    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false)
    private Long clientIdIssuedAt;

    @Column(length = 200)
    private String clientSecret;

    @Temporal(TemporalType.TIMESTAMP)
    private String clientSecretExpiresAt;

    @Column(length = 200, nullable = false)
    private String clientName;

    @Column(length = 1000, nullable = false)
    private String clientAuthenticationMethods;

    @Column(length = 1000, nullable = false)
    private String authorizationGrantTypes;

    @Column(length = 1000)
    private String redirectUris;

    @Column(length = 1000)
    private String postLogoutRedirectUris;

    @Column(length = 1000, nullable = false)
    private String scopes;

    @Column(length = 2000, nullable = false)
    private String clientSettings;

    @Column(length = 2000, nullable = false)
    private String tokenSettings;
}
