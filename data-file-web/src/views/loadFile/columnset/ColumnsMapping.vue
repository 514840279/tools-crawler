<template>
  <div id="columnsMapping">
    <el-row class="select-row">
      <el-col :span="5">
        <div class="boder-box">
          <div class="head-box">
            <div class="title">文件名</div>
            <span>{{ fileInfo.fileName }}</span>
          </div>
          <el-row class="col-head">
            <el-col :span="6">序号</el-col>
            <el-col :span="12">标题名</el-col>
          </el-row>
          <div class="row-box">
            <el-scrollbar height="400px">
              <div v-for="(fileCol, index) in activeFileColumn()" :key="index" :class="fileCol.uuid == selectFileColsInfo.uuid ? 'select-column-row' : 'row-text'" @click="selectFileColumns(fileCol)">
                <el-row>
                  <el-col :span="6" class="col-num"> {{ fileCol.sort }}</el-col>
                  <el-col :span="12"> {{ fileCol.columnDesc }}</el-col>
                </el-row>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </el-col>
      <el-col :span="7">
        <div class="boder-box">
          <div class="head-box">
            <div class="title">表名称</div>
            <span>{{ tableInfo?.tabsName }}</span>
          </div>
          <el-row class="col-head">
            <el-col :span="4">序号</el-col>
            <el-col :span="10">字段名</el-col>
            <el-col :span="10">字段注释</el-col>
          </el-row>
          <div class="row-box">
            <el-scrollbar height="400px">
              <div class="row-text" v-for="(tableCol, index) in activeTableColumn()" :key="index" :class="tableCol.uuid == selectTableColsInfo.uuid ? 'select-column-row' : 'row-text'" @click="selectTableColumns(tableCol)">
                <el-row>
                  <el-col :span="4" class="col-num">{{ tableCol.sort }}</el-col>
                  <el-col :span="10">{{ tableCol.colsName }}</el-col>
                  <el-col :span="10">{{ tableCol.colsDesc }}</el-col>
                </el-row>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="button-box">
          <el-row>
            <el-col :span="24">
              <el-button color="#626aef" plain @click="guessMapping">猜一猜</el-button>
            </el-col>
            <el-col :span="24">
              <el-button color="#626aef" plain @click="pushMapping">添加关系 ></el-button>
            </el-col>
            <el-col :span="24">
              <el-button color="#626aef" plain @click="rmMapping"> &lt; 移除关系</el-button>
            </el-col>
            <el-col :span="24">
              <el-button color="#626aef" plain @click="rmMappingAll"> &lt;&lt; 全部移除</el-button>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="10">
        <div class="boder-box">
          <div class="head-box">
            <div class="title">映射关系</div>
          </div>
          <el-row class="col-head">
            <el-col :span="6">标题名</el-col>
            <el-col :span="6">字段名</el-col>
            <el-col :span="6">字段注释</el-col>
          </el-row>
          <div class="row-box">
            <el-scrollbar height="400px">
              <div class="row-text" v-for="(mapping, index) in fileColsMapping" :key="index" :class="mapping.uuid == selectMappingInfo.uuid ? 'select-column-row' : 'row-text'" @click="selectMapping(mapping)">
                <el-row>
                  <el-col :span="6">{{ activeFileTile(mapping.fileColumnUuid) }}</el-col>
                  <el-col :span="6">{{ activeTableColumnName(mapping.tabsColumnUuid) }}</el-col>
                  <el-col :span="6">{{ activeTableColumnDesc(mapping.tabsColumnUuid) }}</el-col>
                </el-row>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </el-col>
    </el-row>
    <div style="text-align: center;margin-top:8px">
      <el-button type="primary" @click="$emit('gotoBefor')"> 上一项</el-button>
      <el-button type="primary" @click="saveMappingConfig">确认配置信息</el-button>
      <el-button type="primary" @click="$emit('next')"> 下一项</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { ref } from "vue";
