package cn.wangjiahang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 文件分片详情实体类
 */
@Data
@Accessors(chain = true)
@TableName("file_part_detail")
public class FilePartDetail {

    /**
     * 分片id
     */
    @TableId
    private String id;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 上传ID，仅在手动分片上传时使用
     */
    private String uploadId;

    /**
     * 分片 ETag
     */
    private String eTag;

    /**
     * 分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000
     */
    private Integer partNumber;

    /**
     * 文件大小，单位字节
     */
    private Long partSize;

    /**
     * 哈希信息
     */
    private String hashInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    // Getters and setters
}
