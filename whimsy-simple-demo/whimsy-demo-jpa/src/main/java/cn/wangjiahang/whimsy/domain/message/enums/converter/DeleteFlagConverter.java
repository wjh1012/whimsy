package cn.wangjiahang.whimsy.domain.message.enums.converter;

import cn.wangjiahang.whimsy.domain.message.enums.DeleteFlag;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DeleteFlagConverter implements AttributeConverter<DeleteFlag, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DeleteFlag type) {
        return type == null ? null : type.getCode();
    }

    @Override
    public DeleteFlag convertToEntityAttribute(Integer value) {
        return DeleteFlag.container.get(value);
    }
}
