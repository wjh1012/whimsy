package cn.wangjiahang.whimsy.jpa.domain.message.enums;

import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2023/6/30.
 *
 * @author jh.wang
 */
@AllArgsConstructor
@Getter
public enum PublishedMessageStatus {
	RUNNING("running"),
	ACK("ack"),
	NACK("nack"),
	RETURN("return");

	private final String code;

	public static final Map<String, PublishedMessageStatus> container;
	static {
		container = Arrays.stream(PublishedMessageStatus.values()).collect(Collectors.toMap(PublishedMessageStatus::getCode, t -> t));
	}
}
