package cn.wangjiahang.whimsy.shorturl.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author jh.wang
 * @since 2024/4/28
 */
@Entity
@Data
@Accessors(chain = true)
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortUrl;
    private String longUrl;
}
