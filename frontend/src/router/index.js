import { createRouter, createWebHistory } from 'vue-router'
import Agendar from '../views/Agendar.vue'
import Extrato from '../views/Extrato.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/agendar'
    },
    {
      path: '/agendar',
      name: 'agendar',
      component: Agendar
    },
    {
      path: '/extrato',
      name: 'extrato',
      component: Extrato
    }
  ]
})

export default router