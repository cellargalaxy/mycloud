<template>
  <el-container>
    <el-header style="min-width: 50em;">
      <public-menu></public-menu>
    </el-header>
    <el-container>
      <el-aside style="min-width: 3em;max-width: 10em;">
        <admin-menu></admin-menu>
      </el-aside>
      <el-main style="min-width: 70em;">
        <admin-file-info-own></admin-file-info-own>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import publicMenu from './public-menu'
  import adminMenu from './admin-menu'
  import adminFileInfoOwn from './admin-file-info-own'

  export default {
    name: "page-admin-file-info-own",
    components: {
      'public-menu': publicMenu,
      'admin-menu': adminMenu,
      'admin-file-info-own': adminFileInfoOwn,
    },
  }
</script>

<style scoped>

</style>
