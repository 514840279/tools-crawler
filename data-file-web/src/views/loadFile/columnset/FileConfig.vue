
<template>
  <div style="width: 100%;">
    <el-space :fill="true" wrap style="width: 100%;">
      <div>
        <el-descriptions title="文件信息" direction="vertical" :column="7" :border="true">
          <el-descriptions-item label="文件名称">{{ fileInfo.fileName }}</el-descriptions-item>
          <el-descriptions-item label="文件类型">{{ fileInfo.fileType }}</el-descriptions-item>
          <el-descriptions-item label="文件大小">{{ fileInfo.fileSize }}</el-descriptions-item>
          <el-descriptions-item label="跳过头行">
            <el-input-number v-model="fileInfo.skip" :min="0" @change="readFile" />
          </el-descriptions-item>
          <el-descriptions-item label="分割符号" v-if="fileInfo.fileType == FileType.TXT || fileInfo.fileType == FileType.CSV">
            <el-input v-model="fileInfo.separator" placeholder="分割符号：通常以逗号','，'tab'分割字段" @change="readFile" />
          </el-descriptions-item>
          <el-descriptions-item label="限定符号" v-if="fileInfo.fileType == FileType.TXT || fileInfo.fileType == FileType.CSV">
            <el-input v-model="fileInfo.enclosed" placeholder="限定符号:通常以单引号，双引号，反引号包围字段" @change="readFile" />
          </el-descriptions-item>
          <el-descriptions-item label="首行标题">
            <el-switch v-model="fileInfo.hasHead" class="ml-2" inline-prompt style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" active-text="Y" inactive-text="N" active-value="Y" inactive-value="N" @change="readHeadFile" />
          </el-descriptions-item>
        </el-descriptions>
        <div style="text-align: center;margin-top:8px">
          <el-button type="primary" @click="saveFileConfig">确认配置信息</el-button>
          <el-button type="primary" @click="$emit('next')"> 下一项</el-button>
        </div>
      </div>
      <div v-if="datas != null" style="width: 100%">
        <el-table :data="datas" :border="true" height="400" style="width: 100%">
          <el-table-column v-for="(column, index) in fileColumns" :key="index" :prop="column.columnName" :label="column.columnDesc" width="150">
            <template #header>
              <el-input v-model="column.columnDesc" :title="'name:' + column.columnName + ',desc:' + column.columnDesc" />
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-space>
  </div>
</template>
<script setup lang='ts'>
import { nextTick, onMounted, ref } from 'vue';
import http from '../../../plugins/http';
import { FileType } from '../../../interface/LoadFile.js';
import { ElMessage } from 'element-plus'

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../../stone/LoadFile";
// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo, fileColumns } = storeToRefs(store);

// const emit = defineEmits(["next"]);


let datas = ref<Array<Map<string, string>> | null>();

onMounted(() => {
  nextTick(() => {
    readFile();
  })
})


// 读取文件信息
function readFile() {

  let param =
  {
    info: fileInfo.value,
    columns: fileColumns.value
  }
  readFileData(param);
}

function readFileData(param: any) {
  // showdata.value = false;
  datas.value = null;
  http.post<any>("/serve/sysLoadFileInfo/readFile", param).then((reponse) => {
    if (reponse.code == 200) {
      fileColumns.value = reponse.data.columns;
      fileInfo.value = reponse.data.info;
      datas.value = reponse.data.datas;
      // showdata.value = true;
    }
  }).catch((err) => {
    // TODO
  });
}


function readHeadFile() {
  let param =
  {
    info: fileInfo.value,
  }
  readFileData(param);
}


// 提交配置信息
function saveFileConfig() {
  let param = {
    info: fileInfo.value,
    columns: fileColumns.value
  }
  http.post<any>("/serve/sysLoadFileInfo/saveFileConfig", param).then((reponse) => {
    if (reponse.code == 200) {
      ElMessage({
        message: reponse.data,
        type: 'success',
      })

    }
  }).catch((err) => {
    // TODO
  });
}

</script>

<style lang='scss' scoped>
</style>