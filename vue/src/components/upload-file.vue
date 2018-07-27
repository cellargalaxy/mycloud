<template>
  <el-form>
    <el-form-item label="分类">
      <el-input v-model="uploadData.sort" :value="uploadData.sort" size="mini"></el-input>
    </el-form-item>
    <el-form-item label="描述">
      <el-input type="textarea" v-model="uploadData.description" :value="uploadData.description" size="mini"></el-input>
    </el-form-item>
    <el-form-item label="">
      <el-upload
        ref="upload"
        :action="uploadFileUrl"
        multiple
        :data="uploadData"
        :show-file-list="true"
        drag
        :on-preview="onPreview"
        :on-remove="onRemove"
        :on-success="onSuccess"
        :on-error="onError"
        :on-change="onChange"
        list-type="picture"
        :auto-upload="false"
        :file-list="fileList"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-form-item>
    <el-form-item>
      <el-button size="mini" type="primary" @click="submitUpload" style="width: 100%">上传</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import api from '../utils/api'

  export default {
    name: "upload-file",
    data() {
      return {
        uploadFileUrl: null,
        uploadData: {sort: null, description: null},
        fileList: [],
      };
    },
    created: function () {
      this.uploadFileUrl = api.getUploadFileUrl()
    },
    methods: {
      onPreview: function (file) {
        console.log('onPreview');
        console.log(file);
      },
      onRemove: function (file, fileList) {
        this.fileList = fileList
      },
      onSuccess: function (response, file, fileList) {
        this.$message.success('文件(' + file.name + ')上传成功')
      },
      onError: function (err, file, fileList) {
        this.fileList = fileList
        this.fileList.push(file)
        this.$message.error('文件(' + file.name + ')上传失败')
      },
      onChange: function (file, fileList) {
        this.fileList = fileList
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
    },
  }
</script>

<style scoped>

</style>
