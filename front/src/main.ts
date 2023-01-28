import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import './assets/main.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from "./vuex/store.js"
import "bootstrap/dist/css/bootstrap-utilities.css"
const app = createApp(App)



app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.use(router)
app.use(store)   //2. store 등록
app.mount('#app')
