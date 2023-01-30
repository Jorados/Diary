<template>
  <div>
    <div>
      <div id="loginForm()">
        <el-form :label-position="labelPosition"
                 label-width="100px"
                 :model="formLabelAlign"
                 style="max-width: 460px"
         @submit.prevent="fnLogin">
          <p>
            <label for="username">id</label>
            <el-input class="my-1" name="uid" placeholder="아이디를 입력하세요." v-model="user_id" /><br>
          </p>
          <p>
            <label for="password">password</label>
            <el-input name="password" placeholder="비밀번호를 입력하세요." v-model="user_pw" type="password" />
          </p>
          <p>
            <el-row class="my-1">
              <el-button type="primary" @click="fnLogin">로그인</el-button>
            </el-row>
          </p>
        </el-form>
      </div>
    </div>
  </div>

</template>


<script>
import {mapActions, mapGetters} from 'vuex'   //vuex 추가

export default {
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  methods: {
    ...mapActions(['login']),     //vuex/actions에 있는 login 함수

    async fnLogin() {       //async 함수로 변경
      if (this.user_id === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.user_pw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }

      //로그인 API 호출
      try {
        let loginResult = await this.login({user_id: this.user_id, user_pw: this.user_pw})
        if (loginResult) alert('로그인 결과 : ' + loginResult)
        this.$router.push('/')
      } catch (err) {
        if (err.message.indexOf('Network Error') > -1) {
          alert('서버에 접속할 수 없습니다. 상태를 확인해주세요.')
        } else {
          alert('로그인 정보를 확인할 수 없습니다.')
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>
