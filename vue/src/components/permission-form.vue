<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="权限id">
      <el-input v-model="permissionId" :value="permissionId" size="mini" type="number" placeholder="权限id"></el-input>
    </el-form-item>
    <el-form-item label="权限">
      <el-input v-model="permissionMark" :value="permissionMark" size="mini" type="text" placeholder="权限"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="add" size="mini" type="success">添加</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "permission-form",
    data() {
      return {
        permissionId: 0,
        permissionMark: null,
      }
    },
    methods: {
      add() {
        util.simpleDealApi(api.addPermission(this.permissionId, this.permissionMark))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('添加成功')
            this.permissionId = null;
            this.permissionMark = null;
          })
      }
    }
  }
</script>

<style scoped>

</style>
