import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import axios from "axios";

// let server_host = "https://app7275.acapp.acwing.com.cn";
let local_host = "http://127.0.0.1:3000";
axios.defaults.baseURL=`${local_host}/api`;
createApp(App).use(router).use(store).mount('#app')
