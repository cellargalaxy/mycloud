<template>
  <el-table :data="userAuthorizations">
    <el-table-column label="用户" prop="user.username"></el-table-column>
    <el-table-column label="权限">
      <template slot-scope="scope">
        <el-tag v-for="userAuthorization in userAuthorizations[scope.$index].authorizations"
                :key="userAuthorization.authorizationId" closable :disable-transitions="false"
                @close="remove(userAuthorization)">
          {{userAuthorization.permissionMark}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="授权">
      <template slot-scope="scope">
        <el-select v-model="userAuthorizations[scope.$index].permissionId" filterable clearable placeholder="请选择">
          <el-option
            v-for="permission in permissions"
            :key="permission.permissionId"
            :label="permission.permissionMark"
            :value="permission.permissionId">
          </el-option>
        </el-select>
        <el-button type="success" @click="add(scope.row)">添加</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "user-authorization-table",
    data() {
      return {
        permissions: [],
        userAuthorizations: [],
        userAuthorizationQuery: {pageSize: 20, page: 1, userId: 0, username: null, createTime: null, updateTime: null},
      };
    },
    created: function () {
      this.listUserAuthorization()
      util.simpleDealApi(api.listPermission())
        .then(res => {
          if (res.data.status != 1) {
            alert(res.data.massage)
            return;
          }
          this.permissions = res.data.data;
        })
    },
    methods: {
      remove(authorization) {
        util.simpleDealApi(api.removeAuthorization(authorization.authorizationId))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('删除成功')
            this.listUserAuthorization()
          })
      },
      listUserAuthorization: function () {
        util.simpleDealApi(api.listUserAuthorization(
          this.userAuthorizationQuery.pageSize,
          this.userAuthorizationQuery.page,
          this.userAuthorizationQuery.userId,
          this.userAuthorizationQuery.username,
          this.userAuthorizationQuery.createTime,
          this.userAuthorizationQuery.updateTime))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            this.userAuthorizations = res.data.data;
          })
      },
      add: function (userAuthorization) {
        util.simpleDealApi(api.addAuthorization(userAuthorization.user.userId, userAuthorization.permissionId))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('添加成功')
            this.listUserAuthorization()
          })
      }
    }
  }
</script>

<style scoped>
</style>
