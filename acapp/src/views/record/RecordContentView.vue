<template >
  <play-ground-vue />
</template>
<script>
import PlayGroundVue from "@/components/PlayGroundVue.vue";
import {useStore} from "vuex";
import {onUnmounted} from "vue";

export default {
  name: "RecordContentView",
  components: {
    PlayGroundVue
  },
  setup(){
    const store = useStore();
    onUnmounted(() =>{
      store.commit("update_is_record",false);
      store.commit("update_steps",{
        asteps: "",
        bsteps: ""
      });
      store.commit("updateGameMap",{
        game_map: null,
        a_id: 0,
        a_sx: 0,
        a_sy: 0,
        b_id: 0,
        b_sx: 0,
        b_sy: 0,
      });
      store.commit("update_record_loser","All");

    });

  }
}
</script>
<style scoped>

</style>