<template >
  <div id="load-index" @click="ocli">
    <el-collapse-transition>
      <div v-if="show">
        <img src="/src/assets/logo/16590618987737.png" />
        <h1 class="title">LoadData</h1>
      </div>
    </el-collapse-transition>

  </div>
</template>

<script setup lang="ts">import { onMounted, ref } from 'vue';
import { router } from '../../router';

const show = ref(false)
onMounted(() => {
  show.value = !show.value;
})

const gotoFile = function () {
  setTimeout(function () {
    router.push({ name: "fileinfo" })
  }, 300) // 

}
const ocli = function () {
  show.value = !show.value;
  gotoFile();
}

document.onkeydown = function () {
  show.value = !show.value;
  gotoFile();
}
</script>

<style lang="scss" scoped>
#load-index {
  width: 100%;
  height: 100%;
  text-align: center;

  .title {}

  .transition-box {
    margin-bottom: 10px;
    width: 200px;
    height: 100px;
    border-radius: 4px;
    background-color: #409eff;
    text-align: center;
    color: #fff;
    padding: 40px 20px;
    box-sizing: border-box;
    margin-right: 20px;
  }
}
</style>