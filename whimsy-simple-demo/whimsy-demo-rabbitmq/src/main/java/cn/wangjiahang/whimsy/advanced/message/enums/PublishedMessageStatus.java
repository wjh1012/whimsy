package cn.wangjiahang.whimsy.advanced.message.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 2023/6/30.
 *
 * @author jh.wang
 */
@AllArgsConstructor
@Getter
public enum PublishedMessageStatus implements IEnum<String> {
	RUNNING("running"),
	ACK("ack"),
	NACK("nack"),
	RETURN("return");

	private final String code;

	@Override
	public String getValue() {
		return code;
	}
}
