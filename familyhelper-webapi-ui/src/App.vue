<template>
  <div id="app">
    <hello-world msg="启动成功，奖励你一朵小花（๑╹◡╹)ﾉ”"/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue';

export default {
  name: 'App',
  components: {
    HelloWorld,
  },
};
</script>

<style>
@import "assets/css/global.css";
</style>

<style>
#app {
  height: 100%;
  width: 100%;
}
</style>
