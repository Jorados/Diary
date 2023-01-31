<template>

  <div class="my-3 d-flex">
    <div class="mx-2">
      <el-select v-model="search_key">
        <el-option label="- 선택 -" value="">- 선택 -</el-option>
        <el-option label="제목" value="title">제목</el-option>
        <el-option label="내용" value="content">내용</el-option>
      </el-select>
    </div>

    <div class="d-flex">
      <el-input type="text" v-model="search_value" @keyup.enter="fnPage()"></el-input>
      <el-button class="mx-2" type="primary" @click="fnPage()">검색</el-button>
    </div>
  </div>

    <div class="my-5" v-for="(row, id) in list" :key="id">
      <div>{{ row.id }}
        <a v-on:click="fnView(`${row.id}`)">{{ row.title }}</a>
      </div>
      <div>
      {{ row.content }}
      </div>
    </div>


    <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
<span class="pg">
      <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
      <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page-1}`)"
         class="prev w3-button w3-bar-item w3-border">&lt;</a>
      <template v-for=" (n,index) in paginavigation()">
          <template v-if="paging.page==n">
              <strong class="w3-button w3-bar-item w3-border w3-blue" :key="index">{{ n }}</strong>
          </template>
          <template v-else>
              <a class="w3-button w3-bar-item w3-border" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{ n }}</a>
          </template>
      </template>
      <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page"
         @click="fnPage(`${paging.end_page+1}`)" class="next w3-button w3-bar-item w3-border">&gt;</a>
      <a href="javascript:;" @click="fnPage(`${paging.total_page_cnt}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>
      </span>
    </div>


</template>

<script>

import axios from "axios";

const instance = axios.create({
  baseURL: '',
  headers: {
    Authrozation: 'testToken',
  }
})


export default {
  data() { //변수생성
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      no: '', //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 1,
      size: this.$route.query.size ? this.$route.query.size : 5,
      search_key: this.$route.query.sk ? this.$route.query.sk : '',
      search_value: this.$route.query.sv ? this.$route.query.sv : '',
      paginavigation: function () { //페이징 처리 for문 커스텀
        let pageNumber = [] //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
        sk: this.search_key,
        sv: this.search_value,
        page: this.page,
        size: this.size
      }

      this.$axios.get("/api/board/list", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {

        if (res.data.result_code === "OK") {
          this.list = res.data.data
          this.paging = res.data.pagination
          this.no = this.paging.total_list_cnt - ((this.paging.page - 1) * this.paging.page_size)
        }

      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        this.page = n
      }
      this.fnGetList()
    }
  }
}
</script>

<style>
</style>
