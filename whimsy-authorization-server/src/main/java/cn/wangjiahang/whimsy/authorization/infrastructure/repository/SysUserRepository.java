package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser getByUsername(String username);
}
