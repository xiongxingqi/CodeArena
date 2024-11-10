<template >
  <div class="match_ground">
    <div class="row">
      <div class="col-6">
        <div class="photo">
          <img :src="$store.state.user.photo" alt="">
        </div>
        <div class="username">
          {{$store.state.user.username}}
        </div>
      </div>
      <div class="col-6">
        <div class="photo">
          <img :src="$store.state.pk.opponent_photo" alt="">
        </div>
        <div class="username">
          {{$store.state.pk.opponent_username}}
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <button @click="click_match_btn" type="button" class="btn btn-primary btn-lg">{{match_btn_info}}</button>
      </div>
    </div>
  </div>
</template>
<script>

import {ref} from "vue";

export default {
  setup(){
    const match_btn_info = ref('开始匹配');

    const click_match_btn = () =>{
      if (match_btn_info.value === '开始匹配') {
        match_btn_info.value = '取消';
      } else if(match_btn_info.value === '取消'){
        match_btn_info.value = '开始匹配';
      }
    }
    return {
      match_btn_info,
      click_match_btn,
    }

  }
}
</script>
<style scoped>
.match_ground{
  /* background-color: lightblue; */
  width: 60vw;
  height: 70vh;
  margin: 40px auto;
  background-color: rgba(50,50,50,0.5);
}
.photo{
  text-align: center;
  padding-top: 10vh;
}
.photo > img{
  border-radius: 50%;
  width: 15vw;
}
.username{
  text-align: center;
  padding-top: 2vh;
  font-size: 24px;
  font-weight: 600;
  color: white;
}
div.col-12 {
  padding-top: 10vh;
  text-align: center;
}
</style>