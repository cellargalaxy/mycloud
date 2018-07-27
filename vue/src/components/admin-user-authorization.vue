<template>
  <el-container>
    <el-main>
      <el-table :data="userAuthorizations" v-loading="loading">
        <el-table-column label="用户" prop="user.username"></el-table-column>
        <el-table-column label="权限">
          <template slot-scope="scope">
            <el-tag v-for="userAuthorization in userAuthorizations[scope.$index].authorizations"
                    :key="userAuthorization.authorizationId" closable :disable-transitions="false"
                    @close="remove(userAuthorization)" size="mini">
              {{userAuthorization.permissionMark}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="授权">
          <template slot-scope="scope">
            <el-select v-model="userAuthorizations[scope.$index].permissionId" filterable clearable placeholder="请选择"
                       size="mini">
              <el-option
                v-for="permission in permissions"
                :key="permission.permissionId"
                :label="permission.permissionMark"
                :value="permission.permissionId">
              </el-option>
            </el-select>
            <el-button type="success" @click="add(scope.row)" size="mini">添加</el-button>
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
  import pagination from './pagination'

  export default {
    name: "admin-user-authorization",
    data() {
      return {
        loading: false,
        total: 0,
        permissions: [],
        userAuthorizations: [],
        userForm: {username: null, userPassword: null,},
        userAuthorizationQuery: {pageSize: 10, page: 1, userId: 0, username: null, createTime: null, updateTime: null},
      };
    },
    created: function () {
      this.listUserAuthorization()
      api.getUserCount(
        this.userAuthorizationQuery.pageSize,
        this.userAuthorizationQuery.page,
        this.userAuthorizationQuery.userId,
        this.userAuthorizationQuery.username,
        this.userAuthorizationQuery.createTime,
        this.userAuthorizationQuery.updateTime)
        .then(res => {
          if (res.data.status != 1) {
            this.$message.error(res.data.massage)
            return;
          }
          this.total = res.data.data;
        })
      api.listPermission()
        .then(res => {
          if (res.data.status != 1) {
            this.$message.error(res.data.massage)
            return;
          }
          this.permissions = res.data.data;
        })
    },
    methods: {
      changePageSize: function (pageSize) {
        this.userAuthorizationQuery.pageSize = pageSize
      },
      turnPage: function (page) {
        this.userAuthorizationQuery.page = page
        this.listUserAuthorization()
      },
      remove(authorization) {
        api.removeAuthorization(authorization.authorizationId)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('删除成功')
            this.listUserAuthorization()
          })
      },
      listUserAuthorization: function () {
        this.loading = true;
        api.listUserAuthorization(
          this.userAuthorizationQuery.pageSize,
          this.userAuthorizationQuery.page,
          this.userAuthorizationQuery.userId,
          this.userAuthorizationQuery.username,
          this.userAuthorizationQuery.createTime,
          this.userAuthorizationQuery.updateTime)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.userAuthorizations = res.data.data;
            this.loading = false;
          })
      },
      add: function (userAuthorization) {
        api.addAuthorization(userAuthorization.user.userId, userAuthorization.permissionId)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('添加成功')
            this.listUserAuthorization()
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
