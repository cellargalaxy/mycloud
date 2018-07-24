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

  export default {
    name: "exception-info",
    data() {
      return {
        exceptionInfos: [
          {
            status: 0,
            massage: '好滋好味鸡蛋仔',
            date: '江浙小吃、小吃零食',
            exceptionStack: '荷兰优质淡奶，奶香浓而不腻',
          },
        ]
      }
    },
    created: function () {
      this.$http.get('/api/log/listExceptionInfo').then(response => {
        this.exceptionInfos = response.data.data;
        for (let i = 0; i < this.exceptionInfos.length; i++) {
          this.exceptionInfos[i].date = this.$formatDate(this.exceptionInfos[i].date, 'yyyy-MM-dd hh:mm:ss');
        }
      });
    },
  }
</script>

<style scoped>
</style>
