<template >
    <div class="play_ground">
        <game-map-vue/>
    </div>
</template>
<script>
import GameMapVue from './GameMapVue.vue'
export default {
    name: "PlayGroundVue",
    components: {
        GameMapVue,
    }
}
</script>
<style scoped>
.play_ground{
    /* background-color: lightblue; */
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
}
</style>