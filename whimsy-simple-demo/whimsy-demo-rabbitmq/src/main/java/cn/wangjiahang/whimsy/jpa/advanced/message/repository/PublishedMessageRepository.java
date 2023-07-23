package cn.wangjiahang.whimsy.jpa.advanced.message.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.PublishedMessageStatus;
import cn.wangjiahang.whimsy.jpa.advanced.message.mapper.PublishedMessageMapper;
import cn.wangjiahang.whimsy.jpa.advanced.message.po.PublishedMessage;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.PublishedMessageType;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Service实现
 * @createDate 2023-06-07 17:19:16
 */
@Repository
@RequiredArgsConstructor
public class PublishedMessageRepository extends ServiceImpl<PublishedMessageMapper, PublishedMessage> {

	public List<PublishedMessage> getPublishedErrorMsg(int retries, List<PublishedMessageType> type) {
		return this.lambdaQuery()
				.in(CollUtil.isNotEmpty(type), PublishedMessage::getType, type)
				.in(PublishedMessage::getStatus, ListUtil.of(PublishedMessageStatus.NACK, PublishedMessageStatus.RETURN))
				.le(PublishedMessage::getRetries, retries)
				.orderByAsc(PublishedMessage::getRetries)
				.last("limit 100")
				.list();
	}

	public void updateSendSuccess(String sourceKey) {
		this.lambdaUpdate()
				.set(PublishedMessage::getStatus, PublishedMessageStatus.ACK)
				.eq(PublishedMessage::getSourceKey, sourceKey).update();
	}

	public void updateSendFail(String sourceKey, PublishedMessageStatus status, String exceptionMsg) {
		this.lambdaUpdate()
				.setSql("retries = retries + 1")
				.set(PublishedMessage::getStatus, status)
				.set(PublishedMessage::getRemark, StrUtil.sub(exceptionMsg, 0, 499))
				.eq(PublishedMessage::getSourceKey, sourceKey).update();
	}
}




