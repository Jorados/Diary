<script setup lang="ts">
import axios from "axios";
import router from "@/router/index.js";
import {ref} from "vue";
import {state} from "vue-tsc/out/shared";


const userId =ref("")
const userPw = ref("")
const userName = ref("")

const join = function(){
  axios
      .post("/api/user/join",{
        userId: userId.value,
        userPw: userPw.value,
        userName: userName.value,
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
      <el-input type="text" id="userId" placeholder="아이디를 입력하세요." v-model="userId" />
    </div>
    <div class="my-1">
      <label for="password">password</label>
      <el-input type="password" id="userPw" placeholder="비밀번호를 입력하세요." v-model="userPw" />
    </div>
    <div class="my-1">
      <label for="nickname">username</label>
      <el-input type="text" id="userName" placeholder="닉네임을 입력하세요." v-model="userName" />
    </div>
  </el-form>

  <el-row class="my-1">
    <el-button type="primary" @click="join()">회원가입</el-button>
  </el-row>

</template>
