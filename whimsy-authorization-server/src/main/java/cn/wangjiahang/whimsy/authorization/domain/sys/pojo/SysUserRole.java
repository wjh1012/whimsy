package cn.wangjiahang.whimsy.authorization.domain.sys.pojo;

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
@Table(name = "sys_user_role")
public class SysUserRole {
    @EmbeddedId
    private SysUserRolePK sysUserRolePK;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SysUserRolePK implements Serializable {
        @Column(name = "userId")
        private Long userId;
        @Column(name = "roleId")
        private Long roleId;
    }
}
