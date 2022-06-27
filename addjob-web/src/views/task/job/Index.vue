<template>
    <div>
        <Table :columns="columns" :rootUrl="rootUrl" :optionBtn="opt" @onClickRow="showDa" />
    </div>
    <el-drawer v-model="drawer" :title="company" size="60%">
        <div>
            <Table v-if="drawer" :columns="columnsd" :datas="datas" :optionBtn="opt2" @onClickRow="showDa2" />
            <el-card class="box-card" v-if="showdatayear">
                <template #header>
                    {{ year }}
                </template>
                <div>
                    <el-row>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">资产总额:</el-col>
                            <el-col :span="16">
                                <span v-if="showValue(rowYear.assGro) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.assGro) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">所有者权益合计:</el-col>
                            <el-col :span="16">
                                <span v-if="showValue(rowYear.totEqu) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.totEqu) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">营业总收入:</el-col>
                            <el-col :span="16">
                                <span v-if="showValue(rowYear.vendInc) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.vendInc) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">利润总额:</el-col>
                            <el-col :span="16">
                                <span v-if="showValue(rowYear.proGro) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.proGro) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="16" class="alr">营业总收入中主营业务收入:</el-col>
                            <el-col :span="8">
                                <span v-if="showValue(rowYear.maiBusInc) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.maiBusInc) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">净利润:</el-col>
                            <el-col :span="16"> <span v-if="showValue(rowYear.netInc) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.netInc) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">纳税总额:</el-col>
                            <el-col :span="16"><span v-if="showValue(rowYear.ratgro) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.ratgro) }}</span>
                            </el-col>
                        </el-col>
                        <el-col :span="12">
                            <el-col :span="8" class="alr">负债总额:</el-col>
                            <el-col :span="16"><span v-if="showValue(rowYear.liaGro) == '1'">
                                    <el-icon color="green">
                                        <CircleCheckFilled />
                                    </el-icon>
                                </span>
                                <span v-else> {{ showValue(rowYear.liaGro) }}</span>
                            </el-col>
                        </el-col>
                    </el-row>
                </div>
            </el-card>
        </div>
    </el-drawer>
</template>

<script setup lang="ts">
import Table from '../../../components/table/Table.vue'
import { Column, OptionBtn, SearchType } from '../../../interface/Table'
import { ref } from 'vue'
import http from '../../../plugins/http';


let rootUrl: String = '/job',
    columns: Array<Column> = [{
        name: "id",
        title: "id",
        align: 'left',
        show: false,
        disable: true,
    }, {
        name: "regno",
        title: "统一代码",
        align: 'left',
        search: true
    }, {
        name: "companyName",
        title: "企业名",
        align: 'left',
        search: true
    }, {
        name: "anCheYear",
        title: "年份",
        align: 'left',
        search: true
    }, {
        name: "flag",
        title: "状态",
        align: 'centor',
        search: true,
        width: 80,
        type: SearchType.OPERATION
    }, {
        name: "insertTime",
        title: "插入时间",
        align: 'left',
        disable: true,
    }]
    ,
    opt: OptionBtn = {
        search: true,
        sort: false,
        searchParam: false,
        page: true,
        add: true,
        opt: true,
        optbtn: {
            state: false,
            del: true,
            upd: true,
        }
    },
    columnsd: Array<Column> = [{
        name: "id",
        title: "id",
        align: 'left',
        show: false,
        disable: true,
    }, {
        name: "anCheYear",
        title: "统一代码",
        align: 'left',
        show: true,
    }, {
        name: "anCheId",
        title: "企业名",
        align: 'left',
        show: false,
    }, {
        name: "unique",
        title: "年份",
        align: 'left',
        show: false,
    }, {
        name: "flag",
        title: "状态",
        align: 'centor',
        search: true,
        width: 80,
        type: SearchType.OPERATION
    }],
    opt2: OptionBtn = {
        search: false,
        sort: false,
        searchParam: false,
        add: false,
        opt: false,
        optbtn: {
            state: false,
            del: false,
            upd: false,
        }
    }
    ;
let innerDrawer = ref<boolean>(false);
let drawer = ref<boolean>(false);
let company = ref<string>("");

let datas = ref<any[]>();
let year = ref<string>();
let showdatayear = ref<boolean>(false);
let rowYear = ref<any>();

function showDa(el: any) {
    console.log(el)
    company.value = el.row['companyName'];

    http.post<any>('/search/annualList', el.row).then((reponse) => {
        if (reponse.data != null && reponse.code == 200) {
            datas.value = reponse.data;
            drawer.value = true;
        }
    }).catch((err) => {
        // TODO
    });
}

function showDa2(el: any) {
    showdatayear.value = false;
    year.value = el.row['anCheYear'] + '年';
    http.post<any>('/search/reportList', el.row).then((reponse) => {
        if (reponse.data != null && reponse.code == 200) {
            showdatayear.value = true;
            rowYear.value = reponse.data
        }
    }).catch((err) => {
        // TODO
    });

}

function showValue(vv: number) {
    if (vv == null) {
        return "-";
    } else if (vv == 0) {
        return "0";
    } else {
        return "1";
    }
}

</script>

<style scoped>
.alr {
    text-align: right;
    font-weight: 600;
}
</style>