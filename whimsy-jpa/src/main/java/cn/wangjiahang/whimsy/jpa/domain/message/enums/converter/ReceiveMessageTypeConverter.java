package cn.wangjiahang.whimsy.jpa.domain.message.enums.converter;


import cn.wangjiahang.whimsy.jpa.domain.message.enums.ReceiveMessageType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ReceiveMessageTypeConverter implements AttributeConverter<ReceiveMessageType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReceiveMessageType type) {
        return type == null ? null : type.getCode();
    }

    @Override
    public ReceiveMessageType convertToEntityAttribute(Integer value) {
        return ReceiveMessageType.container.get(value);
    }
}
