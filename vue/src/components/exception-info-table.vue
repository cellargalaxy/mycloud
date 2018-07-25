<template>
  <el-table :data="exceptionInfos">

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
    name: "exception-info-table",
    data() {
      return {
        exceptionInfos: []
      }
    },
    created: function () {
      util.simpleDealApi(api.listExceptionInfo())
        .then(res => {
          if (res.data.status != 1) {
            alert(res.data.massage)
            return;
          }
          this.exceptionInfos = response.data.data;
          for (let i = 0; i < this.exceptionInfos.length; i++) {
            this.exceptionInfos[i].date = util.formatTimestamp(this.exceptionInfos[i].date, 'yyyy-MM-dd hh:mm:ss');
          }
        })
    },
  }
</script>

<style scoped>
</style>
