package cn.wangjiahang.whimsy.jpa.advanced.message.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 2023/6/8.
 *
 * @author jh.wang
 */
@AllArgsConstructor
@Getter
public enum ReceiveMessageStatus implements IEnum<Integer> {
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

    @Override
    public Integer getValue() {
        return code;
    }
}
