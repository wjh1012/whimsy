package cn.wangjiahang.whimsy.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.RandomUtil;
import cn.wangjiahang.whimsy.core.CustomFileResult;
import cn.wangjiahang.whimsy.entity.Attachment;
import cn.wangjiahang.whimsy.strategy.businessfile.BusinessFileStrategy;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jh.wang
 * @since 2023/6/3
 */
@Service
@Slf4j
public class AttachmentService implements BusinessFileStrategy<Attachment> {
    private final Map<String, Attachment> map = Maps.newHashMap();

    @Override
    public void deleteAfter(Serializable businessId) {
        map.remove(Convert.toStr(businessId));
    }

    @Override
    public void deleteBefore(Serializable businessId) {
        log.info("delete: {}", businessId);
    }

    @Override
    public Attachment putAfter(CustomFileResult<Attachment> fileResult) {
        final Attachment attachment = new Attachment();
        fileResult.setAttachment(attachment);
        return attachment;
    }

    @Override
    public String convert(Serializable businessId) {
        return "墨墨背单词_1665005584483291136.txt";
    }
}
