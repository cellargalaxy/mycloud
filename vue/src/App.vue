<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  import vue from './components/upload-file'

  export default {
    name: 'App',
    components: {
      'vue': vue
    },
  }
</script>

<style>

</style>
