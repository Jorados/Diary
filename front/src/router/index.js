import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WriteView from '../views/WriteView.vue'
import ReadView from '../views/ReadView.vue'
import EditView from "../views/EditView.vue";
//import LoginView from "@/views/LoginView.vue";
import JoinView from "@/views/JoinView.vue";
import SuccessView from "@/views/SuccessView.vue";
import CommentView from "@/views/CommentView.vue";
import Login from "@/views/commons/Login.vue";
import store from "@/vuex/store.js";
import BoardList from "@/views/BoardList.vue";

const requireAuth = () => (from, to, next) => {
  const token = localStorage.getItem('user_token')
  if (token) {
    store.state.isLogin = true
    return next()
  } // isLogin === true면 페이지 이동
  next('/login') // isLogin === false면 다시 로그인 화면으로 이동
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      beforeEnter: requireAuth()
    },
    {
      path: '/write',
      name: 'write',
      component: WriteView,
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
    // {
    //   path: "/login",
    //   name: "login",
    //   component: LoginView,
    //   props: true,
    // },
    {
      path: "/login",
      name: "login",
      component: Login,
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
    },
    {
      path: '/boardList',
      name: 'boardList',
      component: BoardList,
    }
  ]
})
export default router
