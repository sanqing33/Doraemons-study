import {createRouter, createWebHistory} from 'vue-router'
import Layout from '@/views/index.vue'
import Login from '@/components/Login.vue'
import Home from '@/views/Home.vue'
import CharactIntro from '@/components/CharactIntro.vue'
import AuthorIntro from '@/components/AuthorIntro.vue'

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
