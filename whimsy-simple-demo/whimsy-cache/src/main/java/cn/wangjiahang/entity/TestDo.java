package cn.wangjiahang.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author jh.wang
 * @since 2024/5/7
 */
@Data
@ToString
@Accessors(chain = true)
public class TestDo {
    private String id;
    private String name;
}
