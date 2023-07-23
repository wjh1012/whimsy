package cn.wangjiahang.whimsy.jpa.domain.message.service;


import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.jpa.domain.message.enums.ReceiveMessageStatus;
import cn.wangjiahang.whimsy.jpa.domain.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.jpa.domain.message.po.ReceiveMessage;
import cn.wangjiahang.whimsy.jpa.domain.message.repository.ReceiveMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Service
 * @createDate 2023-06-07 17:19:16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiveMessageService {
	private final ReceiveMessageRepository receiveMessageRepository;

	/**
	 * 保存消息
	 * @param dealSuccess   消息处理成功或失败
	 * @param sourceKey     消息来源的key
	 * @param function      构建消息
	 */
	public void saveMessage(boolean dealSuccess, String sourceKey, Consumer<ReceiveMessage> function) {
		final ReceiveMessage receiveMessage = new ReceiveMessage();
		function.accept(receiveMessage);

		receiveMessage.setSourceKey(StrUtil.nullToDefault(sourceKey, ""));
		receiveMessage.setRemark(StrUtil.sub(receiveMessage.getRemark(), 0, 499));
		if (receiveMessage.getContent() == null) {
			receiveMessage.setContent("");
		}
		if (receiveMessage.getStatus() == null) {
			receiveMessage.setStatus(dealSuccess ? ReceiveMessageStatus.SUCCESS : ReceiveMessageStatus.FAIL);
		}

		Optional<ReceiveMessage> messageOpt = receiveMessageRepository.getBySourceKey(sourceKey, receiveMessage.getType());
		// 不存在则保存
		if (messageOpt.isEmpty()) {
			receiveMessageRepository.save(receiveMessage);
			return;
		}

		// 存在则更新, 重试次数 +1
		receiveMessage.setId(messageOpt.get().getId());
		receiveMessage.setRetries(messageOpt.get().getRetries() + 1);
		receiveMessageRepository.updateById(receiveMessage);
	}

	/**
	 * 保存成功的消息
	 * @param sourceKey     消息来源的key
	 * @param function      构建消息
	 */
	public void saveSuccessMessage(String sourceKey, Consumer<ReceiveMessage> function) {
		// 在调用方处理异常
		saveMessage(true, sourceKey, function);
	}

	/**
	 * 保存错误的消息
	 * @param sourceKey     消息来源的key
	 * @param function      构建消息
	 */
	public void saveFailMessage(String sourceKey, Consumer<ReceiveMessage> function) {
		try {
			saveMessage(false, sourceKey, function);
		} catch (Exception e) {
			log.error(StrUtil.format("错误消息处理保存失败, 消息业务id: {}", sourceKey), e);
		}
	}

	public Optional<ReceiveMessage> getMessageBySourceKey(String sourceKey, ReceiveMessageType receiveMessageType) {
		return receiveMessageRepository.getBySourceKey(sourceKey, receiveMessageType);
	}

	public Optional<ReceiveMessage> getMessageByBusinessKey(String businessKey, ReceiveMessageType receiveMessageType) {
		return receiveMessageRepository.getByBusinessKey(businessKey, receiveMessageType);
	}

	/**
	 * 根据错误类型获取处理错误的消息
	 * @param retriesCount
	 * @param receiveMessageType    为空则查全部类型
	 * @return
	 */
	public List<ReceiveMessage> getSendErrorMsg(Integer retriesCount, ReceiveMessageType receiveMessageType) {
		return receiveMessageRepository.getDealErrorMsg(retriesCount, receiveMessageType);
	}

	public void dealFail(Long id, String exceptionMsg) {
		receiveMessageRepository.updateDealFail(id, exceptionMsg);
	}

	public void dealSuccess(Long id) {
		receiveMessageRepository.updateDealSuccess(id);
	}
}
