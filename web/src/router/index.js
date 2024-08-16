import PkIndexView from '@/views/pk/PkIndexView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import NotFound from '@/views/error/NotFound.vue';
import RankListIndexView from '@/views/ranklist/RankListIndexView.vue';
import RecordListIndexView from '@/views/record/RecordListIndexView.vue';
import UserBotIndexView from '@/views/user/bot/UserBotIndexView.vue'

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
