<script>
export default {
  methods: {
    fnLogout() {
      localStorage.removeItem("user_token")
      localStorage.removeItem("user_role")
      location.reload()
    }
  }
}
</script>
<template>

    <el-header clas="header">
      <router-link :to="{name:'home'}" class="title">다이어리</router-link>
    </el-header>

    <el-menu mode="horizontal" router>
      <el-menu-item index="/">Home</el-menu-item>
      <el-menu-item index="/write" v-if="this.$store.state.isLogin">글 작성</el-menu-item>
      <el-menu-item index="/join">회원가입</el-menu-item>
      <el-menu-item index="/login" v-if="!this.$store.state.isLogin">로그인</el-menu-item>
      <el-menu-item>
        <a v-if="this.$store.state.isLogin" @click="fnLogout">로그아웃 </a>
      </el-menu-item>
    </el-menu>
</template>

<style>
.title {
  font-size: 1.5rem;
  color: #383838;
  text-decoration: none;
  padding: 0;
}

hover {
   text-decoration: underline;
 }

</style>
