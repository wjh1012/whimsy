package cn.wangjiahang.whimsy.config;

import cn.wangjiahang.whimsy.OssProperties;
import cn.wangjiahang.whimsy.core.DefaultOssClient;
import cn.wangjiahang.whimsy.core.OssClient;
import cn.wangjiahang.whimsy.entity.Attachment;
import cn.wangjiahang.whimsy.service.AttachmentService;
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
