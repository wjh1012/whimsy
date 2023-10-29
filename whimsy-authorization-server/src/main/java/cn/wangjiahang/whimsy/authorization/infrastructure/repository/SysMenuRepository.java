package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {
}
