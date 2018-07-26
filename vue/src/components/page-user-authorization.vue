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
        <user-authorization-table></user-authorization-table>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import publicMenu from './menu'
  import adminMenu from './admin-menu'
  import userAuthorizationTable from './admin-user-authorization'
    export default {
        name: "page-user-authorization",
      components: {
        'public-menu': publicMenu,
        'admin-menu': adminMenu,
        'user-authorization-table': userAuthorizationTable,
      },
    }
</script>

<style scoped>

</style>
