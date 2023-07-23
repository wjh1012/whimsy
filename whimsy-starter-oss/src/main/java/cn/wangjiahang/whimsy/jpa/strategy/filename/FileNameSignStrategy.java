package cn.wangjiahang.whimsy.jpa.strategy.filename;

import cn.hutool.core.util.IdUtil;

/**
 * @author jh.wang
 * @since 2023/6/1
 */
public interface FileNameSignStrategy {
    /**
     * 给file加签防止重复
     *
     * @param fileName
     * @return
     */
    FilenameSignResult sign(String fileName);

    /**
     * 文件名加签的末班
     *
     * @return
     */
    default String fileNameTemplate() {
        return "{}_{}{}";
    }

    /**
     * 随机数
     *
     * @return
     */
    default String signValue() {
        return IdUtil.getSnowflakeNextIdStr();
    }
}
