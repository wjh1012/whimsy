package cn.wangjiahang.whimsy.strategy.filename;

import lombok.Builder;
import lombok.Data;

/**
 * @author jh.wang
 * @since 2023/6/3
 */
@Data
@Builder
public class FilenameSignResult {
    private String originalFilename;
    private String signFilename;
    private String signValue;
}
