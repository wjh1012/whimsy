package cn.wangjiahang.whimsy.authorization.domain.sys.pojo;

import cn.wangjiahang.whimsy.authorization.core.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

/**
 * @author jh.wang
 * @since 2023/10/15
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sys_dict")
public class SysDict extends BaseEntity {
    @Column(name = "dictName", nullable = false, unique = true, length = 50)
    @Comment("字典名称")
    private String dictName;

    @Comment("字典编码")
    @Column(name = "dictCode", nullable = false, unique = true, length = 50)
    private String dictCode;
}
