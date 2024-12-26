<template >
    <div class="container contentArea">
        <div class="card">
            <div class="card-body">
            <slot></slot>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "ContentAreaVue",
}
</script>
<style scoped>
.contentArea{
    margin-top: 20px;
}
</style>