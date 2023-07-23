package cn.wangjiahang.whimsy.jpa.advanced.message.mapper;


import cn.wangjiahang.whimsy.jpa.advanced.message.po.PublishedMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Think
* @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Mapper
* @createDate 2023-06-07 17:19:16
* @Entity cn.com.intasect.web.domain.task.po.DingtalkTaskAgenda
*/
@Mapper
public interface PublishedMessageMapper extends BaseMapper<PublishedMessage> {

}




