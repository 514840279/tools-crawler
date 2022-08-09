<template>
  <div id="load-file">
    <el-collapse-transition>
      <div v-if="show">
        <h1 class="title">文件信息列表</h1>
        <el-card class="box-card" shadow="always">
          <Table :columns="columns" :rootUrl="rootUrl" :optionBtn="localOptionBtn" @onClickRow="onClickRow" />
        </el-card>
      </div>
    </el-collapse-transition>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Table from '../../components/table/Table.vue'
import { Column, OptionBtn } from '../../interface/Table'
import { router } from '../../router';

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../stone/LoadFile";
import { SysLoadFileInfo } from '../../interface/LoadFile';

// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo } = storeToRefs(store);
let show = ref<boolean>(true);

let rootUrl: string = '/sysLoadFileInfo';
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
  name: "fileState",
  title: "状态",
  align: 'left',
  search: true,
  // searchDataArray: [{ value: "未配置", lebal: "未配置" }]
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
    option: true,
    info: false, // 详细 暂时无用
    upd: false, // 修改
    state: false, // 修改表中应有固定字段 delete_flag 默认值为0 逻辑删除字段 执行update 
    del: true, // 删除 执行delete sql
  }
});


function onClickRow(row: SysLoadFileInfo) {
  show.value = false;
  setTimeout(function () {
    row.skip == null ? row.skip = 0 : null;
    fileInfo.value = row;
    router.push({ path: "/columnset" })
  }, 300) //
}
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