package cn.wangjiahang.controller;

import cn.wangjiahang.bo.FileUploadInfo;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.spring.file.MultipartFileWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@RestController
public class FileDetailController {

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) {
        //只需要这一行代码即可上传成功
        return fileStorageService.of(file).upload();
    }

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload2")
    public String upload2(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath("upload/") //保存到相对路径下，为了方便管理，不需要可以不写
                .setObjectId("0")   //关联对象id，为了方便管理，不需要可以不写
                .setObjectType("0") //关联对象类型，为了方便管理，不需要可以不写
                .putAttr("role", "admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
                .upload();  //将文件上传到对应地方
        return fileInfo == null ? "上传失败！" : fileInfo.getUrl();
    }

    /**
     * 上传图片，成功返回文件信息
     * 图片处理使用的是 https://github.com/coobird/thumbnailator
     */
    @PostMapping("/upload-image")
    public FileInfo uploadImage(MultipartFile file) {
        return fileStorageService.of(file)
                .image(img -> img.size(1000, 1000))  //将图片大小调整到 1000*1000
                .thumbnail(th -> th.size(200, 200))  //再生成一张 200*200 的缩略图
                .upload();
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/upload-platform")
    public FileInfo uploadPlatform(MultipartFile file) {
        return fileStorageService.of(file)
                .setPlatform("aliyun-oss-1")    //使用指定的存储平台
                .upload();
    }

    /**
     * 直接读取 HttpServletRequest 中的文件进行上传，成功返回文件信息
     * 使用这种方式有些注意事项，请查看文档 基础功能-上传 章节
     */
    @PostMapping("/upload-request")
    public FileInfo uploadPlatform(HttpServletRequest request) {
        return fileStorageService.of(request).upload();
    }

    @RequestMapping(value = "/previewUrl", method = {RequestMethod.POST, RequestMethod.GET})
    public String preview(@RequestParam String url, Boolean th) {
        final FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);

        if (fileInfo == null) {
            return "";
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        return Boolean.TRUE.equals(th) ?
                fileStorageService.generateThPresignedUrl(fileInfo, calendar.getTime())
                : fileStorageService.generatePresignedUrl(fileInfo, calendar.getTime());
    }


    /**
     * 初始化 分片信息
     * @param fileUploadInfo
     * @return
     */
    @GetMapping(value = "/uploader/chunk")
    public FileInfo init(FileUploadInfo fileUploadInfo) {
        return fileStorageService.initiateMultipartUpload()
                .setSize(fileUploadInfo.getTotalSize())
                .setOriginalFilename(fileUploadInfo.getFilename())
                .init();
    }

    /**
     * 上海 分片文件
     * @param fileUploadInfo
     * @return
     */
    @PostMapping(value = "/uploader/chunk")
    public void bigFile(FileUploadInfo fileUploadInfo) {
        final FileInfo fileInfo = fileStorageService.getFileInfoByUrl(fileUploadInfo.getUrl());

        final MultipartFileWrapper fileWrapper = new MultipartFileWrapper(
                fileUploadInfo.getUpfile(),
                fileUploadInfo.getUpfile().getOriginalFilename(),
                fileUploadInfo.getUpfile().getContentType(),
                fileUploadInfo.getUpfile().getSize());

        fileStorageService.uploadPart(fileInfo, fileUploadInfo.getChunkNumber(),
                        fileWrapper, fileUploadInfo.getCurrentChunkSize().longValue()).upload();
    }

    /**
     * 合并 分片文件
     * @param url
     * @return
     */
    @PostMapping(value = "/uploader/merge")
    public FileInfo merge(@RequestParam String url) {
        final FileInfo fileInfo = fileStorageService.getFileInfoByUrl(url);
        return fileStorageService.completeMultipartUpload(fileInfo).complete();
    }

    /**
     * 下载文件
     * @param url
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/download")
    public void download(@RequestParam String url, HttpServletResponse response) throws IOException {
        fileStorageService.download(url).outputStream(response.getOutputStream());
    }
}
