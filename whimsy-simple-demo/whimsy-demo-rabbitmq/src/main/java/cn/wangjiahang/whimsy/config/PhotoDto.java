package cn.wangjiahang.whimsy.config;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jh.wang
 * @since 2023/7/30
 */
@Data
public class PhotoDto {
    private MultipartFile file;
    private Long fileId;
}
