package cn.wangjiahang.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadInfo {
    private String url;

    private Integer chunkNumber;
    private Integer chunkSize;
    private Integer currentChunkSize;
    private Long totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private Integer totalChunks;


    private MultipartFile upfile;
}
