package cn.wangjiahang.whimsy.jpa.advanced.message.enums;

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
public enum PublishedMessageType implements IEnum<Integer> {
	HEALTH_CLOCK(1, "健康打卡");

	private final Integer code;
	private final String remark;

	@Override
	public Integer getValue() {
		return code;
	}
}
