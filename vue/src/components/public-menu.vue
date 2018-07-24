<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="1">mycloud</el-menu-item>
    <el-menu-item index="2">上传文件</el-menu-item>
    <el-menu-item index="2">文件管理</el-menu-item>
    <el-menu-item index="3">个人主页</el-menu-item>
    <el-menu-item index="4">后台管理</el-menu-item>
    <el-menu-item index="5">登录</el-menu-item>
    <el-menu-item index="6">注销</el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: 'public-menu',
    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1'
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
