package cn.wangjiahang.whimsy.domain.message.enums.converter;

import cn.wangjiahang.whimsy.domain.message.enums.ReceiveMessageStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ReceiveMessageStatusConverter implements AttributeConverter<ReceiveMessageStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReceiveMessageStatus type) {
        return type == null ? null : type.getCode();
    }

    @Override
    public ReceiveMessageStatus convertToEntityAttribute(Integer value) {
        return ReceiveMessageStatus.container.get(value);
    }
}
