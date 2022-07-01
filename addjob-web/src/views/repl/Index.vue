<template>
  <el-row>
    <el-col :span="12">
      <!-- <Rel></Rel> -->
    </el-col>
    <el-col :span="12">
      <!-- <Load></Load> -->
    </el-col>
  </el-row>
  <el-input v-model="param.companyName" placeholder="Please input" />

  <el-button type="primary" @click="init()">确定</el-button>

  <div>
    <h1>关系</h1>
    <p v-for=" (ite, ind) in datas.data" :key="ind">{{ ite[0] + '->' + ite[1] }}</p>
    <h1>节点</h1>
    <p v-for=" (ite, ind) in datas.nodes" :key="ind">{{ ite.id + ":" + ite.subscribedAmount }}</p>
  </div>

  <div id="container"></div>
</template>

<script setup lang="ts">
import Rel from './Rel.vue'
import Load from './Load.vue'
import { ref } from 'vue'
import http from '../../plugins/http'
import { ChartView } from 'echarts';

let param = ref<any>({
  companyName: "北京磐石如金投资管理有限公司",
  level: 5
});

let datas = ref<any>([]);

function init() {
  http.post<any>('/repl/chart', param.value).then((response) => {
    if (response.data != null && response.code == 200) {
      datas.value = response.data;
      chart(response.data);
    }
  }).catch((err) => {
    // TODO
  });

}



function chart(dat: any) {
  // JS 代码 
  Highcharts.chart('container', {
    chart: {
      height: 2200,
      inverted: true
    },
    title: {
      text: '惠多利农资有限公司 组织结构'
    },
    series: [{
      type: 'organization',
      name: '惠多利农资有限公司',
      keys: ['from', 'to'],
      data: dat.data,
      levels: [{
        level: 0,
        color: 'silver',
        height: 25
      }, {
        level: 1,
        color: 'silver',
        height: 25
      }, {
        level: 2,
        color: 'silver'
      }, {
        level: 3,
        color: 'silver'
      }, {
        level: 4,
        color: 'silver'
      }, {
        level: 5,
        color: 'silver'
      }],
      nodes: dat.nodes,
      colorByPoint: false,
      color: '#007ad0',
      dataLabels: {
        color: 'white'
      },
      borderColor: 'white',
      nodeWidth: 300
    }],
    tooltip: {
      outside: true
    },
    exporting: {
      allowHTML: true,
      sourceWidth: 1200,
      sourceHeight: 1200
    }
  });
}


</script>

<style scoped>
</style>

