<template>
  <NavBarVue/>
  <router-view/>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.js';
import NavBarVue from './components/NavBarVue.vue';
import $ from 'jquery'
export default {
  name: "App",
  components: {
    NavBarVue,
  },
  setup(){
    $.ajax({
      url: "http://localhost:3000/user/account/token",
      type: "post",
      data:JSON.stringify({
        username: "celestrong",
        password: '123456'
      }),
      datatype: 'json',
      contentType: 'application/json;charset=utf8',
      success(resp){

        console.log('success:'+resp.data.token);
      },
      error(resp){
        console.log('error'+resp.code);
      }
    })
  },
}
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
