package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, SysRoleMenu.SysRoleMenuPK> {
}
