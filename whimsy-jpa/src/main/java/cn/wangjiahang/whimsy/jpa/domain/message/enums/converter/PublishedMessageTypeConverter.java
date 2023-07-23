package cn.wangjiahang.whimsy.jpa.domain.message.enums.converter;

import cn.wangjiahang.whimsy.jpa.domain.message.enums.PublishedMessageType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PublishedMessageTypeConverter implements AttributeConverter<PublishedMessageType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PublishedMessageType type) {
        return type == null ? null : type.getCode();
    }

    @Override
    public PublishedMessageType convertToEntityAttribute(Integer value) {
        return PublishedMessageType.container.get(value);
    }
}
