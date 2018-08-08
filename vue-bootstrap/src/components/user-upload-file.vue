<template>
  <div>
    <b-form @submit="uploadFile">

      <b-form-group label="">
        <b-form-file v-model="files" :state="Boolean(files)" :no-drop="true" :multiple="true"
                     placeholder="可以选择多个文件"></b-form-file>
      </b-form-group>

      <b-form-group label="">
        <b-form-select :options="sorts" v-model="sort" size="sm"/>
      </b-form-group>

      <b-form-group label="">
        <b-form-textarea v-model="description" placeholder="描述" rows="3" maxlength="256" size="sm"></b-form-textarea>
      </b-form-group>

      <b-button type="submit" variant="primary" style="width: 100%" size="sm">上传</b-button>

    </b-form>
  </div>
</template>

<script>
  import userApi from '../utils/user-api'

  export default {
    name: "upload-file",
    data() {
      return {
        sorts: [
          {value: null, text: '默认'},
          {value: 'jpg', text: '图片'},
          {value: 'mkv', text: '视频'},
        ],
        files: [],
        sort: null,
        description: null,
      }
    },
    methods: {
      uploadFile(evt) {
        evt.preventDefault();
        userApi.uploadFile(this.files, this.sort, this.description)
          .then(res => {
              if (res.data.status != 1) {
                alert(res.data.massage)
                return;
              }
              alert('上传成功')
              console.log(res.data)
            },
            error => {
              alert('上传失败:' + error)
            }
          )
      },
    }
  }
</script>

<style scoped>

</style>
