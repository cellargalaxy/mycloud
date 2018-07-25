<template>
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="账号">
      <el-input v-model="username" :value="username" size="mini" type="text" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item label="密码">
      <el-input v-model="userPassword" :value="userPassword" size="mini" type="password" placeholder="密码"></el-input>
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
    name: "user-form",
    data() {
      return {
        username: null,
        userPassword: null,
      }
    },
    methods: {
      add() {
        util.simpleDealApi(api.addUser(this.username, this.userPassword))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('添加成功')
            this.username = null;
            this.userPassword = null;
          })
      }
    }
  }
</script>

<style scoped>

</style>
