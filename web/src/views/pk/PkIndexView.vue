<template >
    <play-ground-vue/>
</template>
<script>
import PlayGroundVue from '@/components/PlayGroundVue.vue'
import {onMounted, onUnmounted} from "vue";
import {useStore} from "vuex";
export default {
    name: 'PkIndexView',
    components: {
        PlayGroundVue,
    },
    setup(){
      let socket = null;
      const store = useStore();
      const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.id}`;

      onMounted(()=>{
        socket=new WebSocket(socketUrl);

        socket.onopen = () => {
          console.log("connected!")

        }
        socket.onmessage= (msg) =>{
          const message = JSON.parse(msg.data);
          console.log(message)
        }
        socket.onclose = () =>{
          console.log("closed!")
        }
      });
      onUnmounted(()=>{
        socket.close();
      })
    }
}
</script>
<style scoped>
    
</style>