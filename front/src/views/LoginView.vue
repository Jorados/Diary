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
      .then((res)=>{
        if (!username || !password) {
          return false;
        }
        if(res.status==200){
          alert('로그인 성공');
          //뭐지이게
          // document.cookie = `authorization=${res.headers.authorization}`;
          // axios.defaults.headers.common['x-access-token'] = res.headers.authorization;
          //뭐지이게
          console.log(res);
          router.replace({name:"success"});
        }
      })
}

const register = function (){
    this.$store.dispatch('register',{
      username: username.value,
      password: password.value
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
  </el-form>

  <el-row class="my-1">
    <el-button type="primary" @click="login()">로그인</el-button>
    <el-button type="primary" @click="register()">로그인2</el-button>
  </el-row>

</template>