import { SysLoadFileColsInfo, SysLoadFileColsMapping } from "../../../interface/LoadFile";
import { SysDbmsTabsCols } from "../../../interface/SysDbms";
import { loadStore } from "../../../stone/LoadFile";
import { ElMessage, ElLoading } from "element-plus";

import { DateUtils } from '../../../plugins/DateUtils';
import { Guid } from '../../../plugins/Guid';
import http from "../../../plugins/http";

// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo, fileColumns, fileColsMapping, tableInfo, tableColumnsInfo } = storeToRefs(store);


const emit = defineEmits(["next", "gotoBefor"]);

let selectFileColsInfo = ref<SysLoadFileColsInfo>({ uuid: '', fileUuid: '', columnName: '' });
let selectTableColsInfo = ref<SysDbmsTabsCols>({ uuid: "" });
let selectMappingInfo = ref<SysLoadFileColsMapping>({ uuid: "" });


// 列表展示 文件信息
const activeFileColumn = function (): Array<SysLoadFileColsInfo> {
  let result: Array<SysLoadFileColsInfo> = [];
  fileColumns.value.forEach(fileColumn => {
    let flag = true;
    fileColsMapping.value.forEach(mapColumn => {
      if (mapColumn.fileColumnUuid == fileColumn.uuid && mapColumn.fileUuid == fileColumn.fileUuid) {
        flag = false;
      }
    });
    if (flag) {
      result.push(fileColumn);
    }

  })
  return result;
}

// 列表展示 表信息
const activeTableColumn = function (): Array<SysDbmsTabsCols> {
  let result: Array<SysDbmsTabsCols> = [];
  tableColumnsInfo.value.forEach(tableColumn => {
    let flag = true;
    fileColsMapping.value.forEach(mapColumn => {
      if (mapColumn.tabsColumnUuid == tableColumn.uuid && mapColumn.tabsUuid == tableColumn.tabsUuid) {
        flag = false;
      }
    });
    if (flag) {
      result.push(tableColumn);
    }

  })
  return result;
}

// 列表展示 映射关系文件 标题
const activeFileTile = function (fileColumnUuid: string | undefined): string {
  let res = "";
  fileColumns.value.forEach(col => {
    if (col.uuid == fileColumnUuid) {
      res = col.columnDesc == null ? col.columnName : col.columnDesc;
    }
  })
  return res;
}

// 列表展示 映射关系 字段名称
const activeTableColumnName = function (tableColumnUuid: string | undefined): string {
  let res = "";
  tableColumnsInfo.value.forEach(col => {
    if (col.uuid == tableColumnUuid) {
      res = col.colsName;
    }
  })
  return res;
}

// 列表展示 映射关系 字段注释
const activeTableColumnDesc = function (tableColumnUuid: string | undefined): string | null {
  let res = null;
  tableColumnsInfo.value.forEach(col => {
    if (col.uuid == tableColumnUuid) {
      res = col.colsDesc;
    }
  })
  return res;
}

// 文件信息选中
function selectFileColumns(col: SysLoadFileColsInfo) {
  selectFileColsInfo.value = col;
}

// 表信息选中
function selectTableColumns(tableCol: SysDbmsTabsCols) {
  selectTableColsInfo.value = tableCol;
}

// 映射关系选中
function selectMapping(mapping: SysLoadFileColsMapping) {
  selectMappingInfo.value = mapping;
}

// 猜一猜
function guessMapping() {

  fileColumns.value.forEach(fileCol => {
    tableColumnsInfo.value.forEach(tableCol => {
      if (fileCol.columnName.indexOf(String(tableCol.colsName)) > -1
        || fileCol.columnName.indexOf(String(tableCol.colsDesc)) > -1
        || (fileCol.columnDesc != null && String(fileCol.columnDesc).indexOf(String(tableCol.colsDesc)) > -1)
        || (fileCol.columnDesc != null && String(fileCol.columnDesc).indexOf(String(tableCol.colsName)) > -1)) {
        let flag = true;
        fileColsMapping.value.forEach(mapping => {
          if (mapping.fileColumnUuid == fileCol.uuid || mapping.tabsColumnUuid == tableCol.uuid) {
            flag = false;
          }
        })
        if (flag) {
          fileColsMapping.value.push({
            uuid: Guid.uuid(),
            fileColumnUuid: fileCol.uuid,
            fileUuid: fileInfo.value.uuid,
            tabsUuid: tableInfo.value?.uuid,
            tabsColumnUuid: tableCol.uuid,
            sort: fileCol.sort,
            createTime: DateUtils.formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss'),
            deleteFlag: 0,
          });
        }
      }
    })
  })


}

