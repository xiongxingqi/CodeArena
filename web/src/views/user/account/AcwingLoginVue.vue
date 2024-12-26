<script>
import {useRoute, useRouter} from "vue-router";
import axios from "axios";

export default {
  name: "AcwingLoginVue",
  setup(){
    const route = useRoute();
    const router = useRouter();
    const code = route.query.code;
    const state = route.query.state;

    const redirect = () =>{
      axios.get("/acwing/web/login/redirect",{
        params:{
          code,
          state
        }
      }).then((rep) =>{
        const res = rep.data;
        if(res.code === 1){
          localStorage.setItem("token",res.data.token);
        }else {
          alert(res.message);

        }
        router.push({
          name: "user-account-login",
        })
      }).catch((error) =>{
        alert("服务异常:" + error.status);
        router.push({
          name: "user-account-login"
        });
      });
    }
    redirect();
    return {
    }
  }
}
</script>

<template>
  <div>
    登陆中
  </div>
</template>

<style scoped>

</style>