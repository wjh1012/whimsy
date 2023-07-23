package cn.wangjiahang.whimsy.jpa.domain.message.enums;

import jakarta.persistence.AttributeConverter;
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
public enum ReceiveMessageStatus {
	/**
	 * 失败
	 */
	FAIL(0),
	/**
     * 成功
     */
    SUCCESS(1),
    ;

    private final Integer code;

    public static final Map<Integer, ReceiveMessageStatus> container;
    static {
        container = Arrays.stream(ReceiveMessageStatus.values()).collect(Collectors.toMap(ReceiveMessageStatus::getCode, t -> t));
    }

}
