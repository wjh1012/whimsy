package cn.wangjiahang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jh.wang
 * @since 2024/6/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("test1")
public class Entity extends Model<Entity> {
    private Integer id;
    private String f1;
    private String f2;
    private String f3;
}
