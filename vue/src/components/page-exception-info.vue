<template>
  <el-container>
    <el-header style="min-width: 50em;">
      <public-menu></public-menu>
    </el-header>
    <el-container>
      <el-aside style="min-width: 3em;max-width: 10em;">
        <admin-menu></admin-menu>
      </el-aside>
      <el-main style="min-width: 40em;">
        <admin-exception-info></admin-exception-info>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import publicMenu from './menu'
  import adminMenu from './admin-menu'
  import adminExceptionInfo from './admin-exception-info'
    export default {
        name: "page-exception-info",
      components: {
        'public-menu': publicMenu,
        'admin-menu': adminMenu,
        'admin-exception-info': adminExceptionInfo,
      },
    }
</script>

<style scoped>

</style>
