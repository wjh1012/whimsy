package cn.wangjiahang.whimsy.core;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.wangjiahang.whimsy.OssProperties;
import cn.wangjiahang.whimsy.strategy.filename.FilenameSignResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;

/**
 * @author jh.wang
 * @since 2023/6/1
 */
@RequiredArgsConstructor
public class DefaultOssClient<T> extends AbstractOssClient<T> {
    private final AmazonS3 amazonS3;
    private final OssProperties ossProperties;

    @Override
    public AmazonS3 amazonS3Client() {
        return amazonS3;
    }

    @Override
    public OssProperties ossProperties() {
        return ossProperties;
    }

    @Override
    public void deleteObject(String objectName) {
        amazonS3.deleteObject(ossProperties.getDefaultBucket(), objectName);
    }

    @Override
    public S3Object getObject(String objectName) {
        return amazonS3.getObject(ossProperties.getDefaultBucket(), objectName);
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> putObject(String fileName, InputStream stream) {
        return putObject(fileName, stream, "application/octet-stream");
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> putObject(String fileName, InputStream stream, String contextType) {
        return put(ossProperties.getDefaultBucket(), fileName, stream, stream.available(), contextType);
    }

    @Override
    @SneakyThrows
    public PutObjectResult putObject(File file) {
        final FilenameSignResult signResult = getFileNameSignStrategy().sign(file.getName());
        return amazonS3.putObject(ossProperties.getDefaultBucket(), signResult.getSignFilename(), file);
    }

    @Override
    @SneakyThrows
    public String getObjectUrl(String objectName, Integer expires) {
        final DateTime time = DateUtil.date().offset(DateField.DAY_OF_MONTH, expires);
        URL url = amazonS3.generatePresignedUrl(ossProperties.getDefaultBucket(), objectName, time);
        return url.toString();
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> put(String bucketName, String fileName, InputStream stream, long size,
                                        String contextType)  {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contextType);

        final FilenameSignResult signResult = getFileNameSignStrategy().sign(fileName);

        return new CustomFileResult<T>()
                .setPutObjectResult(amazonS3.putObject(bucketName, fileName, stream, objectMetadata))
                .signInfo(signResult);
    }



    // ------------------------ business


    @Override
    public void deleteObjectForBusinessId(Serializable businessId) {
        getBusinessFileStrategy().deleteBefore(businessId);
        amazonS3.deleteObject(ossProperties.getDefaultBucket(), getBusinessFileStrategy().convert(businessId));
        getBusinessFileStrategy().deleteAfter(businessId);
    }

    @Override
    public S3Object getObjectForBusinessId(Serializable businessId) {
        return amazonS3.getObject(ossProperties.getDefaultBucket(), getBusinessFileStrategy().convert(businessId));
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> putObjectForBusinessId(String fileName, InputStream stream) {
        return putObjectForBusinessId(fileName, stream, "application/octet-stream");
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> putObjectForBusinessId(String fileName, InputStream stream, String contextType) {

        final CustomFileResult<T> customFileResult = put(ossProperties.getDefaultBucket(), fileName,
                stream, stream.available(), contextType);

        return customFileResult.setAttachment(getBusinessFileStrategy().putAfter(customFileResult));
    }

    @Override
    @SneakyThrows
    public CustomFileResult<T> putObjectForBusinessId(File file) {
        final FilenameSignResult signResult = getFileNameSignStrategy().sign(file.getName());
        final PutObjectResult result = amazonS3.putObject(ossProperties.getDefaultBucket(), signResult.getSignFilename(), file);

        final CustomFileResult<T> fileResult = new CustomFileResult<T>().setPutObjectResult(result).signInfo(signResult);
        return fileResult.setAttachment(getBusinessFileStrategy().putAfter(fileResult));
    }
}
