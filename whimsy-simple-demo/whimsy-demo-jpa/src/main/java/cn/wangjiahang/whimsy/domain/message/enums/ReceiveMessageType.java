package cn.wangjiahang.whimsy.domain.message.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2023/6/8.
 *
 * @author jh.wang
 */
@AllArgsConstructor
@Getter
public enum ReceiveMessageType {
	/**
	 * 发送待办任务
	 */
	SEND_TODO_TASK(1, "发送待办任务"),
	/**
	 * 完成待办任务
	 */
	DONE_TODO_TASK(2, "完成待办任务"),
	/**
	 * 删除待办任务
	 */
	DELETE_TODO_TASK(3, "删除待办任务"),
	/**
	 * 发送通知
	 */
	SEND_NOTICE(4, "发送通知"),
	/**
	 * 删除通知
	 */
	DELETE_NOTICE(5, "删除通知"),
	;

	private final Integer code;
	private final String remark;

	public static final Map<Integer, ReceiveMessageType> container;
	static {
		container = Arrays.stream(ReceiveMessageType.values()).collect(Collectors.toMap(ReceiveMessageType::getCode, t -> t));
	}
}
