package cn.wangjiahang.whimsy.controller;

import cn.hutool.core.util.URLUtil;
import cn.wangjiahang.whimsy.core.CustomFileResult;
import cn.wangjiahang.whimsy.core.OssClient;
import cn.wangjiahang.whimsy.entity.Attachment;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author jh.wang
 * @since 2023/6/3
 */
@RestController
public class FileCommonController {
    @Autowired
    public OssClient<Attachment> ossClient;

    @PostMapping("upload")
    public void upload(MultipartFile file) throws IOException {
        final CustomFileResult<Attachment> customFileResult = ossClient.putObjectForBusinessId(file.getOriginalFilename(), file.getInputStream(), file.getContentType());
        final Attachment attachment = customFileResult.getAttachment();

        System.out.println("attachment = " + attachment);
    }

    @PostMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        ossClient.downloadForBusinessId("1", response.getOutputStream(), fileResult -> {
            final ObjectMetadata metadata = fileResult.getObjectMetadata();
            response.setContentType(metadata.getContentType());
            response.setContentLengthLong(metadata.getContentLength());
            response.setHeader("Content-Disposition", "attachment; filename=" + URLUtil.encode("文件名"));
        });
    }
}
