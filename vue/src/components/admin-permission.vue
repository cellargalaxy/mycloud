<template>
  <el-container>
    <el-header>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="权限id">
          <el-input v-model="permissionForm.permissionId" :value="permissionForm.permissionId" size="mini" type="number" placeholder="权限id"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-input v-model="permissionForm.permissionMark" :value="permissionForm.permissionMark" size="mini" type="text" placeholder="权限" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="add" size="mini" type="success">添加</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table :data="permissions" v-loading="loading">
        <el-table-column label="权限id" prop="permissionId"></el-table-column>
        <el-table-column label="权限">
          <template slot-scope="scope">
            <el-input :value="permissions[scope.$index].permissionMark"
                      v-model="permissions[scope.$index].permissionMark"
                      size="mini" type="text" placeholder="权限"></el-input>
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
  </el-container>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "admin-permission",
    data() {
      return {
        loading: false,
        permissions: [],
        permissionForm:{permissionId: 0, permissionMark: null,}
      }
    },
    created: function () {
      this.listPermission();
    },
    methods: {
      add() {
        api.addPermission(this.permissionForm.permissionId, this.permissionForm.permissionMark)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.listPermission();
            this.$message.success('添加成功')
            this.permissionForm = {permissionId: 0, permissionMark: null,};
          })
      },
      change(row) {
        api.changePermission(row.permissionId, row.permissionMark)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('修改成功')
            this.listPermission()
          })
      },
      remove(row) {
        api.removePermission(row.permissionId)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('删除成功')
            this.listPermission()
          })
      },
      listPermission: function () {
        this.loading = true;
        api.listPermission()
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.permissions = res.data.data;
            for (let i = 0; i < this.permissions.length; i++) {
              this.permissions[i].createTime = util.formatTimestamp(this.permissions[i].createTime, 'yyyy-MM-dd hh:mm:ss')
              this.permissions[i].updateTime = util.formatTimestamp(this.permissions[i].updateTime, 'yyyy-MM-dd hh:mm:ss')
            }
            this.loading = false;
          })
      },
    },
    components: {
    },
  }
</script>

<style scoped>

</style>
