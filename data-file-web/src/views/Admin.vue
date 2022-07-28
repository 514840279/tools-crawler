
<template>
  <el-container>
    <el-header>
      <el-menu :default-active="headMenu.activeIndex" :class="headMenu.class" :mode="headMenu.mode" @select="handleSelect" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item v-for="(menu, index) in headMenu.data" :key="index" :index="menu.index">{{ menu.text }}</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="200px" class="left">
        <!-- aside -->
        <el-menu class="el-menu-vertical-demo " :default-openeds="openedsIndex" :default-active="aside.activeIndex">
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
  activeIndex: '2', // 默认页面 index
  class: "el-menu-demo",
  text: "導航",
  mode: "horizontal",
  data: [
    { index: "0", text: "首页" },
    { index: "1", text: "权限管理" },
    { index: "2", text: "数据库管理" },
    { index: "3", text: "人员查询" },
    { index: "4", text: "企业查询" },
    { index: "5", text: "分析管理" },
    { index: "6", text: "爬虫管理" },
    { index: "7", text: "消息中心" },
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
        text: "首页",
        icon: "Message",
        data: [
          { index: "0-1-1", text: "消息中心", icon: "Message", link: "" },

        ]
      },
    ]
  },
  {
    activeIndex: '1-1-1',
    submenu: [
      {
        index: "1-1",
        text: "权限管理",
        icon: "Message",
        data: [
          { index: "1-1-1", text: "用户管理", link: "" },
          { index: "1-1-2", text: "权限管理", link: "" },
          { index: "1-1-3", text: "项目管理", link: "" },
          { index: "1-1-4", text: "菜单管理", link: "" },
          { index: "1-1-5", text: "消息中心", link: "" },
        ]
      },
    ]
  },
  {
    activeIndex: '2-1-1',
    submenu: [
      {
        index: "2-1",
        text: "数据管理",
        icon: "Message",
        data: [
          { index: "2-1-2", text: "数据类型", link: "/databasetype" },
          { index: "2-1-4", text: "索引类型", link: "/userindex" },
          { index: "2-1-1", text: "数据库", link: "/database" },
          { index: "2-1-3", text: "表管理", link: "/tables" },
          { index: "2-1-5", text: "字段管理", link: "/columns" },
        ]
      },
      {
        index: "2-2",
        text: "数据迁移",
        icon: "Message",
        data: [
          { index: "2-2-1", text: "简单复制", link: "/user" },
          { index: "2-2-2", text: "复杂清洗", link: "/table" },
        ]
      },
      {
        index: "2-3",
        text: "代码管理",
        icon: "Message",
        data: [
          { index: "2-3-1", text: "项目管理", link: "/user" },
          { index: "2-3-2", text: "代码生成配置", link: "/table" },
        ]
      },
    ]
  },
  {
    activeIndex: '3-1-1',
    openedsIndex: [],
    submenu: [
      {
        index: "3-1",
        text: "人员查询",
        icon: "Message",
        data: [
          { index: "3-1-1", text: "查询条件配置", link: "/user" },
          { index: "3-1-2", text: "查询类型配置", link: "/table" },
          { index: "3-1-3", text: "查询", link: "/table" },
          { index: "3-1-4", text: "查询日志", link: "/table" },
          { index: "3-1-5", text: "日志分析", link: "/table" },
        ]
      }
    ]
  },
  {
    activeIndex: '4-1-1',
    submenu: [
      {
        index: "4-1",
        text: "企业查询",
        icon: "Message",
        data: [
          { index: "4-1-1", text: "查询条件配置", link: "/user" },
          { index: "4-1-2", text: "查询类型配置", link: "/table" },
          { index: "4-1-3", text: "查询", link: "/table" },
          { index: "4-1-4", text: "查询日志", link: "/table" },
          { index: "4-1-5", text: "日志分析", link: "/table" },
        ]
      }
    ]
  },
  {
    activeIndex: '5-1-1',
    submenu: [
      {
        index: "5-1",
        text: "分析管理",
        icon: "Message",
        data: [
          { index: "5-1-1", text: "模板管理", link: "" },
          { index: "5-1-2", text: "图形搭配", link: "" },
        ]
      }
    ]
  },
  {
    activeIndex: '6-1-1',
    submenu: [
      {
        index: "6-1",
        text: "爬虫管理",
        icon: "Message",
        data: [
          { index: "6-1-1", text: "机器信息", link: "" },
          { index: "6-1-2", text: "爬虫项目信息", link: "" },
          { index: "6-1-3", text: "爬虫项目管理", link: "" },
        ]
      }
    ]
  },
  {
    activeIndex: '7-1-1',
    submenu: [
      {
        index: "7-1",
        text: "消息中心",
        icon: "Message",
        data: [
          { index: "7-1-1", text: "消息中心", link: "" },
          { index: "7-1-2", text: "消息统计分析", link: "" },
          { index: "7-1-2", text: "网站链接", link: "" },
        ]
      }
    ]
  }
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
