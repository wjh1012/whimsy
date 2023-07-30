package cn.wangjiahang.whimsy.domain.message.po;


import cn.wangjiahang.whimsy.core.repository.BaseEntity;
import cn.wangjiahang.whimsy.domain.message.enums.DeleteFlag;
import cn.wangjiahang.whimsy.domain.message.enums.PublishedMessageStatus;
import cn.wangjiahang.whimsy.domain.message.enums.PublishedMessageType;
import cn.wangjiahang.whimsy.domain.message.enums.converter.DeleteFlagConverter;
import cn.wangjiahang.whimsy.domain.message.enums.converter.PublishedMessageStatusConverter;
import cn.wangjiahang.whimsy.domain.message.enums.converter.PublishedMessageTypeConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

import java.util.Date;


/**
 * 发布的消息
 *
 * @TableName t_published_message
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_published_message")
@Data
public class PublishedMessage extends BaseEntity {

    /**
     * 业务key
     */
    @Column(name = "sourceKey", length = 50, nullable = false)
    @Comment("业务key")
    private String sourceKey;

    /**
     * 交换机
     */
    @Column(name = "exchange", length = 50, nullable = false)
    @Comment("交换机")
    private String exchange;

    /**
     * 路由key
     */
    @Column(name = "routingKey", length = 50, nullable = false)
    @Comment("路由key")
    private String routingKey;

    /**
     * 消息内容
     */
    @Column(name = "content")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Comment("消息内容")
    private String content;

    /**
     * 重试次数
     */
    @Column(name = "retries", length = 4, nullable = false)
    @Comment("重试次数")
    private Integer retries = 0;

    /**
     * 消息过期时间: null为不过期
     */
    @Column(name = "expiry")
    @Comment("消息过期时间: null为不过期")
    private Date expiry;

    /**
     * 消息发送的类型: 1::健康打卡
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = PublishedMessageTypeConverter.class)
    @Comment("消息发送的类型: 1::健康打卡")
    private PublishedMessageType type;

    /**
     * 发送状态，成功则消息ack成功，其他状态都要重试
     */
    @Column(name = "status", nullable = false, length = 10)
    @Convert(converter = PublishedMessageStatusConverter.class)
    @Comment("发送状态，成功则消息ack成功，其他状态都要重试")
    private PublishedMessageStatus status;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    @Comment("备注")
    private String remark;

    @Column(name = "delFlag", nullable = false)
    @Convert(converter = DeleteFlagConverter.class)
    @Comment("刪除状态 0::已删除 1::正常")
    private DeleteFlag delFlag = DeleteFlag.UN_DELETE;

    private static final long serialVersionUID = 1L;

    public boolean hasSendSuccess() {
        return PublishedMessageStatus.ACK.equals(status);
    }
}
