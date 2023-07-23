package cn.wangjiahang.whimsy.jpa.core.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 * @author jh.wang
 * @since 2023/7/15
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @CreatedBy
    @Column(name = "createBy", updatable = false)
    private Long createBy;

    @LastModifiedBy
    @Column(name = "updateBy")
    private Long updateBy;

    @CreatedDate
    @Column(name = "createTime", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updateTime")
    private Date updateTime;
}
