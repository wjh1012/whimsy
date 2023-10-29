package cn.wangjiahang.whimsy.authorization.infrastructure.entity;

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
@Table(name = "sys_menu")
public class SysMenu extends BaseEntity {
    @Comment("菜单名称")
    private String name;

    @Column(name = "method")
    @Comment("请求方式, 为空表示全部支持(POST,GET,DELETE,PUT...)")
    private String method;

    @Column(name = "enabled", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("启用状态 启用:1 未启用:0")
    private Integer enabled;
}
