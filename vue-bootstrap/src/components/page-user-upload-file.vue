<template>
  <b-container>
    <b-row>
      <b-col>
        <navbar></navbar>
      </b-col>
    </b-row>
    <br/>
    <b-row>
      <b-col>
        <b-container fluid>
          <b-row>
            <b-col sm="0" md="2" lg="3" xl="3"></b-col>
            <b-col sm="12" md="8" lg="6" xl="6">
              <user-upload-file></user-upload-file>
            </b-col>
          </b-row>
        </b-container>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
  import navbar from './navbar'
  import userUploadFile from './user-upload-file'

  export default {
    name: "page-upload-file",
    components: {
      'navbar': navbar,
      'user-upload-file': userUploadFile,
    },
  }
</script>

<style scoped>

</style>
