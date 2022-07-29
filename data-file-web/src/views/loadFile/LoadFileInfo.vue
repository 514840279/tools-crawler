<template>
  <div id="load-file">
    <h1 class="title">LoadData</h1>
    <el-card class="box-card">
      <Table :columns="columns" :rootUrl="rootUrl" :optionBtn="localOptionBtn" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from 'vue';
import http from '../../plugins/http';
import Table from '../../components/table/Table.vue'
import { Column, OptionBtn, PageParam } from '../../interface/Table'



let rootUrl: string = '/serve/sysLoadFileInfo';
let columns: Array<Column> = [{
  name: "uuid",
  title: "uuid",
  align: 'left',
  show: false,
}, {
  name: "fileName",
  title: "文件名",
  align: 'left',
  sort: true,
  search: true
}, {
  name: "fileType",
  title: "扩展名",
  align: 'left',
  sort: true,
  search: true
}, {
  name: "fileSize",
  title: "文件大小",
  align: 'left',
}, {
  name: "typeOrder",
  title: "显示顺序",
  align: 'left',
  sort: true,
  // search: true
}];
let localOptionBtn = ref<OptionBtn>({
  search: true, // 开启查询功能
  searchParam: false, // 开启查询功能
  sort: false, // 开启排序功能
  add: false, // 添加
  page: true, // 翻页
  opt: true, // 每条数据后端操作搭配optbtn使用
  optbtn: { // 
    info: false, // 详细 暂时无用
    upd: false, // 修改
    state: false, // 修改表中应有固定字段 delete_flag 默认值为0 逻辑删除字段 执行update 
    del: true, // 删除 执行delete sql
  }
});
let param = ref<PageParam>({
  pageNumber: 1,
  pageSize: 10,
  sizes: [10, 20, 30],
  totalElements: 0,
  info: {

  }
});
onBeforeMount(() => {

})
</script>

<style lang="scss" scoped>
#load-file {
  text-align: center;

  .title {
    text-align: center;
  }

  .box-card {
    width: 95%;
    margin: 0 auto;
  }
}
</style>