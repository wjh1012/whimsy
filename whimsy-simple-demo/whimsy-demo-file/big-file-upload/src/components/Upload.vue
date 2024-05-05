<template>
  <div>
    <!-- 上传器 -->
    <uploader
        ref="uploaderRef"
        :options="options"
        :autoStart="false"
        :file-status-text="fileStatusText"
        class="uploader-ui"
        @file-added="onFileAdded"
        @file-success="onFileSuccess"
        @file-progress="onFileProgress"
        @file-error="onFileError"
    >
      <uploader-unsupport></uploader-unsupport>
      <uploader-drop>
        <div>
          <uploader-btn id="global-uploader-btn" ref="uploadBtn" :attrs="attrs">
            选择文件
            <el-icon><Upload /></el-icon>
          </uploader-btn>
        </div>
      </uploader-drop>
      <uploader-list></uploader-list>
    </uploader>
  </div>
</template>

<script setup>
import { ACCEPT_CONFIG } from '@/config/accept.ts';
import { reactive, ref } from 'vue';
import SparkMD5 from 'spark-md5';
import { mergeFile } from '@/api/fileUpload/index';
import { ElMessage } from 'element-plus';

const options = reactive({
  //目标上传 URL，默认POST, import.meta.env.VITE_API_URL = api
  // target ==》http://localhost:6666/api/uploader/chunk
  target: import.meta.env.VITE_API_URL + '/uploader/chunk',
  query: {},
  headers: {
    // 需要携带token信息，当然看各项目情况具体定义
    token: "your_token",
  },
  //分块大小(单位：字节)
  chunkSize: '5242880',
  url: '',
  //上传文件时文件内容的参数名，对应chunk里的Multipart对象名，默认对象名为file
  fileParameterName: 'upfile',
  //失败后最多自动重试上传次数
  maxChunkRetries: 3,
  //是否开启服务器分片校验，对应GET类型同名的target URL
  testChunks: true,
  // 服务器分片校验函数
  checkChunkUploadedByResponse: function (chunk, response_msg) {
    let objMessage = JSON.parse(response_msg);

    if (!this.url) {
      options.url = objMessage?.url;
    }

    if (objMessage?.attr?.skipUpload) {
      return true;
    }

    return (objMessage.uploadedChunks || []).indexOf(chunk.offset + 1) >= 0;
  },
  processParams: function(params, file) {
    if (!params.url) {
      params.url = options.url
    }
    return params
  }
});
const attrs = reactive({
  accept: ACCEPT_CONFIG.getAll(),
});
const fileStatusText = reactive({
  success: '上传成功',
  error: '上传失败',
  uploading: '上传中',
  paused: '暂停',
  waiting: '等待上传',
});
onMounted(() => {
});
function onFileAdded(file) {
  computeMD5(file);
}

function onFileSuccess(rootFile, file, response, chunk) {
  //refProjectId为预留字段，可关联附件所属目标，例如所属档案，所属工程等
  file.refProjectId = '';
  mergeFile(options.url)
      .then((responseData) => {
        if (responseData.data.code === 415) {
          console.log('合并操作未成功，结果码：' + responseData.data.code);
        }
        ElMessage.success(responseData.data);
        options.url = '';
      })
      .catch(function (error) {
        console.log('合并后捕获的未知异常：' + error);
      })
}

function onFileError(rootFile, file, response, chunk) {
  console.log('上传完成后异常信息：' + response);
}

function onFileProgress(rootFile, file, chunk) {
  // 文件进度的回调
  // console.log('on-file-progress', rootFile, file, chunk)
}

/**
 * 计算md5，实现断点续传及秒传
 * @param file
 */
function computeMD5(file) {
  file.pause();

  //单个文件的大小限制2G
  let fileSizeLimit = 2 * 1024 * 1024 * 1024;
  console.log('文件大小：' + file.size);
  console.log('限制大小：' + fileSizeLimit);
  if (file.size > fileSizeLimit) {
    file.cancel();
  }

  let fileReader = new FileReader();
  let time = new Date().getTime();
  let blobSlice =
      File.prototype.slice ||
      File.prototype.mozSlice ||
      File.prototype.webkitSlice;
  let currentChunk = 0;
  const chunkSize = 10 * 1024 * 1000;
  let chunks = Math.ceil(file.size / chunkSize);
  let spark = new SparkMD5.ArrayBuffer();
  //由于计算整个文件的Md5太慢，因此采用只计算第1块文件的md5的方式
  let chunkNumberMD5 = 1;

  loadNext();

  fileReader.onload = (e) => {
    spark.append(e.target.result);

    if (currentChunk < chunkNumberMD5) {
      loadNext();
    } else {
      let md5 = spark.end();
      file.uniqueIdentifier = md5;
      file.resume();
      console.log(
          `MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${
              file.size
          } 用时：${new Date().getTime() - time} ms`
      );
    }
  };

  fileReader.onerror = function () {
    error(`文件${file.name}读取出错，请检查该文件`);
    file.cancel();
  };

  function loadNext() {
    let start = currentChunk * chunkSize;
    let end = start + chunkSize >= file.size ? file.size : start + chunkSize;

    fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end));
    currentChunk++;
    console.log('计算第' + currentChunk + '块');
  }
}
const uploaderRef = ref();
function close() {
  uploaderRef.value.cancel();
}
function error(msg) {
  console.log(msg, 'msg');
}
</script>

<style scoped>
.uploader-ui {
  padding: 15px;
  margin: 40px auto 0;
  font-size: 12px;
  font-family: Microsoft YaHei;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.4);
}
.uploader-ui .uploader-btn {
  margin-right: 4px;
  font-size: 12px;
  border-radius: 3px;
  color: #fff;
  background-color: #409eff;
  border-color: #409eff;
  display: inline-block;
  line-height: 1;
  white-space: nowrap;
}
.uploader-ui .uploader-list {
  max-height: 440px;
  overflow: auto;
  overflow-x: hidden;
  overflow-y: auto;
}
</style>
