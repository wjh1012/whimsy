package cn.wangjiahang.whimsy.jpa.strategy.businessfile;

import cn.wangjiahang.whimsy.jpa.core.CustomFileResult;

import java.io.Serializable;

/**
 * @author jh.wang
 * @since 2023/6/1
 */

public interface BusinessFileStrategy<T> {
    /**
     * 删除文件后
     * @param businessId
     */
    default void deleteAfter(Serializable businessId) {}

    /**
     * 删除文件前
     * @param businessId
     */
    default void deleteBefore(Serializable businessId) {}

    /**
     * 上传文件后
     * @param customFileResult
     */
    T putAfter(CustomFileResult<T> customFileResult);

    /**
     * 根据业务id获取文件服务器对应文件的key
     * @param businessId
     * @return
     */
    default String convert(Serializable businessId) {
        return (String) businessId;
    }
}
