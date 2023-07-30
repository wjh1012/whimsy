package cn.wangjiahang.whimsy.core;


import cn.wangjiahang.whimsy.strategy.filename.FilenameSignResult;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * @author jh.wang
 * @since 2023/6/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CustomFileResult<T> {
    private String originalFilename;
    private String signFilename;
    private String signValue;

    private T attachment;

    private PutObjectResult putObjectResult;
    private S3Object fileObject;

    public InputStream getInputStream() {
        return fileObject.getObjectContent();
    }

    protected CustomFileResult<T> signInfo(FilenameSignResult filenameSignResult) {
        this.setOriginalFilename(filenameSignResult.getOriginalFilename());
        this.setSignFilename(filenameSignResult.getSignFilename());
        this.setSignValue(filenameSignResult.getSignValue());
        return this;
    }
}
