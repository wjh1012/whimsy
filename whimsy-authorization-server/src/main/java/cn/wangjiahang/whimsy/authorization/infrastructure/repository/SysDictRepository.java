package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysDictRepository extends JpaRepository<SysDict, Long> {
}
