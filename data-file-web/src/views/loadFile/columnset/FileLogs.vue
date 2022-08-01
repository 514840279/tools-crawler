<template>
  <div>
    fileLogs
  </div>
</template>

<script setup lang="ts">

const emit = defineEmits(["showPage"]);


function showPage() {
  emit("showPage", '1');
}

</script>

<style scoped>
</style>