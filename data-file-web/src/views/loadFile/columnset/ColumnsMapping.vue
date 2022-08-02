<template>
  <div>
    ColumnsMapping
    <div style="text-align: center;margin-top:8px">
      <el-button type="primary">确认配置信息</el-button>
      <el-button type="primary" @click="$emit('next')"> 下一项</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">

// 使普通数据变响应式的函数
import { storeToRefs } from "pinia";
import { loadStore } from "../../../stone/LoadFile";
// 实例化仓库
const store = loadStore();
// 解构并使数据具有响应式
const { fileInfo, fileColumns, fileColsMapping, tables, tableInfo, tableColumnsInfo } = storeToRefs(store);


const emit = defineEmits(["next"]);

</script>

<style scoped>
</style>