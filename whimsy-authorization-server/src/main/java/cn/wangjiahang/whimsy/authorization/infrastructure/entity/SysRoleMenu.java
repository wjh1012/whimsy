package cn.wangjiahang.whimsy.authorization.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * @author jh.wang
 * @since 2023/7/30
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu {
    @EmbeddedId
    private SysRoleMenuPK sysRoleMenu;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SysRoleMenuPK implements Serializable {
        @Column(name = "menuId")
        private Long menuId;
        @Column(name = "roleId")
        private Long roleId;
    }
}
