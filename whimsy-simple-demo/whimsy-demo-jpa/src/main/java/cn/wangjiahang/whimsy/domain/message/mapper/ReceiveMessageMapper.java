package cn.wangjiahang.whimsy.domain.message.mapper;


import cn.wangjiahang.whimsy.domain.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.domain.message.po.ReceiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Mapper
 * @createDate 2023-06-07 17:19:16
 * @Entity cn.com.intasect.web.domain.task.po.DingtalkTaskAgenda
 */
@Repository
public interface ReceiveMessageMapper extends JpaRepository<ReceiveMessage, Long>, QuerydslPredicateExecutor<ReceiveMessage> {
    Optional<ReceiveMessage> findBySourceKeyAndType(String sourceKey, ReceiveMessageType type);

    Optional<ReceiveMessage> findByBusinessKeyAndType(String businessKey, ReceiveMessageType type);
}




