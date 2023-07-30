package cn.wangjiahang.whimsy.advanced.message.po;


import cn.wangjiahang.whimsy.advanced.message.enums.PublishedMessageStatus;
import cn.wangjiahang.whimsy.advanced.message.enums.PublishedMessageType;
import cn.wangjiahang.whimsy.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 发布的消息
 *
 * @TableName t_published_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_published_message")
@Data
public class PublishedMessage extends BaseEntity {

    /**
     * 业务key
     */
    private String sourceKey;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 路由key
     */
    private String routingKey;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 重试次数
     */
    private Integer retries;

    /**
     * 消息过期时间: null为不过期
     */
    private Date expiry;

    /**
     * 消息发送的类型: 1::健康打卡
     */
    private PublishedMessageType type;

    /**
     * 发送状态，成功则消息ack成功，其他状态都要重试
     */
    private PublishedMessageStatus status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public boolean hasSendSuccess() {
        return PublishedMessageStatus.ACK.equals(status);
    }
}
