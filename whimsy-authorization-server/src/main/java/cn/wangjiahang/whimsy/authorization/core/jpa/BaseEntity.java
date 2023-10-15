package cn.wangjiahang.whimsy.authorization.core.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "delFlag", nullable = false, columnDefinition = "tinyint default 1")
    @Comment("删除标识 正常:1 已删除:0")
    private Integer delFlag;

    @Column(name = "remark", length = 512)
    @Comment("备注")
    private String remark;

    @CreatedBy
    @Column(name = "createBy", updatable = false)
    @Comment("创建人")
    private Long createBy;

    @CreatedDate
    @Column(name = "createTime", updatable = false, columnDefinition = "datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Comment("创建时间")
    private Date createTime;

    @LastModifiedBy
    @Column(name = "updateBy", nullable = false)
    @Comment("修改人")
    private Long updateBy;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updateTime", nullable = false, columnDefinition = "datetime")
    @Comment("修改时间")
    private Date updateTime;
}
