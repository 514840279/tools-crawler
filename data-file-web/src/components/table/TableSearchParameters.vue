<template>
    <div id="TableSearchParameters">
        <el-row v-for="(item, index) in parents.searchParameters" :key="index" class="prow">
            <el-col :span="1">
                <el-button v-if="index > 0" @click="handleSearchOperator(item, index)" size="small">{{ item.operator }}</el-button>
            </el-col>
            <el-col :span="4">
                <TableSearchColumnSelect :columns="columns" :item="item" :index="index" v-model:parameters="parents.searchParameters"></TableSearchColumnSelect>
            </el-col>
            <el-col :span="3">
                <TableSearchSymbolSelect :item="item" :index="index" v-model:parameters="parents.searchParameters"></TableSearchSymbolSelect>
            </el-col>
            <el-col :span="6" v-if="item.showdata">
                <el-input size="small" v-model="item.data" :placeholder="item.searchPlaceholder" />
            </el-col>
            <el-col :span="1">
                <el-button @click="handleDelSearch(item, index)" icon="Remove" circle size="small" title="删除"></el-button>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-button @click="handleAddSearch" type="success" icon="Plus" circle size="small" title="添加"></el-button>
                <el-button @click="handleSearch" type="success" icon="Check" circle size="small" title="确定"></el-button>
            </el-col>
        </el-row>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { SearchColumn, SearchParamters } from '../../interface/Table'

import TableSearchColumnSelect from './TableSearchColumnSelect.vue'
import TableSearchSymbolSelect from './TableSearchSymbolSelect.vue'


const parents = withDefaults(defineProps<{
    searchColumns?: Array<SearchColumn>,
    searchParameters?: Array<SearchParamters>
}>(), {
    searchColumns: () => [],
    searchParameters: () => []
});

let
    columns = ref<Array<SearchColumn>>([]),
    // parameters = ref<Array<SearchParamters>>([]),
    plusNum = ref<number>(0),
    columnsSize = ref<number>(0);

const emit = defineEmits(["searchTable"]);


onMounted(() => {
    columns.value = parents.searchColumns;
    // parameters.value = parents.searchParameters;

});

// 添加新的筛选条件
function handleAddSearch() {
    // 防阻size为0
    if (columnsSize.value == 0) {
        columnsSize.value = columns.value.length;
    }
    // 保证循环添加默认值数据
    if (columnsSize.value == plusNum.value) {
        plusNum.value = 0;
    }
    let column = columns.value[plusNum.value];
    parents.searchParameters.push({
        operator: 'and',
        column: column.searchName,
        title: column.searchTitle,
        symbol: "eq",
        data: "",
        searchPlaceholder: column.searchPlaceholder,
        showdata: true,
    });

    plusNum.value++;

}
// 查询筛选
function handleSearch() {
    emit("searchTable");
}
// 修改连接符
function handleSearchOperator(item: SearchParamters, index: number) {
    if (item.operator == 'and') {
        item.operator = 'or';
    } else {
        item.operator = 'and';
    }
    parents.searchParameters.splice(index, 1, item);
}
// 删除条件
function handleDelSearch(item: SearchParamters, index: number) {
    parents.searchParameters.splice(index, 1);
}

</script>
<style scoped>
#TableSearchParameters {
    margin: 0px;
    padding: 8px;
    background: rgb(248, 248, 248);
    min-height: 150px;
    max-height: 320px;
}
</style>