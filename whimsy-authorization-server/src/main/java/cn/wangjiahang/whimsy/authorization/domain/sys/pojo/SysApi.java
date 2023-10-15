package cn.wangjiahang.whimsy.authorization.domain.sys.pojo;

import cn.wangjiahang.whimsy.authorization.core.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * @author jh.wang
 * @since 2023/7/30
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sys_api")
public class SysApi extends BaseEntity {
    @Comment("请求路径")
    private String path;

    @Column(name = "method", length = 50)
    @Comment("请求方式, 为空表示全部支持(POST,GET,DELETE,PUT...)")
    private String method;

    @Column(name = "enabled", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("启用状态 启用:1 未启用:0")
    private Integer enabled;
}
