<template>
  <div>
    <el-upload
      ref="upload"
      action="https://jsonplaceholder.typicode.com/posts/"
      multiple
      :data="data"
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
    <el-button size="small" type="success" @click="submitUpload">上传到服务器</el-button>


  </div>
</template>

<script>
  export default {
    name: "upload-file",
    data() {
      return {
        data: {info: 'asasas'},
        fileList: [],
      };
    },
    methods: {
      onPreview: function (file) {
        console.log('onPreview');
        console.log(file);
      },
      onRemove: function (file, fileList) {
        console.log('onRemove');
        console.log(file);
        console.log(fileList);
        this.fileList=fileList
      },
      onSuccess: function (response, file, fileList) {
        console.log('onSuccess');
        console.log(response);
        console.log(file);
        console.log(fileList);
        this.fileList=fileList
      },
      onError: function (err, file, fileList) {
        console.log('onError');
        console.log(err);
        console.log(file);
        console.log(fileList);
        this.fileList=fileList
        // this.fileList.push(file)
      },
      onChange: function (file, fileList) {
        console.log('onChange');
        console.log(file);
        console.log(fileList);
        this.fileList=fileList
      },
      submitUpload() {
        this.$refs.upload.submit();
      },
    },
  }
</script>

<style scoped>

</style>
