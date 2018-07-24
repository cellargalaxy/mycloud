<template>
  <el-menu default-active="1-4-1" class="el-menu-vertical-demo"
           :collapse="isCollapse">
    <el-menu-item index="1" @click="changeCollapse">
      <i class="el-icon-setting"></i>
    </el-menu-item>
    <el-menu-item index="1">
      <i class="el-icon-setting"></i>
      <span slot="title">所属管理</span>
    </el-menu-item>
    <el-menu-item index="2">
      <i class="el-icon-setting"></i>
      <span slot="title">用户管理</span>
    </el-menu-item>
    <el-menu-item index="3">
      <i class="el-icon-setting"></i>
      <span slot="title">权限管理</span>
    </el-menu-item>
    <el-menu-item index="4">
      <i class="el-icon-setting"></i>
      <span slot="title">授权管理</span>
    </el-menu-item>
    <el-menu-item index="5">
      <i class="el-icon-setting"></i>
      <span slot="title">异常日志</span>
    </el-menu-item>
    <el-menu-item index="6">
      <i class="el-icon-setting"></i>
      <span slot="title">任务日志</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "admin-menu",
    data() {
      return {
        isCollapse: true
      };
    },
    methods: {
      changeCollapse: function () {
        this.isCollapse = !this.isCollapse;
      },
    }
  }
</script>

<style scoped>

</style>
