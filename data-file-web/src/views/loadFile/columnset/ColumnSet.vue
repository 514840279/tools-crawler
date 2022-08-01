<template>
  <el-space :fill="true" wrap style="width: 100%;">
    <el-card class="box-card">
      <el-steps :active="confStep" finish-status="success" simple style="margin-top: 20px">
        <el-step title="文件处理" description="文件数据格式配置" />
        <el-step title="表选择" description="对应导入表选择" />
        <el-step title="映射关系" description="字段关系，格式化处理" />
      </el-steps>
    </el-card>
    <el-card class="box-card">
      <FileConfig v-if="confStep == 0" @next="confStep = 1"> </FileConfig>
      <TableSelect v-if="confStep == 1" @next="confStep = 2"></TableSelect>
      <ColumnsMapping v-if="confStep == 2" @next="$emit('showPage', '2')"></ColumnsMapping>
    </el-card>
  </el-space>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import FileConfig from './FileConfig.vue';
import TableSelect from './TableSelect.vue';
import ColumnsMapping from './ColumnsMapping.vue';

const emit = defineEmits(["showPage"]);



const confStep = ref<number>(0);


</script>

<style scoped>
</style>