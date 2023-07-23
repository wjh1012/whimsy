package cn.wangjiahang.whimsy.jpa.config;

import cn.wangjiahang.whimsy.jpa.OssProperties;
import cn.wangjiahang.whimsy.jpa.core.DefaultOssClient;
import cn.wangjiahang.whimsy.jpa.core.OssClient;
import cn.wangjiahang.whimsy.jpa.entity.Attachment;
import cn.wangjiahang.whimsy.jpa.service.AttachmentService;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jh.wang
 * @since 2023/6/3
 */
@Configuration
public class OssConfig {

    @Bean
    public OssClient<Attachment> ossClient(AmazonS3 ossOriginalClient, OssProperties ossProperties, AttachmentService attachmentService){
        final DefaultOssClient<Attachment> ossClient = new DefaultOssClient<>(ossOriginalClient, ossProperties);
        ossClient.setBusinessFileStrategy(attachmentService);
        return ossClient;
    }

}
