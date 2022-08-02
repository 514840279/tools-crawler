<template>
  <div>
    <div style="float: right;">
      <el-input v-model="searchText"></el-input>
    </div>
    <el-table ref="singleTableRef" :data="searchTable(tables)" highlight-current-row style="width: 100%" @current-change="handleCurrentChange">
      <el-table-column v-for="(column, index) in activeColumns" :key="index" :property="column.name" :label="column.title">
      </el-table-column>
    </el-table>
    <div style="text-align: center;margin-top:8px">
      <el-button type="primary" :disabled="true">创建新表</el-button>
      <el-button type="primary" @click="gotoNext"> 下一项</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { computed, ref } from "vue";
import { SysDbmsTabsTableInfo } from "../../../interface/SysDbms";
import { Column } from "../../../interface/Table";
import http from "../../../plugins/http";
import { loadStore } from "../../../stone/LoadFile";


// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { tables, tableInfo, tableColumnsInfo } = storeToRefs(store);

const columns: Array<Column> = [{
  name: "uuid",
  title: "uuid",
  show: false,
}, {
  name: "tabsName",
  title: "表名称",
  show: true,
}, {
  name: "tabsDesc",
  title: "表注释",
}, {
  name: "tabsRows",
  title: "表数据量",
}, {
  name: "sort",
  title: "排序",
}];

let searchText = ref<string>();

const handleCurrentChange = (val: SysDbmsTabsTableInfo | undefined) => {
  tableInfo.value = val;
}

const searchTable = function (tables: Array<SysDbmsTabsTableInfo>): Array<SysDbmsTabsTableInfo> {
  if (searchText.value == null || searchText.value.length == 0) {
    return tables;
  } else {
    let aa: Array<SysDbmsTabsTableInfo> = [];
    tables.forEach(element => {
      if (element.tabsName.indexOf(String(searchText.value)) > -1) {
        aa.push(element);
      } else {
        let desc = element.tabsDesc;
        if (desc != null && desc.indexOf(String(searchText.value)) > -1) {
          aa.push(element);
        }
      }
    });
    return aa;
  }

}

const activeColumns = computed<Column[]>(() => {
  let temp: Column[] = new Array<Column>();
  columns.forEach(column => {
    if (column.show == null || column.show) {
      temp.push(column);
    }
  });
  return temp;
})
const emit = defineEmits(["next"]);

// 加载对应表的字段信息
function gotoNext() {
  let param = { tabsUuid: tableInfo.value?.uuid }
  http.post<any>("/serve/sysDbmsTabsColsInfo/findAll", param).then((reponse) => {
    if (reponse.code == 200) {
      tableColumnsInfo.value = reponse.data;
      emit('next')
    }
  }).catch((err) => {
    // TODO
  });
}


</script>

<style scoped>
</style>