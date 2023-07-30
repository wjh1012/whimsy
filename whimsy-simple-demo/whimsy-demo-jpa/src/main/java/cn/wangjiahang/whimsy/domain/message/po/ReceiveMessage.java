package cn.wangjiahang.whimsy.domain.message.po;


import cn.wangjiahang.whimsy.domain.message.enums.DeleteFlag;
import cn.wangjiahang.whimsy.domain.message.enums.ReceiveMessageStatus;
import cn.wangjiahang.whimsy.domain.message.enums.ReceiveMessageType;
import cn.wangjiahang.whimsy.domain.message.enums.converter.DeleteFlagConverter;
import cn.wangjiahang.whimsy.domain.message.enums.converter.ReceiveMessageStatusConverter;
import cn.wangjiahang.whimsy.domain.message.enums.converter.ReceiveMessageTypeConverter;
import cn.wangjiahang.whimsy.core.repository.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Comment;

/**
 * 接收的消息
 *
 * @TableName t_receive_message
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_receive_message")
@Entity
@Data
public class ReceiveMessage extends BaseEntity {
    /**
     * 业务系统来源key
     */
    @Column(name = "sourceKey", length = 50, nullable = false)
    @Comment("业务系统来源key")
    private String sourceKey;

    /**
     * 业务key
     */
    @Column(name = "businessKey", length = 50)
    @Comment("业务key")
    private String businessKey;

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
    @Column(name = "retries", nullable = false)
    @Comment("重试次数")
    private Integer retries = 0;

    /**
     * 消息发送的类型: 1::发送待办 2::完成待办 3::删除待办 4::发送通知 5::删除通知
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = ReceiveMessageTypeConverter.class)
    @Comment("消息发送的类型: 1::发送待办 2::完成待办 3::删除待办 4::发送通知 5::删除通知")
    private ReceiveMessageType type;

    /**
     * 处理状态: 0::失败 1::成功
     */
    @Column(name = "status", nullable = false)
    @Convert(converter = ReceiveMessageStatusConverter.class)
    @Comment("处理状态: 0::失败 1::成功")
    private ReceiveMessageStatus status = ReceiveMessageStatus.SUCCESS;

    /**
     * 备注
     */
    @Column(name = "remark", length = 500)
    @Comment("备注")
    private String remark;

    @Column(name = "delFlag")
    @Convert(converter = DeleteFlagConverter.class)
    @Comment("刪除状态 0::已删除 1::正常")
    private DeleteFlag delFlag = DeleteFlag.UN_DELETE;

    public boolean hasDealSuccess() {
        return ReceiveMessageStatus.SUCCESS.equals(status);
    }

    private static final long serialVersionUID = 1L;
}
