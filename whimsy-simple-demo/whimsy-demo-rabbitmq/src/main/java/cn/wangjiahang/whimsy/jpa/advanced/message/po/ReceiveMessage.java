package cn.wangjiahang.whimsy.jpa.advanced.message.po;


import cn.wangjiahang.whimsy.jpa.advanced.message.enums.ReceiveMessageStatus;
import cn.wangjiahang.whimsy.jpa.advanced.message.enums.ReceiveMessageType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.wangjiahang.whimsy.jpa.core.BaseEntity;

/**
 * 接收的消息
 *
 * @TableName t_receive_message
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_receive_message")
@Data
public class ReceiveMessage extends BaseEntity {
    /**
     * 业务系统来源key
     */
    private String sourceKey;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 重试次数
     */
    private Integer retries;

    /**
     * 消息发送的类型: 1::发送待办 2::完成待办 3::删除待办 4::发送通知 5::删除通知
     */
    private ReceiveMessageType type;

    /**
     * 处理状态: 0::失败 1::成功
     */
    private ReceiveMessageStatus status;

    public boolean hasDealSuccess(){
        return ReceiveMessageStatus.SUCCESS.equals(status);
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
