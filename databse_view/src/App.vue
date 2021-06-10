<template>
  <Header />
  <router-view :key="this.$route.params.name" />
</template>

<script>
import Header from './components/header.vue'
export default {
  components:{
    Header
  }
}
</script>

<style>
  /* 解决element-ui的table表格控件表头与内容列不对齐问题 */
  .el-table th.gutter{
    display: table-cell!important;
  }
</style>
