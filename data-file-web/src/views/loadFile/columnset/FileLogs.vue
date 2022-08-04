<template>
  <div>
    <el-space :fill="true" wrap style="width: 95%;text-align: center;">
      <el-card class="box-card">
        <el-row>
          <el-col :span="24">
            <div style="text-align: right;">
              <el-button-group>
                <el-button type="info" @click="reStartJob">重启</el-button>
                <el-button type="info" @click="stopJob">停止</el-button>
                <el-button type="info" @click="continueJob">继续</el-button>
              </el-button-group>
              <el-button type="info" @click="showPage">修改配置</el-button>
            </div>
          </el-col>
        </el-row>
        <el-popover placement="top-start" title="Title" :width="200" trigger="hover" content="this is content, this is content, this is content">
          阿斯顿撒
        </el-popover>
        <el-table id="tableid" v-loading="loading" :data="dataList" style="width: 100%">
          <el-table-column prop="runState" label="状态" width="150" />
          <el-table-column prop="complateRows" label="执行提交行" width="150" />
          <el-table-column prop="errorRows" label="执行失败行" width="150" />
          <el-table-column prop="errorMessage" label="失败消息" width="550" />
          <el-table-column prop="errorFile" label="失败文件保存位置" width="550" />
          <el-table-column prop="updateTime" label="更新时间" width="150" />
          <el-table-column prop="createTime" label="创建时间" width="150" />
        </el-table>
        <el-row>
          <el-col :span="12" :offset="12">
            <el-pagination class="pagex" style="float: right;" background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="param.pageNumber" :page-sizes="param.sizes" :page-size="param.pageSize" :pager-count="5" layout="total, sizes, prev, pager, next, jumper" :total="param.totalElements" :hide-on-single-page="true">
            </el-pagination>
          </el-col>
        </el-row>
      </el-card>
    </el-space>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from "vue";
import Table from '../../../components/table/Table.vue'
import { Column, OptionBtn, PageParam } from '../../../interface/Table'
import http from '../../../plugins/http';

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../../stone/LoadFile";
// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo } = storeToRefs(store);

const emit = defineEmits(["showPage"]);

let show = ref<boolean>(true);


// 请求参数
let param: PageParam = {
  pageNumber: 1,
  sizes: [10, 20, 50, 100],
  pageSize: 10,
  totalElements: 0,
  info: {},
  searchList: [{
    //   operator: 'and',
    //   column: "deleteFlag",
    //   title: "删除",
    //   symbol: "eq",
    //   data: "0",
    //   showdata: true,
    // }, {
    operator: 'and',
    column: "fileUuid",
    title: "删除",
    symbol: "eq",
    data: fileInfo.value.uuid,
    showdata: true,
  }],
  sortList: [{
    sortIndex: 1,
    sortTitle: "updateTime",
    sortOrder: "desc",
    sortName: "updateTime"
  }, {
    sortIndex: 2,
    sortTitle: "createTime",
    sortOrder: "desc",
    sortName: "createTime",
  }]

};

// 表加载
let loading = ref<Boolean>(true);
// 表格数据
let dataList = ref<Array<any>>([]);

onBeforeMount(() => {
  initTable();
})

// 初始化表格
function initTable() {
  let rootUrl: string = '/serve/sysLoadFileLogInfo';

}

// 重启一个任务
function reStartJob() {

}

// 停止任务
function stopJob() {

}

// 继续执行job
function continueJob() {

}

// 每页大小
function handleSizeChange(val: Number): void {
  param.pageSize = val;
  initTable();
}
// 翻页 
function handleCurrentChange(val: number): void {
  param.pageNumber = val;
  initTable();
}

// 去配置
function showPage() {
  emit("showPage", '1');
}

</script>

<style scoped>
</style>