<script>
import ContentAreaVue from "@/components/ContentAreaVue.vue";
import {ref} from "vue";
import {useStore} from "vuex";
import router from "@/router";
export default {
  name: "UserAccountLoginView",
  setup(){
    const store=useStore()
    const username=ref('');
    const password=ref('');
    const error_message=ref('');
    const token = localStorage.getItem('token');
    if(token){
      store.commit('updateToken',token);
      store.dispatch('getInfo',{
        success(){
          router.push({name: 'home'});
          store.commit('updateAccessToInformation',false);
        },
        error(resp){
          if(resp.status!==401) {
            alert("服务器内部错误");
          }
          store.commit("updateAccessToInformation",false);
        }
      });
    }else {
      store.commit('updateAccessToInformation',false);
    }
    const login =() =>{
      error_message.value='';
      store.dispatch('login',{
        username: username.value,
        password: password.value,
        success(resp){
          localStorage.setItem("token", resp.data.data.token);
          store.dispatch("getInfo",{
            success(){
              router.push({name: 'home'});
            },
            error(resp){
              if (resp.status=== 401) {
                router.push({name: 'user-account-login'})
              }else {
                alert("服务器内部错误")
              }
            }
          })
          router.push({name: 'home'});
        },
        error(resp){
          error_message.value=resp.errorMessage;
        }
      })
    }
    return{
      error_message,
      username,
      password,
      login
    };
  },
  components:{
    ContentAreaVue,
  }
}
</script>

<template>
  <ContentAreaVue>
    <div class="row justify-content-md-center">
      <div class="col-md-3">
        <form @submit.prevent="login">
          <div class="mb-3">
            <label for="username" class="form-label">用户名:</label>
            <input type="text" v-model="username" class="form-control" id="username" placeholder="请输入用户名">
          </div>
          <div class="mb-3">
            <label for="password" class="form-label">密码:</label>
            <input type="password" v-model="password" class="form-control" id="password" placeholder="请输入密码">
          </div>
          <div class="error_message">{{error_message}}</div>
          <button type="submit" class="btn btn-primary">登录</button>
        </form>
      </div>
    </div>
  </ContentAreaVue>
</template>

<style scoped>
button {
  width: 100%;
}
.error_message{
  color: red;
}
</style>