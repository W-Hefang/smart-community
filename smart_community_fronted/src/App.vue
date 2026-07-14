<template>
  <router-view />
</template>

<script setup lang="ts">
// 不需要导入任何组件，router-view 会自动渲染路由对应的组件
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
  width: 100%;
}
</style>