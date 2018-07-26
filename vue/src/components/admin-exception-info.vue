<template>
  <el-table :data="exceptionInfos" v-loading="loading">

    <el-table-column type="expand">
      <template slot-scope="props">
        <pre><code v-text="props.row.exceptionStack"></code></pre>
      </template>
    </el-table-column>

    <el-table-column label="状态" prop="status">
    </el-table-column>
    <el-table-column label="信息" prop="massage">
    </el-table-column>
    <el-table-column label="日期" prop="date">
    </el-table-column>

  </el-table>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'

  export default {
    name: "admin-exception-info",
    data() {
      return {
        loading: false,
        exceptionInfos: []
      }
    },
    created: function () {
      this.loading = true;
      api.listExceptionInfo()
        .then(res => {
          if (res.data.status != 1) {
            this.$message.error(res.data.massage)
            return;
          }
          this.exceptionInfos = res.data.data;
          for (let i = 0; i < this.exceptionInfos.length; i++) {
            this.exceptionInfos[i].date = util.formatTimestamp(this.exceptionInfos[i].date, 'yyyy-MM-dd hh:mm:ss');
          }
          this.loading = false;
        })
    },
  }
</script>

<style scoped>
</style>
