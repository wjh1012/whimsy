package cn.wangjiahang.whimsy.authorization.infrastructure.entity;

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
@Table(name = "sys_dict_item")
public class SysDictItem extends BaseEntity {
    @Column(name = "dictId", nullable = false)
    @Comment("字典类型id")
    private Long dictId;

    @Column(name = "itemText", nullable = false, length = 200)
    @Comment("字典项名称")
    private String itemText;

    @Comment("字典项值")
    @Column(name = "itemValue", nullable = false, length = 200)
    private String itemValue;

    @Comment("字典项排序")
    @Column(name = "sortOrder")
    private Integer sortOrder;
}
