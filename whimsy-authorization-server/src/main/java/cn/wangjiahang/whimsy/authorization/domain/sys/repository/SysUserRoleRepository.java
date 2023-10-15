package cn.wangjiahang.whimsy.authorization.domain.sys.repository;

import cn.wangjiahang.whimsy.authorization.domain.sys.pojo.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, SysUserRole.SysUserRolePK> {
}
