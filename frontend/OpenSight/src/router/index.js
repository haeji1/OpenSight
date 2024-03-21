import { createRouter, createWebHistory } from 'vue-router';
import IntroView from '@/views/intro/Intro.vue';
import LoginView from '@/views/auth/Login.vue';
import MainView from '@/views/dashboard/Main.vue';
import IntroView from '@/views/intro/Intro.vue';
import RegisterView from '@/views/auth/Register.vue'
import RegisterCompleteView from '@/views/auth/RegisterComplete.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    },
    {
      path: '/main',
      name: 'Main',
      component: MainView
    },
    {
      path: '/',
      name: 'Intro',
      component: IntroView
    },
    {
      path: '/register',
      name: 'Reigster',
      component: RegisterView
    },
    {
      path: '/register/complete',
      name: 'RegisterComplete',
      component: RegisterCompleteView
    }
  ]
})

export default router
