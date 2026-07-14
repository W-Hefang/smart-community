// src/router/index.ts
import { createRouter, createWebHashHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/Home.vue'),
    redirect: '/user/list',
    children: [
      {
        path: '/user/list',
        name: 'userList',
        component: () => import('../views/user/UserList.vue')
      },
      {
        path: '/community/list',
    name: 'communityList',
    component: () => import('../views/community/CommunityList.vue')
      },
      {
        path: '/community/statistics',
        name: 'communityStatistics',
        component: () => import('../views/community/CommunityList.vue')
      },
      {
        path: '/announcement/list',
        name: 'announcementList',
        component: () => import('../views/announcement/AnnouncementList.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(async(to, _from, next) => {
  const userStore = useUserStore()
  await userStore.loadUserInfo()
  
  if (to.path !== '/' && !userStore.token) {
    next('/')
  } else if (to.path === '/' && userStore.token) {
    next('/home')
  } else {
    next()
  }
})

export default router