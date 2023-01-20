import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WriteView from '../views/WriteView.vue'
import ReadView from '../views/ReadView.vue'
import EditView from "../views/EditView.vue";
import LoginView from "@/views/LoginView.vue";
import JoinView from "@/views/JoinView.vue";
import SuccessView from "@/views/SuccessView.vue";
import CommentView from "@/views/CommentView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/write',
      name: 'write',
      component: WriteView
    },
    {
      path:"/read/:postId",
      name:"read",
      component: ReadView,
      //해당 변수는 ReadComponents의 props에서 받을수있게 하겠다
      props: true,
    },
    {
      path: "/edit/:postId",
      name: "edit",
      component: EditView,
      props: true,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView,
      props: true,
    },
    {
      path: "/join",
      name: "join",
      component: JoinView,
      props: true,
    },
    {
      path: "/success",
      name: "success",
      component: SuccessView,
      props: true,
    },
    {
      path: "/comment/:postId",
      name: "comment",
      component: CommentView,
      props: true,
    }
  ]
})

export default router
