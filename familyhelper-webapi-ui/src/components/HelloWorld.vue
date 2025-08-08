<template>
  <div class="hello">
    {{ msg }}
  </div>
</template>

<script setup lang="ts">
defineOptions({
  name: 'HelloWorld',
})

// -----------------------------------------------------------Props 定义-----------------------------------------------------------
type Props = {
  msg?: string
}

withDefaults(defineProps<Props>(), {
  msg: '欢迎文本',
})
</script>

<style scoped>
.hello {
  width: 100%;
  height: 100%;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  font-weight: bold;
  color: #bfbfbf;
  user-select: none;
}
</style>
