
<template>
  <Admin v-if="viewF === ViewType.Admin" />
  <Index v-if="viewF === ViewType.User" />
  <Login v-if="viewF === ViewType.Login" />
  <Error v-if="viewF === ViewType.Error" />
</template>
<script setup   lang="ts">


import Admin from './views/Admin.vue'
import Index from './views/index.vue'
import Login from './views/Login.vue'
import Error from './views/Error.vue'


import { ViewType } from './interface/View'

// TODO 判断登录后信息展示 默认登录页面 
let viewF: ViewType = ViewType.Admin;



</script>

<style>
html,
body {
  margin: 0px;
  padding: 0px;
}

.el-header {
  padding: 0px;
}
</style>
