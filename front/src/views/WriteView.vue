<script setup lang="ts">
import axios from "axios";
import router from "@/router/index.js";
import {ref} from "vue";

const title =ref("")
const content = ref("")

const write = function(){
  axios
      .post("/api/posts",{
        title: title.value,
        content: content.value
      })
      .then(()=>{
        router.replace({name:"home"});
      })
}

</script>

<template>

  <div class="my-1">
    <el-input v-model="title" type ="text" placeholder="제목을 입력해주세요."/>
  </div>

  <div class="my-1">
    <el-input v-model="content" type="textarea" rows="20" placeholder="내용을 입력해주세요."></el-input >
  </div>

  <el-row class="my-1">
    <el-button type="primary"  @click="write()">글 쓰기</el-button>
  </el-row>


</template>

<style></style>

