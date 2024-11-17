<template >
    <div ref="parent" class="game_map">
        <canvas ref="canvas" tabindex="0"></canvas>
    </div>
</template>
<script>
import { onMounted, ref } from 'vue';
import {GameMap} from '@/assets/scripts/GameMap';
import {useStore} from "vuex";
export default {
    name: 'GameMap',
    setup(){
        let parent=ref(null);
        let canvas=ref(null);
        const store = useStore();
        onMounted(() =>{
            store.commit("updateGameObject",
                new  GameMap(canvas.value.getContext('2d'),parent.value ,store));
        });
        return {
            parent,
            canvas,
        }
    },
}
</script>
<style scoped>
.game_map{
    width: 100%;
    height: 100%;
    /* background-color: lightblue; */
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>