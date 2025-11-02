import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Dashboard from '@/views/Dashboard.vue'
import FoodDonors from '@/views/FoodDonors.vue'
import WasteItems from '@/views/WasteItems.vue'
import CollectionCenters from '@/views/CollectionCenters.vue'
import Processors from '@/views/Processors.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/dashboard'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path: '/donors',
      name: 'FoodDonors',
      component: FoodDonors
    },
    {
      path: '/waste-items',
      name: 'WasteItems',
      component: WasteItems
    },
    {
      path: '/centers',
      name: 'CollectionCenters',
      component: CollectionCenters
    },
    {
      path: '/processors',
      name: 'Processors',
      component: Processors
    }
  ]
})

// No authentication guards - all pages are public
export default router