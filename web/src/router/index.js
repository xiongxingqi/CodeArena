import PkIndexView from '@/views/pk/PkIndexView.vue';
import { createRouter, createWebHistory } from 'vue-router';
import NotFound from '@/views/error/NotFound.vue';
import RankListIndexView from '@/views/ranklist/RankListIndexView.vue';
import RecordListIndexView from '@/views/record/RecordListIndexView.vue';
import UserBotIndexView from '@/views/user/bot/UserBotIndexView.vue'
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView.vue";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView.vue";
import store from "@/store";
import RecordContentView from "@/views/record/RecordContentView.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: { name: 'pk' },
    meta: {
      requiredAuth: true
    }
  },
  {
    path: '/pk',
    name: 'pk',
    component: PkIndexView,
    meta: {
      requiredAuth: true
    }
  },
  {
    path: "/record/content/:record_id",
    name: "recordContent",
    component: RecordContentView,
    meta: {
      requiredAuth: true,
    }

  },
  {
    path: '/rankList',
    name: 'rank-list',
    component: RankListIndexView,
    meta: {
      requiredAuth: true
    }
  },
  {
    path: '/record',
    name: 'record',
    component: RecordListIndexView,
    meta: {
      requiredAuth: true
    }
  },
  {
    path: '/user/account/login',
    name: 'user-account-login',
    component: UserAccountLoginView,
    meta: {
      requiredAuth: false
    }
  },
  {
    path: '/user/account/register',
    name: 'user-account-register',
    component: UserAccountRegisterView,
    meta: {
      requiredAuth: false
    }
  },
  {
    path: '/userBots',
    name: 'user-bots',
    component: UserBotIndexView,
    meta: {
      requiredAuth: true
    }
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

router.beforeEach((to, from, next) => {
  if (to.meta.requiredAuth &&store.state.user.is_login === false) {
    next({name: "user-account-login"});
  }else {
    next();
  }
})
export default router
