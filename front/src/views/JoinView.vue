<script setup lang="ts">
import axios from "axios";
import router from "@/router";
import {ref} from "vue";
import {state} from "vue-tsc/out/shared";


const username =ref("")
const password = ref("")
const nickname = ref("")

const join = function(){
  axios
      .post("/api/join",{
        username: username.value,
        password: password.value,
        nickname: nickname.value,
      })
      .then(()=>{
        router.replace({name:"success"});
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
      <label for="username">id</label>
      <el-input type="text" id="username" v-model="username" />
    </div>
    <div class="my-1">
      <label for="password">password</label>
      <el-input type="password" id="password" v-model="password" />
    </div>
    <div class="my-1">
      <label for="nickname">nickname</label>
      <el-input type="text" id="nickname" v-model="nickname" />
    </div>
  </el-form>

  <el-row class="my-1">
    <el-button type="primary" @click="join()">회원가입</el-button>
  </el-row>

</template>
