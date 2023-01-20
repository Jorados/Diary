<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {hColgroup} from "element-plus/es/components/table/src/h-helper";
import props = hColgroup.props;

const props = defineProps({
  postId:{
    type: [Number,String],
    require: true,
  }
});
const comment = ref({
  id:0,
  content:"",
})

const comments = ref([]);

//댓글 조회
//글 조회 --> for in문으로 불러와야함 --> Home참고
  axios.get(`/api/post/${props.postId}/comment`).then((response) => {
    response.data.forEach((r:any) =>{
      comments.value.push(r)
    })
  });
</script>


<template>
<h2 class="my-3">댓글 조회</h2>
  <ul>

      <div class="my-4" v-for="comment in comments" :key="comment.id">
        <div class="content">
          <li>{{comment.content}}</li>
        </div>
      </div>

  </ul>
</template>

<style scoped>

</style>
