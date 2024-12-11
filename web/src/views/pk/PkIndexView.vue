<template >
  <play-ground-vue v-if="$store.state.pk.status === 'playing'"/>
  <MatchGround ref="match_ground" v-if="$store.state.pk.status === 'matching'"/>
  <ResultBoard v-if="$store.state.pk.loser !== 'none' "/>
  <div class="user-pos" v-if="$store.state.pk.status === 'playing' && $store.state.user.id === $store.state.pk.a_id">在左下角</div>
  <div class="user-pos" v-if="$store.state.pk.status === 'playing' && $store.state.user.id === $store.state.pk.b_id">在右上角</div>
</template>
<script>
import PlayGroundVue from '@/components/PlayGroundVue.vue'
import {onMounted, onUnmounted, useTemplateRef} from "vue";
import {useStore} from "vuex";
import MatchGround from '../../components/MatchGround.vue'
import ResultBoard from "@/components/ResultBoard.vue";
export default {
    name: 'PkIndexView',
    components: {
        PlayGroundVue,
        MatchGround,
        ResultBoard
    },
    setup(){
      let socket = null;
      const store = useStore();
      // const server = 'wss://app7275.acapp.acwing.com.cn';
      const local = 'ws://127.0.0.1:3000';
      const socketUrl = `${local}/websocket/${store.state.user.token}`;

      const match_ground = useTemplateRef("match_ground");

      onMounted(()=>{

        store.commit("updateOpponent",{
          username: '你的对手',
          photo: 'https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png'
        });
        socket=new WebSocket(socketUrl);

        store.commit("updateSocket",socket);

        socket.onopen = () => {
          console.log("connected!")
        }



        socket.onmessage= (msg) =>{
          const message = JSON.parse(msg.data);
          // console.log(message)
          //匹配成功
          if (message.event === 'match_success') {
            match_ground.value.match_btn_info = "开始匹配";
            console.log(match_ground.value.match_btn_info );
            store.commit("updateOpponent",{
              username: message.opponent_username,
              photo: message.opponent_photo
            })
            //对战见面路由
            setTimeout(()=>{
              store.commit("updateStatus","playing");
            },100)
            //更新玩家信息
            store.commit("updateGameMap",message.game);
          }else if(message.event === "move"){

            const [snakeA,snakeB] = store.state.pk.game_object.snakes;
            snakeA.set_direction(message.a_direction);
            snakeB.set_direction(message.b_direction);

          }else if(message.event === "result"){
            const [snakeA,snakeB] = store.state.pk.game_object.snakes;
            if(message.loser === "All"|| message.loser === "A" ){
              snakeA.status = "die";
            }
            if(message.loser === "All" || message.loser === "B"){
              snakeB.status = "die";
            }
            store.commit("updateLoser",message.loser);
          }
        }



        socket.onclose = () =>{
          console.log("closed!")
        }
      });


      //切换页面的逻辑
      onUnmounted(()=>{
        console.log("UnMount PkIndex!");
        store.commit("updateStatus","matching");
        store.commit("updateLoser","none");
        store.commit("updateGameMap",{
          game_map: null,
          a_id: 0,
          a_sx: 0,
          a_sy: 0,
          b_id: 0,
          b_sx: 0,
          b_sy: 0,
        });
      })
    }
}
</script>
<style scoped>
div.user-pos {
  text-align: center;
  font-size: 40px;
  color: white;
  font-weight: 600;
}
</style>