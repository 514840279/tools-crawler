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
    <h1>企业》企业股东 关系</h1>
    <!-- <p v-for=" (ite, ind) in datas.data1" :key="ind">{{ ite.personName + (ite.subscribedAmount == null ? "" : ":" + ite.subscribedAmount) + (ite.pers == null ? "" : ("(" + (ite.pers * 100) + "%)")) + '->' + ite.companyName }}</p> -->
    <RelTree :datas="datas.data1"></RelTree>
  </div>

  <div id="container" style="height: 800px;width: 100%; border: 1px solid;"></div>

  <div>
    <h1>企业》投资企业 关系</h1>
    <!-- <p v-for=" (ite, ind) in datas.rel2" :key="ind">{{ ite.personName + (ite.subscribedAmount == null ? "" : ":" + ite.subscribedAmount) + (ite.pers == null ? "" : ("(" + (ite.pers * 100) + "%)")) + '->' + ite.companyName }}</p>
    <h1>节点</h1>
    <p v-for=" (ite, ind) in datas.nodes2" :key="ind">{{ ite.id + (ite.creditCode == null ? "" : ("(" + ite.creditCode + ")")) }}</p> -->
    <RelTree :datas="datas.data2"></RelTree>
  </div>
  <div id="container2" style="height:800px ;width:100%; border: 1px solid;"></div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import http from '../../plugins/http'
import RelTree from '../../components/tree/RelTree.vue'



import * as echarts from 'echarts';

type EChartsOption = echarts.EChartsOption;
var option: EChartsOption;

var myChart1: any = null;
var myChart2: any = null;


onMounted(() => {
  var chartDom = document.getElementById('container')!;
  myChart1 = echarts.init(chartDom);

  var chartDom2 = document.getElementById('container2')!;
  myChart2 = echarts.init(chartDom2);
})

let param = ref<any>({
  companyName: "北京磐石如金投资管理有限公司",
  level: 5
});

let datas = ref<any>([]);

function init() {
  http.post<any>('/repl/echart1', param.value).then((response) => {
    if (response.data != null && response.code == 200) {

      datas.value = response.data;
      console.log(datas.value)
      chart1(response.data);
      chart2(response.data);
    }
  }).catch((err) => {
    // TODO
  });

}
// 投资
function chart2(dat: any) {
  myChart2.hideLoading();

  myChart2.setOption(
    (option = {
      tooltip: {
        trigger: 'item',
        triggerOn: 'mousemove'
      },
      series: [
        {
          type: 'tree',

          data: [dat.data2],

          left: '2%',
          right: '2%',
          top: '8%',
          bottom: '20%',

          symbol: 'emptyCircle',

          orient: 'vertical',

          expandAndCollapse: true,

          label: {
            position: 'top',
            rotate: -90,
            verticalAlign: 'middle',
            align: 'right',
            fontSize: 9
          },

          leaves: {
            label: {
              position: 'bottom',
              rotate: -90,
              verticalAlign: 'middle',
              align: 'left'
            }
          },

          animationDurationUpdate: 750
        }
      ]
    })
  );
}

// 股东
function chart1(dat: any) {
  myChart1.hideLoading();
  myChart1.setOption(
    (option = {
      tooltip: {
        trigger: 'item',
        triggerOn: 'mousemove'
      },
      series: [
        {
          type: 'tree',
          data: [dat.data1],
          left: '2%',
          right: '2%',
          top: '20%',
          bottom: '8%',
          symbol: 'emptyCircle',
          orient: 'BT',
          expandAndCollapse: true,
          label: {
            position: 'bottom',
            rotate: 90,
            verticalAlign: 'middle',
            align: 'right'
          },
          leaves: {
            label: {
              position: 'top',
              rotate: 90,
              verticalAlign: 'middle',
              align: 'left'
            }
          },
          emphasis: {
            focus: 'descendant'
          },
          animationDurationUpdate: 750
        }
      ]
    })
  );
}


</script>

<style scoped>
</style>

