<template>
  <el-row>
    <el-col :span="12">
      <Rel></Rel>
    </el-col>
    <el-col :span="12">
      <Load></Load>
    </el-col>
  </el-row>

</template>

<script setup lang="ts">
import Rel from './Rel.vue'
import Load from './Load.vue'
</script>

<style scoped>
</style>

