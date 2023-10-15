package cn.wangjiahang.whimsy.authorization.domain.sys;

import cn.wangjiahang.whimsy.authorization.domain.client.repository.ClientRegisteredRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

/**
 * @author jh.wang
 * @since 2023/9/3
 */
@Service
public class ClientRegisteredService extends JdbcRegisteredClientRepository implements RegisteredClientRepository {
    private final ClientRegisteredRepository clientRegisteredRepository;

    public ClientRegisteredService(JdbcOperations jdbcOperations, ClientRegisteredRepository clientRegisteredRepository) {
        super(jdbcOperations);
        this.clientRegisteredRepository = clientRegisteredRepository;
    }


}
