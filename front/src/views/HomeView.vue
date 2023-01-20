<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";

const posts = ref([]);

axios.get("/api/posts?page=1&size=5").then((response) => {
  response.data.forEach((r:any) =>{
    posts.value.push(r)
  })
});

</script>

<template>
  <div>
    <div class="my-4" v-for="post in posts" :key="post.id">
    <ul>

      <div class="title">
        <li><router-link :to="{name:'read',params: {postId: post.id}}">{{post.title}}</router-link></li>
      </div>

      <div class="content">
        {{post.content}}
      </div>
    </ul>
    </div>
  </div>
</template>
