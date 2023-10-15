package cn.wangjiahang.whimsy.authorization.domain.sys.repository;

import cn.wangjiahang.whimsy.authorization.domain.sys.pojo.SysApiMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysApiMenuRepository extends JpaRepository<SysApiMenu, SysApiMenu.SysApiMenuPK> {
}
