import {createRouter, createWebHistory} from 'vue-router'
import Layout from '@/views/Layout/index.vue'
import Login from '@/views/Login/index.vue'
import Home from '@/views/Home/index.vue'
import CharactIntro from '@/views/Home/components/CharactIntro.vue'
import AuthorIntro from '@/views/Home/components/AuthorIntro.vue'

const routes = [
    {
        path: '/',
        component: Home
    },
    {
        path: '/charactintro',
        component: Layout,
        children: [
            {
                path: '',
                component: CharactIntro,
            },
            {
                path: '/authorintro',
                component: AuthorIntro
            }
        ]
    },
    {
        path: '/login',
        component: Login
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
