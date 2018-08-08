<template>
  <b-container fluid>
    <b-row>
      <b-col>
        <navbar></navbar>
      </b-col>
    </b-row>
    <br/>
    <b-row>
      <b-col>
        <user-home></user-home>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
  import navbar from './navbar'
  import adminHome from './user-home'
    export default {
        name: "page-user-home",
      components: {
        'navbar': navbar,
        'user-home': adminHome,
      },
    }
</script>

<style scoped>

</style>
