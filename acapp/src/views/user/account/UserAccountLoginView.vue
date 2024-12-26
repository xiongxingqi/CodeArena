<script>
import ContentAreaVue from "@/components/ContentAreaVue.vue";
import {ref} from "vue";
import {useStore} from "vuex";
import router from "@/router";
import store from "@/store";
import axios from "axios";
export default {
  name: "UserAccountLoginView",
  computed: {
    store() {
      return store
    }
  },
  setup(){
    const store=useStore()
    const username=ref('');
    const password=ref('');
    const error_message=ref('');
    const token = localStorage.getItem('token');


    console.log(token);

    if(token){
      store.commit('updateToken',token);
      store.dispatch('getInfo',{
        success(res){
          store.commit("updateUser",{
            ...(res.data),
            is_login: true
          });
          router.push({name: 'home'});
        },
        error(resp){
          if (resp.status === 401) {
            localStorage.removeItem("token");
            console.log("身份失效");
            location.reload();
          }else {
            alert("服务器内部错误,响应码:" + resp.status);
          }
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
        success(){
            store.dispatch("getInfo",{
              success(res){
                store.commit("updateUser",{
                  ...(res.data),
                  is_login: true
                });
                router.push({name: 'home'});
              },
              error(resp){
                if (resp.status === 401) {
                  alert("身份失效请重新登录!")
                  localStorage.removeItem("token");
                  location.reload();
                }else {
                  alert("服务器内部错误,响应码:" + resp.status);
                }
              }
            })
        },
        error(res){
          error_message.value=res.message;
        }
      })
    }
    const acwing_login = () =>{
      console.log("开始acwing登录");
      axios.get("/acwing/web/login/applyCode")
          .then((rep) =>{
            const res = rep.data;
            if(res.code === 1) {
              location.assign(res.data);
            }else {
              alert(res.message);
              location.reload();
            }
          }).catch(error =>{
            alert("服务异常:"+ error.status);
            location.reload();
      });
    }
    return{
      error_message,
      username,
      password,
      login,
      acwing_login,
    };
  },
  components:{
    ContentAreaVue,
  }
}
</script>

<template>
  <ContentAreaVue v-if="!$store.state.user.accessToInformation">
    <div class="row justify-content-md-center" >
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
        <div class="acwingLogin" @click="acwing_login">
          <img src="https://cdn.acwing.com/media/article/image/2022/09/06/1_32f001fd2d-acwing_logo.png" alt="">
          <br/>
          acwing一键登录
        </div>
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
div.acwingLogin{
  margin-top: 3vh;
  text-align: center;
  user-select: none;
  cursor: pointer;
}
div.acwingLogin > img{
  height: 30px;
  width: 30px;
}
</style>