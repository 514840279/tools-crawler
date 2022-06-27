<template>
    <div id="TableSave">
        <el-form ref="form" :model="row" label-width="120px" :label-position="parents.labelPosition">
            <el-form-item v-for="(item, index) in parents.columns" :key="index" :label="item.title">
                <el-input v-model="row[item.name]" :disabled="item.disable"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSave">保存</el-button>
                <el-button @click="onCancel">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { Column } from '../../interface/Table'

const parents = withDefaults(defineProps<{
    info?: any,
    columns?: Array<Column>,
    labelPosition?: String
}>(), {
    info: () => { },
    columns: () => [],
    labelPosition: () => "top"
});

const emit = defineEmits(["onSave", "update:info", "onCancel"]);

let row = ref<any>({});

onMounted(() => {
    row.value = parents.info;
})


function onSave() {
    emit('update:info', row.value);
    emit('onSave');
}
function onCancel() {
    emit('onCancel');
}



</script>
<style scoped>
#Table {}
</style>