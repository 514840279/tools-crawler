<template>
  <div>
    <el-row>
      <el-col>
        <div style="text-align: right;">
          <el-input v-model="searchText" :autofocus="true" label="赛选表" placeholder="输入表名或者表注释进行筛选。" style="width: 180px;"></el-input>
        </div>
      </el-col>
    </el-row>
    <el-scrollbar height="400px">
      <el-table ref="singleTableRef" :data="searchTable(tables)" :highlight-current-row="true" style="width: 100%" @current-change="handleCurrentChange" :setCurrentRow="tableInfo">
        <el-table-column v-for="(column, index) in activeColumns" :key="index" :property="column.name" :label="column.title">
        </el-table-column>
      </el-table>
    </el-scrollbar>
    <div style="text-align: center;margin-top:8px">
      <el-button type="primary" @click="$emit('gotoBefor')"> 上一项</el-button>
      <el-button type="primary" @click="toShowDialog">创建新表</el-button>
      <el-button type="primary" @click="gotoNext"> 下一项</el-button>
    </div>
  </div>
  <el-dialog v-model="showDialog" title="Outer Dialog" draggable>
    <template #default>
      <el-input v-model="sqlText" :rows="fileColumns.length + 3" placeholder="Please input" show-word-limit type="textarea" />
    </template>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="showDialog = false">关闭</el-button>
        <el-button type="primary" @click="toCreateTable">生成表</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
// 使普通数据变响应式的函数
import { ElTable } from "element-plus";
import { storeToRefs } from "pinia";
import { computed, onMounted, ref } from "vue";
import { SysDbmsTabsTableInfo } from "../../../interface/SysDbms";
import { Column } from "../../../interface/Table";
import http from "../../../plugins/http";
import { loadStore } from "../../../stone/LoadFile";


// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo, fileColumns, tables, tableInfo, tableColumnsInfo } = storeToRefs(store);

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
let showDialog = ref<boolean>(false);
let sqlText = ref<string>("");

let singleTableRef = ref<InstanceType<typeof ElTable>>();

onMounted(() => {
  // nextTick(() => {
  tables.value.forEach(tab => {
    if (tab.uuid == tableInfo.value.uuid) {
      // console.log(tableInfo.value, '21321', tab)
      singleTableRef.value!.setCurrentRow(tab)
    }
  });
  // })
})

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

const emit = defineEmits(["next", "gotoBefor"]);

// 加载对应表的字段信息
function gotoNext() {
  let param = { info: { tabsUuid: tableInfo.value?.uuid }, sortList: [{ sortIndex: 1, sortOrder: "asc", sortName: "sort", }] }
  http.post<any>("/sysDbmsTabsColsInfo/findAllBySort", param).then((reponse) => {
    if (reponse.code == 200) {
      tableColumnsInfo.value = reponse.data;
      emit('next')
    }
  }).catch((err) => {
    // TODO
  });
}

// 构造语句
const toShowDialog = function () {
  sqlText.value = "CREATE TABLE " + sqlName(fileInfo.value.fileName) + "  ( \r\n"
  for (let index = 0; index < fileColumns.value.length; index++) {
    const col = fileColumns.value[index];
    if (index > 0) {
      sqlText.value += "  ,"
    } else {
      sqlText.value += "   "
    }
    sqlText.value += sqlName(col.columnDesc == null ? col.columnName : col.columnDesc) + "  " + col.columnType + " \r\n";
  }
  sqlText.value += " )";

  showDialog.value = true
}

// 简单处理名称使其符合数据库要求，字节长度不超过30，没有特殊的符号要求，
const sqlName = function (name: string | undefined): string {
  if (name == null) {
    return "未知名称"
  }
  let tem = name;
  tem = tem.replaceAll(new RegExp(/\.|\(|\)|（|）|\[|\]|{|}|\$|@|!|【|】| |　|；|;/ig), "_");
  while (getBytesLength(tem) > 30) {
    tem = tem.substring(0, tem.length - 2);
  }

  return "`" + tem + "`";
}

//字符编码数值对应的存储长度：
//UCS-2编码(16进制) UTF-8 字节流(二进制)
//0000 - 007F       0xxxxxxx （1字节）
//0080 - 07FF       110xxxxx 10xxxxxx （2字节）
//0800 - FFFF       1110xxxx 10xxxxxx 10xxxxxx （3字节）
const getBytesLength = function (str: string): number {
  var totalLength = 0;
  var charCode;
  for (var i = 0; i < str.length; i++) {
    charCode = str.charCodeAt(i);
    if (charCode < 0x007f) {
      totalLength++;
    } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
      totalLength += 2;
    } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
      totalLength += 3;
    } else {
      totalLength += 4;
    }
  }
  return totalLength;
}

// 调用表生成策略
const toCreateTable = function () {
  let param = {
    info: fileInfo.value,
    columns: fileColumns.value,
    sqlText: sqlText.value
  }
  http.post<any>("/sysLoadFileInfo/sqlText", param).then((reponse) => {
    if (reponse.code == 200) {
      tableInfo.value = reponse.data;
      searchText.value = tableInfo.value?.tabsName;
      if (tableInfo.value != null) {
        tables.value.push(tableInfo.value)
      }
      showDialog.value = false;
    }
  }).catch((err) => {
    // TODO
  });

}


</script>

<style scoped>
</style>