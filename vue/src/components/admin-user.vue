<template>
  <el-container>
    <el-header>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="账号">
          <el-input v-model="userForm.username" :value="userForm.username"
                    size="mini" type="text" placeholder="账号" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="userForm.userPassword" :value="userForm.userPassword"
                    size="mini" type="password" placeholder="密码" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="add" size="mini" type="success">添加</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table :data="users" v-loading="loading">
        <el-table-column label="用户名">
          <template slot-scope="scope">
            <el-input :value="users[scope.$index].username" v-model="users[scope.$index].username"
                      size="mini" type="text" placeholder="用户名"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="密码">
          <template slot-scope="scope">
            <el-input :value="users[scope.$index].userPassword" v-model="users[scope.$index].userPassword"
                      size="mini" type="password" placeholder="密码"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="更新时间" prop="updateTime"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" type="warning" @click="change(scope.row)">修改</el-button>
            <el-button size="mini" type="danger" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer>
      <pagination :total="total" v-on:changePageSize="changePageSize" v-on:turnPage="turnPage"></pagination>
    </el-footer>
  </el-container>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'
  import pagination from './pagination'

  export default {
    name: "admin-user",
    data() {
      return {
        loading: false,
        users: [],
        total: 0,
        userForm: {username: null, userPassword: null,},
        userQuery: {pageSize: 10, page: 1, userId: 0, username: null, createTime: null, updateTime: null},
      }
    },
    created: function () {
      this.listUser()
      this.getUserCount()
    },
    methods: {
      changePageSize: function (pageSize) {
        this.userQuery.pageSize = pageSize
      },
      turnPage: function (page) {
        this.userQuery.page = page
        this.listUser()
      },
      add() {
        api.addUser(this.userForm.username, this.userForm.userPassword)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.listUser()
            this.getUserCount()
            this.$message.success('添加成功')
            this.userForm = {username: null, userPassword: null,};
          })
      },
      change(row) {
        api.changeUser(row.userId, row.username, row.userPassword)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('修改成功')
            this.listUser()
          })
      },
      remove(row) {
        api.removeUser(row.userId)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('删除成功')
            this.listUser()
            this.getUserCount()
          })
      },
      listUser: function () {
        this.loading = true;
        api.listUser(this.userQuery.pageSize, this.userQuery.page, this.userQuery.userId, this.userQuery.username, this.userQuery.createTime, this.userQuery.updateTime)
          .then(res => {
            this.loading = false;
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.users = res.data.data;
            for (let i = 0; i < this.users.length; i++) {
              this.users[i].createTime = util.formatTimestamp(this.users[i].createTime, 'yyyy-MM-dd hh:mm:ss')
              this.users[i].updateTime = util.formatTimestamp(this.users[i].updateTime, 'yyyy-MM-dd hh:mm:ss')
            }
          })
      },
      getUserCount: function () {
        api.getUserCount(this.userQuery.pageSize, this.userQuery.page, this.userQuery.userId, this.userQuery.username, this.userQuery.createTime, this.userQuery.updateTime)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.total = res.data.data;
          })
      }
    },
    components: {
      'pagination': pagination,
    },
  }
</script>

<style scoped>

</style>