// 添加关系
function pushMapping() {
  let flag = true;
  fileColsMapping.value.forEach(mapping => {
    if (mapping.fileColumnUuid == selectFileColsInfo.value.uuid || mapping.tabsColumnUuid == selectTableColsInfo.value.uuid) {
      flag = false;
    }
  })
  if (flag) {
    fileColsMapping.value.push({
      uuid: Guid.uuid(),
      fileColumnUuid: selectFileColsInfo.value.uuid,
      fileUuid: fileInfo.value.uuid,
      tabsUuid: tableInfo.value?.uuid,
      tabsColumnUuid: selectTableColsInfo.value.uuid,
      sort: selectFileColsInfo.value.sort,
      createTime: DateUtils.formatDate(new Date(), 'yyyy-MM-dd HH:mm:ss'),
      deleteFlag: 0,
    });
  }
}

// 移除选中的mapping
function rmMapping() {
  for (let index = 0; index < fileColsMapping.value.length; index++) {
    const element = fileColsMapping.value[index];
    if (element.uuid == selectMappingInfo.value.uuid) {
      fileColsMapping.value.splice(index, 1);
    }
  }
}

// 移除所有关系
function rmMappingAll() {
  fileColsMapping.value = []
}

// 保存映射关系
function saveMappingConfig() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  let param = {
    fileInfo: fileInfo.value,
    mappings: fileColsMapping.value
  }
  http.post<any>("/serve/sysLoadFileColsMapping/saveFileMappingConfig", param).then((reponse) => {
    if (reponse.code == 200) {
      // 延时 证明系统在后台处理
      setTimeout(function () {
        ElMessage({
          message: reponse.data,
          type: 'success',
        })
        loading.close();
        setTimeout(function () {
          emit("next");
        }, 1000) // 
      }, 3000) // 

    }
  }).catch((err) => {
    // TODO
  });
}
</script>

<style lang="scss" scoped>
#columnsMapping {
  text-align: left;


  .boder-box {
    border: 1px solid rgb(216, 208, 208);
    border-radius: 4px;
    padding-top: 0px;
    margin: 0 5px;
  }

  .head-box {
    // width: 99%;
    margin-top: 0px;
    display: block;
    height: 63px;
    background-color: rgba(177, 169, 169, 0.452);
    padding: 0px 4px;
    padding-bottom: 6px;
    overflow: hidden;
    // white-space: nowarp;
    // text-overflow: ellipsis;

    .title {
      text-align: center;
      font-size: 16px;
      font-weight: 600;
    }
  }

  .button-box {
    text-align: center;

    .el-row {
      margin-top: 120px;
    }

    .el-col {
      margin-top: 5px;
    }
  }

  .col-head {
    border-top: 1px solid rgb(235, 225, 225);
    font-size: 15;
    font-weight: 500;
    background-color: rgba(206, 201, 201, 0.521);
    height: 26px;
    text-align: center;
  }

  .row-box {
    padding: 6px 5px 5px 5px;

    .row-text {
      border-top: 1px solid rgb(235, 225, 225);
      height: 24px;

      .col-num {
        text-align: center;
      }
    }


    .select-column-row {
      background-color: aliceblue;
      border-top: 1px solid rgb(235, 225, 225);
      border-left: 1px solid rgb(235, 225, 225);
      border-right: 1px solid rgb(235, 225, 225);
      border-radius: 2px;
      margin-left: 1px;

      .col-num {
        text-align: center;
      }
    }
  }


}
</style>