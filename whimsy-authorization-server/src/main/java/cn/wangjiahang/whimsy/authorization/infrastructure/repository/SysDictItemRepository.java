package cn.wangjiahang.whimsy.authorization.infrastructure.repository;

import cn.wangjiahang.whimsy.authorization.infrastructure.entity.SysDictItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysDictItemRepository extends JpaRepository<SysDictItem, Long> {
}
