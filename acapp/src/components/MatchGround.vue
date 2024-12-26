<template >
  <div class="match_ground">
    <div class="row">
      <div class="col-4">
        <div class="photo">
          <img :src="$store.state.user.photo" alt="">
        </div>
        <div class="username">
          {{$store.state.user.username}}
        </div>
      </div>
      <div class="col-4">
        <div class="user-select-bot">
          <select v-model="select_bot" class="form-select" aria-label="Default select example">
            <option selected value="-1">亲自出马</option>
            <option v-for="bot in bots" :value="bot.id" :key="bot.id" >
              {{bot.title}}
            </option>
          </select>
        </div>
      </div>
      <div class="col-4">
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
<script >

import {onUnmounted, ref} from "vue";
import {useStore} from "vuex";
import axios from "axios";

export default {
  setup(props,{expose}){
    const match_btn_info = ref('开始匹配');
    const store = useStore();

    const bots = ref([]);
    const select_bot = ref("-1");

    expose({
      match_btn_info
    });
    const click_match_btn = () =>{
      if (match_btn_info.value === '开始匹配') {
        match_btn_info.value = '取消';

        store.state.pk.socket.send(JSON.stringify({
          event: "start_match",
          botId: select_bot.value
        }))
      } else if(match_btn_info.value === '取消'){
        match_btn_info.value = '开始匹配';
        store.state.pk.socket.send(JSON.stringify({
          event: 'stop_match'
        }));
      }
    }
    const refresh=() =>{
      axios.get('/user/bot/getList',{
        headers: {
          Authorization: 'Bearer ' + store.state.user.token
        }
      }).then((resp)=>{
        const result = resp.data;
        if(result.code === 1){
          bots.value=result.data;
        }else{
          alert(result.message);
        }
      }).catch((error)=>{
        alert("网络环境波动,请稍后再试"+error.status);
      });
    }


    onUnmounted(() =>{

      console.log("UnMounted MatchGround!");
      if(match_btn_info.value === "取消" ){
        store.state.pk.socket.send(JSON.stringify({
          event: "stop_match",
        }))
      }
    });

    refresh();

    return {
      match_btn_info,
      click_match_btn,
      bots,
      select_bot,
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
div.user-select-bot{
  padding-top: 60%;
}
select.form-select{
  width: 60%;
  margin: 0 auto;
}
</style>