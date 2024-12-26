import { createStore } from 'vuex'
import User from "@/store/user";
import Pk from './pk'
import RecordModel from './record';
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
    record: RecordModel
  }
})
