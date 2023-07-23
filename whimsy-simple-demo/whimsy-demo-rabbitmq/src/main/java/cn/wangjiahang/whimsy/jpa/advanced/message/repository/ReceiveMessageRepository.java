package cn.wangjiahang.whimsy.jpa.advanced.message.repository;


import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.ReceiveMessageStatus;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.jpa.advanced.message.mapper.ReceiveMessageMapper;
import cn.wangjiahang.whimsy.jpa.advanced.message.po.ReceiveMessage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ReceiveMessageRepository extends ServiceImpl<ReceiveMessageMapper, ReceiveMessage> {
	public Optional<ReceiveMessage> getBySourceKey(String sourceKey, ReceiveMessageType receiveMessageType) {
		return this.lambdaQuery().eq(ReceiveMessage::getSourceKey, sourceKey)
				.eq(ReceiveMessage::getType, receiveMessageType).oneOpt();
	}

	public Optional<ReceiveMessage> getByBusinessKey(String businessKey, ReceiveMessageType receiveMessageType) {
		return this.lambdaQuery().eq(ReceiveMessage::getBusinessKey, businessKey)
				.eq(ReceiveMessage::getType, receiveMessageType).oneOpt();
	}

	/**
	 * 根据类型及失败次数获取错误处理的消息
	 *
	 * @param retriesCount
	 * @param receiveMessageType
	 * @return
	 */
	public List<ReceiveMessage> getDealErrorMsg(Integer retriesCount, ReceiveMessageType receiveMessageType) {
		return this.lambdaQuery()
				.eq(receiveMessageType != null, ReceiveMessage::getType, receiveMessageType)
				.eq(ReceiveMessage::getStatus, ReceiveMessageStatus.FAIL)
				.le(ReceiveMessage::getRetries, retriesCount)
				.orderByAsc(ReceiveMessage::getRetries)
				.last("limit 100")
				.list();
	}

	public void updateDealSuccess(Long id) {
		this.lambdaUpdate()
				.set(ReceiveMessage::getStatus, ReceiveMessageStatus.SUCCESS)
				.eq(ReceiveMessage::getId, id).update();
	}

	public void updateDealFail(Long id, String exceptionMsg) {
		this.lambdaUpdate()
				.setSql("retries = retries + 1")
				.set(ReceiveMessage::getStatus, ReceiveMessageStatus.FAIL)
				.set(ReceiveMessage::getRemark, StrUtil.sub(exceptionMsg, 0, 499))
				.eq(ReceiveMessage::getId, id).update();
	}
}




