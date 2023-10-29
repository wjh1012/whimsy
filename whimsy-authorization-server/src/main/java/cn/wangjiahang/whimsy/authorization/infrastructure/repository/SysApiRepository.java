package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysApi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysApiRepository extends JpaRepository<SysApi, Long> {
}
