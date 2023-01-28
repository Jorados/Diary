// src/vuex/store.js
import {createStore} from "vuex"
import getters from "./getter"
import mutations from "./mutation"
import actions from "./actions"

export default createStore({
    state: {
        user: null,
        isLogin: false,
    },
    mutations,
    getters,
    actions,
})
