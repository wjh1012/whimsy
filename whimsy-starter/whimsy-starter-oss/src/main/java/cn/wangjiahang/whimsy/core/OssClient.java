package cn.wangjiahang.whimsy.core;

import cn.wangjiahang.whimsy.OssProperties;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.SneakyThrows;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author jh.wang
 * @since 2023/6/1
 */

public interface OssClient<T> {
    AmazonS3 amazonS3Client();
    OssProperties ossProperties();

    void download(String objectName, OutputStream os);

    String getObjectUrl(String objectName, Integer expires);

    void deleteObject(String objectName);

    S3Object getObject(String objectName);

    CustomFileResult<T> putObject(String fileName, InputStream stream);

    CustomFileResult<T> putObject(String fileName, InputStream stream, String contextType);

    PutObjectResult putObject(File file);


    CustomFileResult<T> put(String bucketName, String fileName, InputStream stream, long size, String contextType);

    void deleteObjectForBusinessId(Serializable businessId);

    S3Object getObjectForBusinessId(Serializable businessId);

    CustomFileResult<T> putObjectForBusinessId(String fileName, InputStream stream);

    CustomFileResult<T> putObjectForBusinessId(String fileName, InputStream stream, String contextType);

    CustomFileResult<T> putObjectForBusinessId(File file);

    @SneakyThrows
    void downloadForBusinessId(Serializable businessId, OutputStream os, Consumer<S3Object> consumer);
}
