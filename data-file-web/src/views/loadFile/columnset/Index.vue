<template>
  <div id="load-conf">
    <h1 class="title">{{ title }}</h1>
    <el-card class="box-card" shadow="always">
      <ColumnSet v-if="showPage == '1'" @showPage="showLogPage"></ColumnSet>
      <FileLogsVue v-if="showPage == '2'" @showPage="showLogPage"></FileLogsVue>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import ColumnSet from './ColumnSet.vue'
import FileLogsVue from './FileLogs.vue';

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../../stone/LoadFile";
import { SysLoadFileInfo } from '../../../interface/LoadFile';
import { onBeforeMount, ref } from "vue";
import http from '../../../plugins/http';

// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo, fileColumns, fileColsMapping, tableInfo, tableColumnsInfo } = storeToRefs(store);

// showPage: 1: 配置页面，2，日志页面
let showPage = ref<string>("1");
let title = ref<string>("配置信息");

onBeforeMount(() => {
  // 初始化 配置完成 页面展示日志页面，没有配置完的展示配置页面
  init();
})
// 初始化 配置完成 页面展示日志页面，没有配置完的展示配置页面
const init = function () {
  switch (fileInfo.value.fileState) {
    case "未配置":
      showPage.value = "1";
      title.value = "配置信息";
      // 加载已经配置的文件字段信息,
      initFileColumn();
      break;
    case "已配置":
      showPage.value = "2";
      title.value = "日志信息"
      initLogs();
      break;
  }

}
// 加载已经配置的文件字段信息,
const initFileColumn = function () {
  let param = { fileUuid: fileInfo.value.uuid }
  http.post<any>("/serve/sysLoadFileColsInfo/findAll", param).then((reponse) => {
    if (reponse.code == 200) {
      fileColumns.value = reponse.data;
      if (reponse.data != null) {
        // 加载字段映射信息 
        initMapping();
      }
    }
  }).catch((err) => {
    // TODO
  });
}
// 加载字段映射信息 
const initMapping = function () {
  let param = { fileUuid: fileInfo.value.uuid }
  http.post<any>("/serve/sysLoadFileColsMapping/findAll", param).then((reponse) => {
    if (reponse.code == 200) {
      fileColsMapping.value = reponse.data;
      if (reponse.data != null) {
        // 加载对应表信息
        initTables();
        // 加载对应表的字段信息
        initColumns();
      }
    }
  }).catch((err) => {
    // TODO
  });
}
// 加载所有表信息
const initTables = function () {
  let param = { uuid: fileColsMapping.value[0].tabsUuid }
  http.post<any>("/serve/sysDbmsTabsTableInfo/findOne", param).then((reponse) => {
    if (reponse.code == 200) {
      tableInfo.value = reponse.data;
    }
  }).catch((err) => {
    // TODO
  });
}
// 加载对应表的字段信息
const initColumns = function () {
  let param = { tabsUuid: fileColsMapping.value[0].tabsUuid }
  http.post<any>("/serve/sysDbmsTabsColsInfo/findAll", param).then((reponse) => {
    if (reponse.code == 200) {
      tableColumnsInfo.value = reponse.data;
    }
  }).catch((err) => {
    // TODO
  });
}
// 加载对应表的字段信息
const initLogs = function () {

}

const showLogPage = function (value: string) {
  showPage.value = value;
  if (showPage.value == "1") {
    title.value = "配置信息";
  } else {
    title.value = "日志信息"
  }
}

</script>

<style lang="scss" scoped>
#load-conf {
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