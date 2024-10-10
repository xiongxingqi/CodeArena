import PkIndexView from '@/views/pk/PkIndexView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import NotFound from '@/views/error/NotFound.vue';
import RankListIndexView from '@/views/ranklist/RankListIndexView.vue';
import RecordListIndexView from '@/views/record/RecordListIndexView.vue';
import UserBotIndexView from '@/views/user/bot/UserBotIndexView.vue'
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView.vue";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: { name: 'pk' },
  },
  {
    path: '/pk',
    name: 'pk',
    component: PkIndexView,
  },
  {
    path: '/rankList',
    name: 'rank-list',
    component: RankListIndexView,
  },
  {
    path: '/record',
    name: 'record',
    component: RecordListIndexView,
  },
  {
    path: '/user/account/login',
    name: 'user-account-login',
    component: UserAccountLoginView
  },
  {
    path: '/user/account/register',
    name: 'user-account-register',
    component: UserAccountRegisterView
  },
  {
    path: '/userBots',
    name: 'user-bots',
    component: UserBotIndexView,
  },
  {
    path: '/:matchAll(.*)',
    name: 'not-found',
    component: NotFound,
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
