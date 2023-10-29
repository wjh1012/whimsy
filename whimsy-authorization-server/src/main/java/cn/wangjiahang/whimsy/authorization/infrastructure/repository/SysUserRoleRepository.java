package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, SysUserRole.SysUserRolePK> {
}
