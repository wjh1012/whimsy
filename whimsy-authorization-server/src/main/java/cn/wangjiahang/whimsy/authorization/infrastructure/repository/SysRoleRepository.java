package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
}
