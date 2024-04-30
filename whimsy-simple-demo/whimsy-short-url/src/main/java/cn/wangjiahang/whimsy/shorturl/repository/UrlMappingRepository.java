package cn.wangjiahang.whimsy.shorturl.repository;

import cn.wangjiahang.whimsy.shorturl.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    @Query("select count(1) from UrlMapping where shortUrl = ?1")
    Integer getCountByShortUrl(String shortUrl);

    UrlMapping getByShortUrl(String shortUrl);
}
