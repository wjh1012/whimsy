package cn.wangjiahang.whimsy.jpa.advanced.message.service;


import cn.hutool.core.util.StrUtil;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.PublishedMessageStatus;
import cn.wangjiahang.whimsy.jpa.advanced.message.po.PublishedMessage;
import cn.wangjiahang.whimsy.jpa.advanced.message.repository.PublishedMessageRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.PublishedMessageType;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Think
 * @description 针对表【dingtalk_task_agenda(钉钉待办任务表)】的数据库操作Service
 * @createDate 2023-06-07 17:19:16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PublishedMessageService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback  {
	public final Gson gson;
	public final PublishedMessageRepository publishedMessageRepository;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		try {
			if (ack) {
				log.info("消息发送到交换机成功, 消息业务id: {}", correlationData.getId());
				publishedMessageRepository.updateSendSuccess(correlationData.getId());
			} else {
				log.error("消息发送到交换机失败, 消息业务id: {}, 原因是: {}", correlationData.getId(), cause);
				publishedMessageRepository.updateSendFail(correlationData.getId(), PublishedMessageStatus.NACK, cause);
			}
		} catch (Exception e) {
			final String status = ack ? PublishedMessageStatus.ACK.getCode() : PublishedMessageStatus.NACK.getCode();
			log.error(StrUtil.format("confirmMessage时, 保存消息履历失败, 消息状态: {}, 消息业务id: {}", status, correlationData.getId()));
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void returnedMessage(ReturnedMessage returned) {
		try {
			String errMsg = StrUtil.format("replyCode='{}' replyText='{}'", returned.getReplyCode(), returned.getReplyText());
			log.info("消息发送到队列失败, 消息业务id: {}, 原因是: {}", returned.getMessage().getMessageProperties().getCorrelationId(), errMsg);
			publishedMessageRepository.updateSendFail(
					returned.getMessage().getMessageProperties().getMessageId(), PublishedMessageStatus.RETURN, errMsg
			);
		} catch (Exception e) {
			log.error(StrUtil.format("returnedMessage时, 保存消息履历失败, 消息状态: {}, 消息业务id: {}",
					PublishedMessageStatus.RETURN.getCode(), returned.getMessage().getMessageProperties().getMessageId())
			);
		}
	}

	public List<PublishedMessage> getPublishedErrorMsg(int retries, PublishedMessageType... type) {
		return publishedMessageRepository.getPublishedErrorMsg(retries, Arrays.asList(type));
	}

	public void save(Consumer<PublishedMessage> messageConsumer) {
		PublishedMessage message = new PublishedMessage();
		messageConsumer.accept(message);
		publishedMessageRepository.save(message);
	}
}
