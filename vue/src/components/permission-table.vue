<template>
  <el-table :data="permissions">
    <el-table-column label="权限id" prop="permissionId"></el-table-column>
    <el-table-column label="权限">
      <template slot-scope="scope">
        <el-input :value="permissions[scope.$index].permissionMark" v-model="permissions[scope.$index].permissionMark"
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
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "permission-table",
    data() {
      return {
        permissions: [],
      }
    },
    created: function () {
      this.listPermission();
    },
    methods: {
      change(row) {
        util.simpleDealApi(api.changePermission(row.permissionId, row.permissionMark))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('修改成功')
            this.listPermission()
          })
      },
      remove(row) {
        util.simpleDealApi(api.removePermission(row.permissionId))
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            alert('删除成功')
            this.listPermission()
          })
      },
      listPermission: function () {
        util.simpleDealApi(api.listPermission())
          .then(res => {
            if (res.data.status != 1) {
              alert(res.data.massage)
              return;
            }
            this.permissions = res.data.data;
            for (let i = 0; i < this.permissions.length; i++) {
              this.permissions[i].createTime = util.formatTimestamp(this.permissions[i].createTime, 'yyyy-MM-dd hh:mm:ss')
              this.permissions[i].updateTime = util.formatTimestamp(this.permissions[i].updateTime, 'yyyy-MM-dd hh:mm:ss')
            }
          })
      },
    }
  }
</script>

<style scoped>

</style>
