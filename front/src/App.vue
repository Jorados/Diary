<script setup="ts">
import { RouterLink, RouterView } from 'vue-router'
import Header from "@/components/Header.vue"
import {createApp, ref} from "vue";
// import store from './vuex/store.js'
import store from './vuex/store'
import App from './App.vue'
import router from './router'



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
app{
  display: grid;
  min-height: 100%;
  position: relative;
  padding-bottom: 170px;
}
</style>
