package cn.wangjiahang.whimsy.authorization.domain.client.repository;

import cn.wangjiahang.whimsy.authorization.domain.client.OAuth2RegisteredClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author jh.wang
 * @since 2023/9/3
 */
@Repository
public interface ClientRegisteredRepository
        extends JpaRepository<OAuth2RegisteredClient, String>, QuerydslPredicateExecutor<OAuth2RegisteredClient> {

}
