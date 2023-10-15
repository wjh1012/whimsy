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
@Table(name = "sys_role")
public class SysRole extends BaseEntity {
    @Column(length = 30)
    @Comment("角色名称")
    private String name;

    @Column(length = 30)
    @Comment("角色权限字符串")
    private String code;

    @Column(name = "enabled", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("启用状态 启用:1 未启用:0")
    private Integer enabled;

    @Comment("排序")
    private Integer sort;
}
