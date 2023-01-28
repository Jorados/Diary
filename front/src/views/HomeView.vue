<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";

const posts = ref([]);

axios.get("/api/posts?page=1&size=5").then((response) => {
  response.data.forEach((r:any) =>{
    posts.value.push(r)
  })
});


const instance = axios.create({
  baseURL: '',
  headers: {
    Authrozation: 'testToken',
  }
})

</script>

<template>
  <ul>
    <li class="my-4" v-for="post in posts" :key="post.id">

      <div class="title">
        <router-link :to="{name:'read',params: {postId: post.id}}">{{post.title}}</router-link>
      </div>

      <div class="content">
        {{post.content}}
      </div>

    </li>
  </ul>

    <el-pagination
        small
        background
        layout="prev, pager, next"
        :total="50"
        class="mt-4"
    />
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      line-height: 1.4;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }
  }
}

</style>

