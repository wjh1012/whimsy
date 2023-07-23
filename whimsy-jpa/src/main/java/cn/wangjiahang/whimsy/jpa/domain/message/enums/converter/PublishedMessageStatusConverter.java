package cn.wangjiahang.whimsy.jpa.domain.message.enums.converter;

import cn.wangjiahang.whimsy.jpa.domain.message.enums.PublishedMessageStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PublishedMessageStatusConverter implements AttributeConverter<PublishedMessageStatus, String> {
    @Override
    public String convertToDatabaseColumn(PublishedMessageStatus type) {
        return type == null ? null : type.getCode();
    }

    @Override
    public PublishedMessageStatus convertToEntityAttribute(String value) {
        return PublishedMessageStatus.container.get(value);
    }
}
