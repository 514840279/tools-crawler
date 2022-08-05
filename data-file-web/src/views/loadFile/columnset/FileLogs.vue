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
        <el-table id="tableid" v-loading="loading" :data="dataList" style="width: 100%">
          <el-table-column prop="complateRows" label="执行提交行" width="150" />
          <el-table-column prop="errorRows" label="执行失败行" width="150" />
          <el-table-column prop="errorMessage" label="失败消息" width="150" />
          <el-table-column prop="errorFile" label="失败文件保存位置" />
          <el-table-column prop="runState" label="状态" width="150" />
          <el-table-column prop="createTime" label="创建时间" width="150" />
          <el-table-column prop="updateTime" label="更新时间" width="150" />
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
import { PageParam } from '../../../interface/Table'
import http from '../../../plugins/http';
import { ElMessage } from 'element-plus'

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../../stone/LoadFile";
// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo } = storeToRefs(store);

const emit = defineEmits(["showPage"]);


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
  dataList.value = [];
  loading.value = true;
  http.post<any>("/serve/sysLoadFileLogInfo/page", param).then((reponse) => {
    if (reponse.code == 200) {
      dataList.value = reponse.data.content;
      param.totalElements = reponse.data.totalElements;
    }
    loading.value = false;
  }).catch((err) => {
    // TODO
  });


}

// 重启一个任务
function reStartJob() {
  http.post<any>("/serve/sysLoadFileLogInfo/reStartJob", fileInfo.value).then((reponse) => {
    if (reponse.code == 200) {
      ElMessage({
        message: reponse.data,
        type: 'success',
      })
      setTimeout(function () {
        initTable()
      }, 1000) // 
    }
  }).catch((err) => {
    // TODO
  });
}

// 停止任务
function stopJob() {
  http.post<any>("/serve/sysLoadFileLogInfo/stopJob", fileInfo.value).then((reponse) => {
    if (reponse.code == 200) {
      ElMessage({
        message: reponse.data,
        type: 'success',
      })
      setTimeout(function () {
        initTable()
      }, 1000) // 

    }
  }).catch((err) => {
    // TODO
  });
}

// 继续执行job
function continueJob() {
  http.post<any>("/serve/sysLoadFileLogInfo/continueJob", fileInfo.value).then((reponse) => {
    if (reponse.code == 200) {
      ElMessage({
        message: reponse.data,
        type: 'success',
      })
      setTimeout(function () {
        initTable()
      }, 1000) // 
    }
  }).catch((err) => {
    // TODO
  });
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