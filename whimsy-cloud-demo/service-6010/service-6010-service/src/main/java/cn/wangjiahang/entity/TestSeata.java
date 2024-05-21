package cn.wangjiahang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author jh.wang
 * @since 2024/5/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("test_seata6010")
public class TestSeata extends Model<TestSeata> {
    @TableId
    private Long id;
    private BigDecimal money;
}
