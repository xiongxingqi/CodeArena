<template>
  <NavBarVue/>
  <router-view/>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.js';
import NavBarVue from './components/NavBarVue.vue';
// import axios from "axios";
export default {
  name: "App",
  components: {
    NavBarVue,
  },
  setup(){
    // axios.post('http://localhost:3000/user/account/token',{
    //       username: 'celestrong',
    //       password: '123456'
    //     })
    //     .then((data) => {
    //       console.log('success:'+ data.data.data.token);
    //     })
    //     .catch(error =>{
    //       console.log('error:'+error);
    //     })
    // axios.get('/user/account/info',{
    //   headers:{
    //     Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEsImV4cGlyZV90aW1lIjoxNzI5NjkyMjk4ODgxfQ.c1COcBtlB1NWQumOBbb_Dlvo-lX13jgDI4hIliQO6JI'
    //   }
    // }).then(resp =>{
    //   console.log('success:'+JSON.stringify(resp.data))
    // }).catch(error =>{
    //   console.log('error:'+JSON.stringify(error))
    // });
    // axios.post('/user/account/register',{
    //   username: 'xiongxingqi',
    //   password: '123456',
    //   confirmedPassword: '123456'
    // }).then(resp =>{
    //   console.log('success:'+JSON.stringify(resp.data))
    // }).catch(error => {
    //   console.log('error:'+ JSON.stringify(error));
    // })
  },
}
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
