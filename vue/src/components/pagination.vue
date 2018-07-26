<template>
  <div class="block">
    <el-pagination
      @size-change="changePageSize"
      @current-change="turnPage"
      :current-page="1"
      :page-sizes="[10, 50, 100]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    name: "pagination",
    methods: {
      changePageSize(pageSize) {
        this.$emit('changePageSize', pageSize)
      },
      turnPage(page) {
        this.$emit('turnPage', page)
      }
    },
    data() {
      return {
      };
    },
    props: ['total'],
  }
</script>

<style scoped>

</style>
