<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {hColgroup} from "element-plus/es/components/table/src/h-helper";
import props = hColgroup.props;
import router from "@/router/index.js";

const post =ref({
  id:0,
  title:"",
  content:"",
})

const content = ref("")

const props = defineProps({
  postId:{
    type: [Number,String],
    require: true,
  }
});


//edit페이지로 post.id 넘기기
const moveToEdit = () => {
  router.push({name:"edit",params:{postId: props.postId}});
}
const moveToEdit2 = () => {
  router.push({name:"comment",params:{postId:props.postId}});
}


//글 조회
onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});


//글 삭제
const drop = () => {
  axios.delete(`/api/posts/${props.postId}`).then(() => {
    router.replace({name:"home"});
  });
};

//post가 가지는 댓글 조회 -->  post조회에서 comment를 가지고 와야함.

//댓글 생성
const commentCreate = function(){
  axios.post("/api/comment",{content: content.value, post: post.value}).then(()=>{
        router.replace({name:"home"});
      })
}

</script>

<template>
  <div class="post" style="display: none" >{{post}}</div>

  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ post.content }}</div>
    </el-col>
  </el-row>

  <el-row class="my-1">
    <el-col>
      <el-button type="primary" @click="moveToEdit()">글 수정</el-button>
      <el-button type="danger" @click="drop()">글 삭제</el-button>
      <el-button type="warning" @click="moveToEdit2()">댓글 보기</el-button>
    </el-col>
  </el-row>

  <el-form  label-width="100px" style="max-width: 460px">
    <div class="my-5">
      <h2>댓글 달기</h2>
        <el-input v-model="content" type="text" id="content" placeholder="댓글을 입력해주세요."/>
        <el-row class="my-1">
          <el-col>
            <el-button type="primary" @click="commentCreate()">댓글 등록</el-button>
          </el-col>
        </el-row>
    </div>
  </el-form>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 400;
  color: #383838;
}

.content {
  color: #616161;
  white-space: break-spaces;
}
</style>
