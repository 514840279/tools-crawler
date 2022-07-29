<template >
  <div id="load-data">
    <el-collapse-transition>
      <!-- 路由匹配到的组件将渲染在这里 -->

      <router-view></router-view>
    </el-collapse-transition>

  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { router } from '../router';



onMounted(() => {
  // 默认展示页面
  gotoindex()
})
function gotoindex() {
  router.push({ path: "/loadIndex" });
}
</script>

<style lang="scss" scoped>
#load-data {}
</style>