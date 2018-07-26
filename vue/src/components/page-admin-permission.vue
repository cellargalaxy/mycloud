<template>
  <el-container>
    <el-header style="min-width: 50em;">
      <public-menu></public-menu>
    </el-header>
    <el-container>
      <el-aside style="min-width: 3em;max-width: 10em;">
        <admin-menu></admin-menu>
      </el-aside>
      <el-main style="min-width: 40em;max-width: 60em;">
        <admin-permission></admin-permission>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import publicMenu from './public-menu'
  import adminMenu from './admin-menu'
  import adminPermission from './admin-permission'

  export default {
    name: "page-admin-permission",
    components: {
      'public-menu': publicMenu,
      'admin-menu': adminMenu,
      'admin-permission': adminPermission,
    },
  }
</script>

<style scoped>

</style>
