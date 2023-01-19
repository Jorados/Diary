<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {hColgroup} from "element-plus/es/components/table/src/h-helper";
import props = hColgroup.props;
import router from "@/router";

const post =ref({
  id:0,
  title:"",
  content:"",
})

const props = defineProps({
  postId:{
    type: [Number,String],
    require: true,
  }
});

const moveToEdit = () => {
  router.push({name:"edit",params:{postId: props.postId}});
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});

const drop = () => {
  axios.delete(`/api/posts/${props.postId}`).then(() => {
    router.replace({name:"home"});
  });
};

</script>

<template>

  <div>
    <h2 class="title">{{ post.title }}</h2>
  </div>

  <div>
    <div class="content">{{ post.content }}</div>
  </div>

  <el-row class="my-1">
    <el-button type="primary" @click="moveToEdit()">글 수정</el-button>
    <el-button type="danger" @click="drop()">글 삭제</el-button>
  </el-row>


</template>

<style>
.content {
  white-space: break-spaces;
}
</style>
