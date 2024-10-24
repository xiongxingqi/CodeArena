<template >
    <content-area-vue>
        用户的bots
    </content-area-vue>
</template>
<script>
import ContentAreaVue from '@/components/ContentAreaVue.vue'
// import axios from "axios";
// import {useStore} from "vuex";
export default {
  name: 'UserBotIndexView',
  components: {
      ContentAreaVue
  },
  setup(){
    // const store = useStore();
   // axios.post("/user/bot/addBot",{
   //   title: 'myBot',
   //   description: '我超级bot',
   //   content: 'include'
   // },{
   //   headers:{
   //     Authorization: 'Bearer '+ store.state.user.token
   //   }
   // }).then((resp)=>{
   //   console.log(JSON.stringify(resp.data));
   // }).catch((resp)=>{
   //   alert(resp.status);
   // })
   //  axios.get('/user/bot/getList',{
   //    headers:{
   //      Authorization: 'Bearer '+ store.state.user.token
   //    }
   //  }).then((resp)=>{
   //    console.log(JSON.stringify(resp.data));
   //  }).catch((resp)=>{
   //    alert(resp.status);
   //  });
   //  axios.delete('/user/bot/remove?id=2',{
   //    headers: {
   //      Authorization: 'Bearer '+ store.state.user.token
   //    }
   //  }).then((resp)=>{
   //    console.log(JSON.stringify(resp.data));
   //  }).catch((error)=>{
   //    alert(error.status);
   //  });

  }

}
</script>
<style scoped>
    
</style>