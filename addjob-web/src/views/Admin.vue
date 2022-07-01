
<template>
  <el-container>
    <el-header>
      <el-menu :default-active="headMenu.activeIndex" :class="headMenu.class" :mode="headMenu.mode" @select="handleSelect" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item v-for="(menu, index) in headMenu.data" :key="index" :index="menu.index">{{ menu.text }}</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="60px" class="left">
        <!-- aside -->
        <el-menu :collapse="true" class="el-menu-vertical-demo " :default-openeds="openedsIndex" :default-active="aside.activeIndex">
          <el-sub-menu v-for="(subme, index) in aside.submenu" :key="index" :index="subme.index">
            <template #title>
              <el-icon>
                <component :is="subme.icon"></component>
              </el-icon>
              <span>{{ subme.text }}</span>
            </template>
            <!-- group menu -->
            <el-menu-item-group v-for="(group, ind) in subme.group" :key="ind" :title="group.title">
              <el-menu-item v-for="(data, i) in group.data" :key="i" :index="data.index">
                <span>{{ data.text }}</span>
              </el-menu-item>
            </el-menu-item-group>
            <!-- submenu -->
            <el-menu-item v-for="(data, i) in subme.data" :key="i" :index="data.index">
              <el-icon>
                <component :is="data.icon"></component>
              </el-icon>
              <span @click="handleBreadcrumb(subme, data)">{{ data.text }}</span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>
          <el-card class="box-card">
            <template #header>
              <!-- 面包屑 -->
              <el-breadcrumb v-if="currentList.length > 0" separator="/">
                <el-breadcrumb-item v-for="(item, i) in currentList" :key="i" :index="i" :to="{ path: item.path }">{{ item.text }}</el-breadcrumb-item>
              </el-breadcrumb>
            </template>
            <!-- 路由出口 -->
            <!-- 路由匹配到的组件将渲染在这里 -->
            <router-view></router-view>
          </el-card>
        </el-main>
        <el-footer class="footer">
          <Foot />
        </el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>
<script setup  lang="ts">
import Foot from '../components/home/Food.vue';

// import { ref, reactive, toRefs } from 'vue'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router';
import { Menu, MenuItem, Aside, Breadcrumb } from '../interface/Menu'


// 头部导航菜单
const headMenu: Menu = {
  activeIndex: '0', // 默认页面 index
  class: "el-menu-demo",
  text: "導航",
  mode: "horizontal",
  data: [
    { index: "0", icon: "AddLocation", text: "首页" },

  ]
};
// 替换的菜单
let aside = ref<Aside>({});
// 默认展开menu
const openedsIndex: Array<String> = ["0-1", "1-1", "2-1", "3-1", "4-1", "5-1", "6-1", "7-1"];
// 菜单集
const asides: Array<Aside> = [
  {
    activeIndex: '0-1-1',
    submenu: [
      {
        index: "0-1",
        text: "采集",
        icon: "AddLocation",
        data: [
          { index: "0-1-1", text: "任务", icon: "CoffeeCup", link: "job" },
          { index: "0-1-2", text: "关系图", icon: "CoffeeCup", link: "repl" },
        ]
      },
    ]
  },

];
// 面包屑
let currentList = ref<Array<Breadcrumb>>([]);


const router = useRouter();

onMounted(() => {
  // TODO 根据不同权限 headMenu，asides 应该设置不同的数据，
  init();
});

// 初始化展示信息
function init(): void {
  let index = headMenu.activeIndex;
  if (typeof index == "string") {
    handleSelect(index);
  }

};
// head 头部点击事件 切换左侧导航信息 ，更换路由
function handleSelect(index: String): void {
  // 切换aside
  aside.value = asides[Number(index)];
  var path = '/home' + index;
  currentList.value = [];
  currentList.value[0] = { path: path, text: headMenu.data[Number(index)].text };
  // 更换默认页面
  router.push(path);

  // let activeIndex = aside.value.activeIndex;
  // aside.value.submenu?.forEach(men => {
  //   if (activeIndex?.indexOf(men.index) > 0) {

  //   }

  // })

};
// aside 左侧点击事件切换面包屑信息
function handleBreadcrumb(submenu: Menu, data: MenuItem): void {
  currentList.value[1] = { text: submenu.text };
  currentList.value[2] = { text: data.text };
  var path = data.link;
  if (typeof path == 'string') {
    // 更换默认页面
    router.push(path);
  }
}



</script>

<style>
html,
body {
  margin: 0px;
  padding: 0px;
  min-height: 500px;
}

.el-header {
  padding: 0px;
}

.memu-card {
  padding: 0px;

  .el-card__body {
    padding: 0px;
  }
}

.left {
  min-height: 800px;
}

.footer {
  height: 35px;
  background-color: rgba(247, 245, 230, 0.733);
  text-align: center;
  padding: 8px 0px 5px 0px;
}
</style>
