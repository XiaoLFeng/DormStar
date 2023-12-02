import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  base: '/',
  routes: [
    {
      path: '/',
      component: () => import('../views/IndexWelcome.vue'),
    },
    {
      path: '/sign/in',
      component: () => import('../views/Sign/SignIn.vue'),
    },
    {
      path: '/sign/up',
      component: () => import('../views/Sign/SignUp.vue'),
    },
    {
      path: '/sign/reset',
    },
    {
      path: '/dashboard',
      component: () => import('../views/Dashboard.vue'),
    }
  ]
})

export default router
