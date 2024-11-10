import { createStore } from 'vuex'
import User from "@/store/user";
import Pk from './pk'
export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: User,
    pk: Pk,
  }
})
