import { RouteConfig } from 'vue-router'
import AuthService from 'src/services/AuthService'

const authService = new AuthService()

const routes: RouteConfig[] = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'main', component: () => import('pages/Index.vue') },
      { path: '/about', component: () => import('pages/About.vue') },
      {
        path: '/fileHistory',
        beforeEnter: authService.isAuth,
        name: 'fileHistory',
        component: () => import('pages/FileHistory.vue')
      },
      {
        path: '/monitoring',
        beforeEnter: authService.isAuth,
        name: 'monitoring',
        component: () => import('pages/Monitoring.vue')
      },
      {
        path: '/settings',
        beforeEnter: authService.isAuth,
        name: 'settings',
        component: () => import('pages/Settings.vue')
      }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
