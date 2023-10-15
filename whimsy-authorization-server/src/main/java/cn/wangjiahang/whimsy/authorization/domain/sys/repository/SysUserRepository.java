package cn.wangjiahang.whimsy.authorization.domain.sys.repository;

import cn.wangjiahang.whimsy.authorization.domain.sys.pojo.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
}
