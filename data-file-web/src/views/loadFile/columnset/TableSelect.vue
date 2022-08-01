<template>
  <div>
    TableSelect
    <div style="text-align: center;margin-top:8px">
      <el-button type="primary">确认配置信息</el-button>
      <el-button type="primary" @click="$emit('next')"> 下一项</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits(["next"]);

</script>

<style scoped>
</style>