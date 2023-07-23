package cn.wangjiahang.whimsy.jpa.domain.message.mapper;


import cn.wangjiahang.whimsy.jpa.domain.message.po.PublishedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Mapper
 * @createDate 2023-06-07 17:19:16
 * @Entity cn.com.intasect.web.domain.task.po.DingtalkTaskAgenda
 */
@Repository
public interface PublishedMessageMapper extends JpaRepository<PublishedMessage, Long>, QuerydslPredicateExecutor<PublishedMessage> {

}




