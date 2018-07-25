<template>
  <el-table :data="users">
    <el-table-column label="用户名">
      <template slot-scope="scope">
        <el-input :value="users[scope.$index].username" v-model="users[scope.$index].username"
                  size="mini" type="text" placeholder="请输入内容"></el-input>
      </template>
    </el-table-column>
    <el-table-column label="密码">
      <template slot-scope="scope">
        <el-input :value="users[scope.$index].userPassword" v-model="users[scope.$index].userPassword"
                  size="mini" type="password" placeholder="请输入内容"></el-input>
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
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "user-table",
    data() {
      return {
        users: [],
        userQuery: {pageSize: 20, page: 1, userId: 0, username: null, createTime: null, updateTime: null},
      }
    },
    created: function () {
      this.listUser()
    },
    methods: {
      change(row) {
        util.simpleDealApi(api.changeUser(row.userId, row.username, row.userPassword))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('修改成功')
            this.listUser()
          })
      },
      remove(row) {
        util.simpleDealApi(api.removeUser(row.userId))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('删除成功')
            this.listUser()
          })
      },
      listUser: function () {
        util.simpleDealApi(api.listUser(this.userQuery.pageSize, this.userQuery.page, this.userQuery.userId, this.userQuery.username, this.userQuery.createTime, this.userQuery.updateTime))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            this.users = res.data.data;
            for (let i = 0; i < this.users.length; i++) {
              this.users[i].createTime = util.formatTimestamp(this.users[i].createTime, 'yyyy-MM-dd hh:mm:ss')
              this.users[i].updateTime = util.formatTimestamp(this.users[i].updateTime, 'yyyy-MM-dd hh:mm:ss')
            }
          })
      },
    }
  }
</script>

<style scoped>

</style>
