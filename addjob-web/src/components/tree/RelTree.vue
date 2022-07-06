<template>
  <div id="reltree">
    <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick" />
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref, watch } from 'vue';

const defaultProps = {
  children: 'children',
  label: 'label',
}

interface Node {
  name: string,
  companyId?: string,
  creditCode?: string,
  value?: number,
  pers?: number,
  level?: number,
  children?: Node[]
}

const parents = withDefaults(defineProps<{
  datas?: Node
}>(), {
  datas: () => { return { name: "" } }

});



interface Tree {
  label: string,
  children?: Tree[]
}

let data = ref<Tree[]>([]);

onBeforeMount(() => {

  data.value.push(initData(parents.datas));
  console.log(data.value);
});

function initData(data: Node): Tree {
  console.log(data, data.name, data.children)
  let arr: Array<Tree>;
  if (data.children != null && data.children.length > 0) {
    arr = [];
    data.children.forEach(element => {
      arr.push(initData(element));
    });
    let t: Tree = {
      label: (data.name) + (data.creditCode == null ? "" : ("[" + data.creditCode + "]")) + (data.value == null ? "" : ":" + data.value) + (data.pers == null ? "" : "-" + data.pers * 100 + "%"),
      children: arr
    }
    return t;
  } else {
    let t: Tree = {
      label: (data.name) + (data.creditCode == null ? "" : ("[" + data.creditCode + "]")) + (data.value == null ? "" : ":" + data.value) + (data.pers == null ? "" : "-" + data.pers * 100 + "%"),
    }
    return t;
  }
}


const handleNodeClick = (data: Tree) => {
  console.log(data)
}


// 监听传入的数据改变表数据改变
// 字段數據改變
watch(
  () => parents.datas,
  (newValue, oldValue) => {
    data.value = [];
    data.value.push(initData(newValue));
  }
);
</script>

<style scoped>
.reltree {
  font-size: 22px;
}
</style>