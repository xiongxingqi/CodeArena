<template >
    <nav class="navbar navbar-expand-lg bg-black " data-bs-theme="dark" >
        <div class="container ">
            <!-- <a class="navbar-brand" href="/">King Of Bots</a> -->
            <router-link class="navbar-brand" :to="{name: 'home'}">King Of Bots</router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                <!-- <a class="nav-link active" aria-current="page" href="/pk">对战</a> -->
                <router-link class="nav-link active" aria-current="page" :to="{name: 'pk'}">对战</router-link>
                </li>
                <li class="nav-item">
                <!-- <a class="nav-link" href="/record">对战记录</a> -->
                <router-link class="nav-link" :to="{name: 'record'}">对战记录</router-link>
                </li>
                <li class="nav-item">
                <!-- <a class="nav-link" href="/rankList">排行榜</a> -->
                <router-link class="nav-link" :to="{name: 'rank-list'}">排行榜</router-link>
                </li>
            </ul>

             <ul class="navbar-nav mb-2 mb-lg-0" v-if="$store.state.user.is_login">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        {{$store.state.user.username}}
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <!-- <a class="dropdown-item" href="/userBots">bots</a> -->
                            <router-link class="dropdown-item" :to="{name: 'user-bots'}">bots</router-link>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
                    </ul>
                </li>
            </ul>
              <ul class="navbar-nav mb-2 mb-lg-0" v-else>
                <li class="nav-item ">
                  <router-link class="nav-link" :to="{name: 'user-account-login'}">登录</router-link>
                </li>
                <li class="nav-item ">
                  <router-link class="nav-link" :to="{name: 'user-account-register'}">注册</router-link>
                </li>
              </ul>
            </div>
        </div>
    </nav>
</template>
<script>
import {useStore} from "vuex";
import router from "@/router";

export default {
    name: "NavBarVue",
    setup(){
      const store =useStore()
      const logout = () =>{
        store.dispatch('logout');
        router.push({name: 'home'});
      }
      return {
        logout,
      }
    }
}
</script>
<style >
    
</style>