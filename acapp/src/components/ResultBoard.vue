<script>
import {useStore} from "vuex";

export default {
  name: "ResultBoard",
  setup(){
    const store = useStore();
    console.log(store.state.user.id,store.state.pk.a_id)
    const oneMore=()=>{
      store.commit("updateOpponent",{
        username: '你的对手',
        photo: 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png'
      });
      store.commit("updateStatus","matching");
      store.commit("updateLoser",'none')
    }
    return {
     oneMore
    };
  },

}
</script>

<template>
<div class="result-board">
  <div class="result-board-text" v-if="$store.state.pk.loser === 'All'">
    Draw
  </div>
  <div class="result-board-text" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id === $store.state.user.id">
    Lose
  </div>
  <div class="result-board-text" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id === $store.state.user.id">
    Lose
  </div>
  <div class="result-board-text" v-else>
    win
  </div>

  <div class="result-board-btn">
    <button type="button" class="btn btn-warning btn-lg" @click="oneMore">再来一把!</button>
  </div>
</div>
</template>

<style scoped>
div.result-board{
  width: 40vw;
  height: 40vh;
  background-color: rgba(50,50,50,0.5);
  position: absolute;
  top: 20vh;
  left: 30vw;

}
div.result-board-text{
  text-align: center;
  font-size: 90px;
  color: white;
  padding-top: 5vh;
  font-weight: 600;
  font-style: italic;
}
div.result-board-btn{
  text-align: center;
  padding-top: 10vh;
}
</style>