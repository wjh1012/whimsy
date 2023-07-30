package cn.wangjiahang.whimsy.domain.message.service;


import cn.wangjiahang.whimsy.domain.message.enums.PublishedMessageType;
import cn.wangjiahang.whimsy.domain.message.repository.PublishedMessageRepository;
import cn.wangjiahang.whimsy.domain.message.po.PublishedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class PublishedMessageService {
	public final PublishedMessageRepository publishedMessageRepository;


	public List<PublishedMessage> getPublishedErrorMsg(int retries, PublishedMessageType... type) {
		return publishedMessageRepository.getPublishedErrorMsg(retries, type);
	}

	public void save(Consumer<PublishedMessage> messageConsumer) {
		PublishedMessage message = new PublishedMessage();
		messageConsumer.accept(message);
		publishedMessageRepository.save(message);
	}
}
