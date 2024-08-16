<template>
  <NavBarVue/>
  <router-view/>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.js';
import NavBarVue from './components/NavBarVue.vue';

export default {
  name: "App",
  components: {
    NavBarVue,
  },
  setup(){
  },
}
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
