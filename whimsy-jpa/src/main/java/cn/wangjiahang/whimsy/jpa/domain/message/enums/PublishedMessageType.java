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
public enum PublishedMessageType {
	HEALTH_CLOCK(1, "健康打卡"),
	;

	private final Integer code;
	private final String remark;

	public static final Map<Integer, PublishedMessageType> container;
	static {
		container = Arrays.stream(PublishedMessageType.values()).collect(Collectors.toMap(PublishedMessageType::getCode, t -> t));
	}

}
