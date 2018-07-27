<template>
  <el-row>
    <el-col :xs="0" :sm="1" :md="3" :lg="4" :xl="5">
      <div style="min-height: 1em;"></div>
    </el-col>
    <el-col :xs="24" :sm="22" :md="18" :lg="16" :xl="14" justify="center">
      <el-container>
        <el-header style="min-width: 50em;">
          <public-menu></public-menu>
        </el-header>

        <el-row>
          <el-col :xs="0" :sm="5" :md="5" :lg="6" :xl="7">
            <div style="min-height: 1em;"></div>
          </el-col>
          <el-col :xs="24" :sm="14" :md="14" :lg="12" :xl="10" justify="center">
            <el-container>
              <el-main style="min-width: 20em;max-width: 25em;">
                <upload-file></upload-file>
              </el-main>
            </el-container>
          </el-col>
          <el-col :xs="0" :sm="5" :md="5" :lg="6" :xl="7">
            <div style="min-height: 1em;"></div>
          </el-col>
        </el-row>

      </el-container>
    </el-col>
    <el-col :xs="0" :sm="1" :md="3" :lg="4" :xl="5">
      <div style="min-height: 1em;"></div>
    </el-col>
  </el-row>
</template>

<script>
  import publicMenu from './public-menu'
  import adminMenu from './admin-menu'
  import uploadFile from './upload-file'

  export default {
    name: "page-upload-file",
    components: {
      'public-menu': publicMenu,
      'admin-menu': adminMenu,
      'upload-file': uploadFile,
    },
  }
</script>

<style scoped>

</style>
