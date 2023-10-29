package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysApiMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysApiMenuRepository extends JpaRepository<SysApiMenu, SysApiMenu.SysApiMenuPK> {
}
