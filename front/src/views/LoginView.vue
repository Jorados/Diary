<script setup lang="ts">
import axios from "axios";
import router from "@/router";
import {ref} from "vue";


const username =ref("")
const password = ref("")

const login = function(){
  axios
      .post("/api/login",{
        username: username.value,
        password: password.value
      })
      .then(()=>{
        router.replace({name:"home"});
      })
}
</script>

<template>
  <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="formLabelAlign"
      style="max-width: 460px"
  >
    <div class="my-1">
      <label for="username">username</label>
      <el-input type="text" id="username" v-model="username" />
    </div>
    <div class="my-1">
      <label for="password">password</label>
      <el-input type="text" id="password" v-model="password" />
    </div>
  </el-form>

  <el-row class="my-1">
    <el-button @click="login()">로그인</el-button>
  </el-row>

</template>
