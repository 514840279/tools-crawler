<template>
    <el-row>
        <el-col :span="searchSize / 20" v-for="(item, index) in columns" :key="index" class="ainput">
            <el-input size="small" v-model="parents.searchParameters[index].data" :placeholder="item.searchPlaceholder" @input="handleSearch()" />
        </el-col>
        <el-col :span="2">
            <el-button title="确定" @click="handleSearch()" type="success" icon="Check" circle size="small"></el-button>
            <el-button title="重置" @click="handleReset()" icon="CloseBold" circle size="small"></el-button>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { SearchColumn, SearchParamters } from '../../interface/Table'


const parents = withDefaults(defineProps<{
    searchColumns?: Array<SearchColumn>,
    searchParameters?: Array<SearchParamters>
}>(), {
    searchColumns: () => [],
    searchParameters: () => []
});

let
    columns = ref<Array<SearchColumn>>([]),
    searchSize = ref<number>(0);

const emit = defineEmits(["searchTable"]);


onMounted(() => {
    columns.value = parents.searchColumns;

    init();
});

function init() {
    parents.searchColumns.forEach((column, index) => {
        if (index < 5) {
            parents.searchParameters.push({
                operator: 'and',
                column: column.searchName,
                title: column.searchTitle,
                symbol: "like",
                data: "",
                searchPlaceholder: column.searchPlaceholder,
                showdata: true,
            })
        }
    });
    searchSize.value = parents.searchParameters.length;
}
// 查询筛选
function handleSearch() {
    emit("searchTable");
}
function handleReset() {
    parents.searchParameters.forEach(item => {
        item.data = "";
    });
    handleSearch();
}
</script>

<style lang="scss" scoped>
.ainput {
    padding: 0px 5px;
}
</style>