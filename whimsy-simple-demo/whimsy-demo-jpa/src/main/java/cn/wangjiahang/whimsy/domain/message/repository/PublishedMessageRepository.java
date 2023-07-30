package cn.wangjiahang.whimsy.domain.message.repository;


import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.domain.message.enums.PublishedMessageStatus;
import cn.wangjiahang.whimsy.domain.message.enums.PublishedMessageType;
import cn.wangjiahang.whimsy.domain.message.mapper.PublishedMessageMapper;
import cn.wangjiahang.jpa.domain.message.po.QPublishedMessage;
import cn.wangjiahang.whimsy.domain.message.po.PublishedMessage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class PublishedMessageRepository {
	private final PublishedMessageMapper publishedMessageMapper;
	private final JPAQueryFactory queryFactory;

	public List<PublishedMessage> getPublishedErrorMsg(int retries, PublishedMessageType... type) {
//		return this.lambdaQuery()
//				.in(CollUtil.isNotEmpty(type), PublishedMessage::getType, type)
//				.in(PublishedMessage::getStatus, ListUtil.of(PublishedMessageStatus.NACK, PublishedMessageStatus.RETURN))
//				.le(PublishedMessage::getRetries, retries)
//				.orderByAsc(PublishedMessage::getRetries)
//				.last("limit 100")
//				.list();

		QPublishedMessage publishedMessage = QPublishedMessage.publishedMessage;
		final BooleanBuilder builder = new BooleanBuilder().and(type == null ? null : publishedMessage.type.in(type))
				.andAnyOf(publishedMessage.status.eq(PublishedMessageStatus.NACK), publishedMessage.status.eq(PublishedMessageStatus.RETURN))
				.and(publishedMessage.retries.loe(retries));

		return queryFactory.selectFrom(publishedMessage)
				.where(builder)
				.orderBy(publishedMessage.retries.asc())
				.limit(100)
				.fetch();
	}

	public void updateSendSuccess(String sourceKey) {
//		this.lambdaUpdate()
//				.set(PublishedMessage::getStatus, PublishedMessageStatus.ACK)
//				.eq(PublishedMessage::getSourceKey, sourceKey).update();
		QPublishedMessage publishedMessage = QPublishedMessage.publishedMessage;
		queryFactory.update(publishedMessage)
				.set(publishedMessage.status, PublishedMessageStatus.ACK)
				.where(publishedMessage.sourceKey.eq(sourceKey))
				.execute();
	}

	public void updateSendFail(String sourceKey, PublishedMessageStatus status, String exceptionMsg) {
//		this.lambdaUpdate()
//				.setSql("retries = retries + 1")
//				.set(PublishedMessage::getStatus, status)
//				.set(PublishedMessage::getRemark, StrUtil.sub(exceptionMsg, 0, 499))
//				.eq(PublishedMessage::getSourceKey, sourceKey).update();

		QPublishedMessage publishedMessage = QPublishedMessage.publishedMessage;
		queryFactory.update(publishedMessage)
				.set(publishedMessage.retries, publishedMessage.retries.add(1))
				.set(publishedMessage.status, status)
				.set(publishedMessage.remark, StrUtil.sub(exceptionMsg, 0, 499))
				.where(publishedMessage.sourceKey.eq(sourceKey))
				.execute();
	}

	public void save(PublishedMessage message) {
		publishedMessageMapper.save(message);
	}
}




