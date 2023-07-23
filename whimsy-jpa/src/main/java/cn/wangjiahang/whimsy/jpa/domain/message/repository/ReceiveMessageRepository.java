package cn.wangjiahang.whimsy.jpa.domain.message.repository;


import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.jpa.domain.message.enums.ReceiveMessageStatus;
import cn.wangjiahang.whimsy.jpa.domain.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.jpa.domain.message.mapper.ReceiveMessageMapper;
import cn.wangjiahang.whimsy.jpa.domain.message.po.QReceiveMessage;
import cn.wangjiahang.whimsy.jpa.domain.message.po.ReceiveMessage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Service实现
 * @createDate 2023-06-07 17:19:16
 */
@Repository
@RequiredArgsConstructor
public class ReceiveMessageRepository {
	private final JPAQueryFactory queryFactory;
	private final ReceiveMessageMapper receiveMessageMapper;

	public Optional<ReceiveMessage> getBySourceKey(String sourceKey, ReceiveMessageType receiveMessageType) {
		// return this.lambdaQuery().eq(ReceiveMessage::getSourceKey, sourceKey).eq(ReceiveMessage::getType, receiveMessageType).oneOpt()
		return receiveMessageMapper.findBySourceKeyAndType(sourceKey, receiveMessageType);
	}

	public Optional<ReceiveMessage> getByBusinessKey(String businessKey, ReceiveMessageType receiveMessageType) {
		// return this.lambdaQuery().eq(ReceiveMessage::getBusinessKey, businessKey).eq(ReceiveMessage::getType, receiveMessageType).oneOpt();
		return receiveMessageMapper.findByBusinessKeyAndType(businessKey, receiveMessageType);
	}

	/**
	 * 根据类型及失败次数获取错误处理的消息
	 *
	 * @param retriesCount
	 * @param receiveMessageType
	 * @return
	 */
	public List<ReceiveMessage> getDealErrorMsg(Integer retriesCount, ReceiveMessageType receiveMessageType) {
//		return this.lambdaQuery()
//				.eq(receiveMessageType != null, ReceiveMessage::getType, receiveMessageType)
//				.eq(ReceiveMessage::getStatus, ReceiveMessageStatus.FAIL)
//				.le(ReceiveMessage::getRetries, retriesCount)
//				.orderByAsc(ReceiveMessage::getRetries)
//				.last("limit 100")
//				.list();
		QReceiveMessage receiveMessage = QReceiveMessage.receiveMessage;

		final BooleanBuilder builder = new BooleanBuilder()
				.and(receiveMessageType == null ? null : receiveMessage.type.eq(receiveMessageType))
				.and(receiveMessage.status.eq(ReceiveMessageStatus.FAIL))
				.and(receiveMessage.retries.loe(retriesCount));

		return queryFactory.selectFrom(receiveMessage)
				.where(builder)
				.orderBy(receiveMessage.retries.asc())
				.limit(100)
				.fetch();
	}

	public void updateDealSuccess(Long id) {
		// this.lambdaUpdate().set(ReceiveMessage::getStatus, ReceiveMessageStatus.SUCCESS).eq(ReceiveMessage::getId, id).update()

		QReceiveMessage receiveMessage = QReceiveMessage.receiveMessage;
		queryFactory.update(receiveMessage)
				.set(receiveMessage.status, ReceiveMessageStatus.SUCCESS)
				.where(receiveMessage.id.eq(id))
				.execute();
	}

	public void updateDealFail(Long id, String exceptionMsg) {
//		this.lambdaUpdate()
//				.setSql("retries = retries + 1")
//				.set(ReceiveMessage::getStatus, ReceiveMessageStatus.FAIL)
//				.set(ReceiveMessage::getRemark, StrUtil.sub(exceptionMsg, 0, 499))
//				.eq(ReceiveMessage::getId, id).update();

		QReceiveMessage receiveMessage = QReceiveMessage.receiveMessage;

		queryFactory.update(receiveMessage)
				.set(receiveMessage.retries, receiveMessage.retries.add(1))
				.set(receiveMessage.status, ReceiveMessageStatus.FAIL)
				.set(receiveMessage.remark, StrUtil.sub(exceptionMsg, 0, 499))
				.where(receiveMessage.id.eq(id))
				.execute();
	}

	public void updateById(ReceiveMessage receiveMessage) {
		receiveMessageMapper.save(receiveMessage);
	}

	public void save(ReceiveMessage receiveMessage) {
		receiveMessageMapper.save(receiveMessage);
	}
}




