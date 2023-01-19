<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from "@/components/Header.vue"
import {ref} from "vue";


</script>
<template>
  <div>
      <Header />
      <RouterView />
  </div>
</template>

<style>
body {
  margin: 0;
}
/*#app {*/
/*  width : 100vw;*/
/*  height : 100vh;*/
/*}*/

app{
  min-height: 100%;
  position: relative;
  padding-bottom: 170px;
}


</style>
