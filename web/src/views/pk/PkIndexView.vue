<template >
  <play-ground-vue v-if="$store.state.pk.status === 'playing'"/>
  <MatchGround v-if="$store.state.pk.status === 'matching'"/>
</template>
<script>
import PlayGroundVue from '@/components/PlayGroundVue.vue'
import {onMounted, onUnmounted} from "vue";
import {useStore} from "vuex";
import MatchGround from '../../components/MatchGround.vue'
export default {
    name: 'PkIndexView',
    components: {
        PlayGroundVue,
        MatchGround,
    },
    setup(){
      let socket = null;
      const store = useStore();
      const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;

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
          console.log(message)
          if (message.event === 'match_success') {
            store.commit("updateOpponent",{
              username: message.opponent_username,
              photo: message.opponent_photo
            })
            setTimeout(()=>{
              store.commit("updateStatus","playing");
            },2000)
            store.commit("updateGameMap",message.game_map);
          }
        }
        socket.onclose = () =>{
          console.log("closed!")
        }
      });
      onUnmounted(()=>{
        socket.close();
        store.commit("updateStatus","matching");
      })
    }
}
</script>
<style scoped>
    
</style>