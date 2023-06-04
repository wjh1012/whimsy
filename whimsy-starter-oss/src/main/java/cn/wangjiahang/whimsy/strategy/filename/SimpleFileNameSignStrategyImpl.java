package cn.wangjiahang.whimsy.strategy.filename;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author jh.wang
 * @since 2023/6/1
 */

public class SimpleFileNameSignStrategyImpl implements FileNameSignStrategy {

    /**
     * "d:/test/aaa" 返回 "aaa_1664282611879190528"
     * "/test/aaa.jpg" 返回 "aaa_1664282611879190524.jpg"
     */
    @Override
    public FilenameSignResult sign(String fileName) {
        final String signValue = this.signValue();
        final String signFilename = StrUtil.format(
                this.fileNameTemplate(),
                FileNameUtil.getPrefix(fileName),
                signValue,
                StrUtil.isBlank(FileNameUtil.getSuffix(fileName)) ? StrUtil.EMPTY : "." + FileNameUtil.getSuffix(fileName)
        );

        return FilenameSignResult.builder()
                .originalFilename(FileNameUtil.mainName(fileName))
                .signFilename(signFilename)
                .signValue(signValue)
                .build();
    }
}
