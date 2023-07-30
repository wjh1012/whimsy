package cn.wangjiahang.whimsy.core;

import cn.wangjiahang.whimsy.strategy.businessfile.BusinessFileStrategy;
import cn.wangjiahang.whimsy.strategy.filename.FileNameSignStrategy;
import cn.wangjiahang.whimsy.strategy.filename.SimpleFileNameSignStrategyImpl;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author jh.wang
 * @since 2023/6/1
 */

public abstract class AbstractOssClient<T> implements OssClient<T> {
    private FileNameSignStrategy fileNameSignStrategy = new SimpleFileNameSignStrategyImpl();
    private BusinessFileStrategy<T> businessFileStrategy = customFileResult -> null;

    public void setFileNameSignStrategy(FileNameSignStrategy fileNameSignStrategy) {
        Assert.notNull(fileNameSignStrategy, "fileNameSignStrategy can not be null");
        this.fileNameSignStrategy = fileNameSignStrategy;
    }

    public FileNameSignStrategy getFileNameSignStrategy() {
        return fileNameSignStrategy;
    }

    public void setBusinessFileStrategy(BusinessFileStrategy<T> businessFileStrategy) {
        Assert.notNull(businessFileStrategy, "businessFileStrategy can not be null");
        this.businessFileStrategy = businessFileStrategy;
    }

    public BusinessFileStrategy<T> getBusinessFileStrategy() {
        return businessFileStrategy;
    }

    @Override
    @SneakyThrows
    public void download(String objectName, OutputStream os) {
        final S3Object object = getObject(objectName);
        final S3ObjectInputStream objectContent = object.getObjectContent();
        try (os; objectContent) {
            IOUtils.copy(objectContent, os);
        }
    }

    @Override
    @SneakyThrows
    public void downloadForBusinessId(Serializable businessId, OutputStream os, Consumer<S3Object> consumer) {
        final S3Object object = getObjectForBusinessId(businessId);
        consumer.accept(object);
        final S3ObjectInputStream objectContent = object.getObjectContent();
        try (os; objectContent) {
            IOUtils.copy(objectContent, os);
        }
    }
}
