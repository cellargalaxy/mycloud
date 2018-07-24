<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="1">
      mycloud
    </el-menu-item>
    <el-menu-item index="2">
      <router-link to="/about">
        <i class="el-icon-upload"></i>
        上传文件
      </router-link>

    </el-menu-item>


    <el-menu-item index="3">
      <i class="el-icon-picture-outline"></i>
      文件管理
    </el-menu-item>
    <el-menu-item index="4">
      <i class="el-icon-menu"></i>
      个人主页
    </el-menu-item>
    <el-menu-item index="5">
      <i class="el-icon-setting"></i>
      后台管理
    </el-menu-item>
    <el-menu-item index="6">
      <!--<i class="el-icon-d-arrow-left"></i>-->
      登录
    </el-menu-item>
    <el-menu-item index="7">
      <!--<i class="el-icon-d-arrow-right"></i>-->
      注销
    </el-menu-item>
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
        // window.location.href=""+keyPath;
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
