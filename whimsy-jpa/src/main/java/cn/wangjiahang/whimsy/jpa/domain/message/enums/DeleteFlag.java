package cn.wangjiahang.whimsy.jpa.domain.message.enums;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Converter(autoApply=true)
public enum DeleteFlag {
	DELETED(0, "已删除"),
	UN_DELETE(1, "未删除"),
	;

	private final Integer code;
	private final String remark;

	public static final Map<Integer, DeleteFlag> container;
	static {
		container = Arrays.stream(DeleteFlag.values()).collect(Collectors.toMap(DeleteFlag::getCode, t -> t));
	}
}
