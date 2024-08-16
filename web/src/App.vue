<template>
  <div>bot-name: {{bot_name}} </div>
  <div>bot-rating: {{bot_rating}} </div>
  <router-view/>
</template>

<script>
import { ref } from 'vue';
import $ from 'jquery'
export default {
  name: "App",
  setup(){
    const bot_name = ref("");
    const bot_rating= ref("");
    
    $.ajax({
      url: "http://localhost:3000/pk/info",
      type: "get",
      success(resp){
        bot_name.value = resp.name;
        bot_rating.value = resp.rating;
      },
    });   

    return {
      bot_name,
      bot_rating,
    }
  },
}
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
