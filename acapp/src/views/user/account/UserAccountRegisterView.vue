<script>
import ContentAreaVue from '@/components/ContentAreaVue.vue'
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

export default {
  name: "UserAccountRegisterView",
  setup(){
    const username = ref('');
    const password = ref('');
    const confirmedPassword = ref('');
    const error_message = ref('');
    const router = useRouter();

    const register= () => {
      axios.post('/user/account/register',{
        username: username.value,
        password: password.value,
        confirmedPassword: confirmedPassword.value,
      }).then((resp) => {
        const result=resp.data;
        if(result.code === 1){
          alert("注册成功");
          router.push({name: 'user-account-login'});
        }else {
          alert("注册失败,请从新注册");
        }
      }).catch((error)=>{
        alert("服务器内部错误:"+ error.status);
      })
    }

    return {
      username,
      password,
      confirmedPassword,
      error_message,
      register
    }
  },
  components:{
    ContentAreaVue
  }
}
</script>

<template>
  <content-area-vue>
    <div class="row justify-content-md-center">
      <div class="col-md-3">
        <form @submit.prevent="register">
          <div class="mb-3">
            <label for="username" class="form-label">用户名:</label>
            <input type="text" v-model="username" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密码:</label>
            <input type="password" v-model="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="mb-3">
            <label for="confirmedPassword" class="form-label">确认密码:</label>
            <input type="password" v-model="confirmedPassword" class="form-control" id="confirmedPassword" placeholder="请再次输入密码">
          </div>
          <div class="error_message">{{error_message}}</div>
          <button type="submit" class="btn btn-primary">登录</button>
        </form>
      </div>
    </div>
  </content-area-vue>
</template>

<style scoped>
.error_message {
  color: red;
}
button{
  width: 100%;
}
</style>