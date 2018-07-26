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
        <admin-user></admin-user>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import publicMenu from './menu.vue'
  import adminMenu from './admin-menu'
  import adminUser from './admin-user'

  export default {
    name: "page-admin-user",
    components: {
      'public-menu': publicMenu,
      'admin-menu': adminMenu,
      'admin-user': adminUser,
    },
  }
</script>

<style scoped>

</style>
