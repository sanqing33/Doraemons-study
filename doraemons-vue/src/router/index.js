import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        component: ()=>import('@/views/Home.vue')
    },
    {
        path: '/charactintro',
        component: ()=>import('@/views/index.vue'),
        children: [
            {
                path: '',
                component: () => import('@/components/CharactIntro.vue'),
            },
            {
                path: '/authorintro',
                component: ()=>import('@/components/AuthorIntro.vue'),
            }
        ]
    },
    {
        path: '/login',
        component: ()=>import('@/components/Login.vue')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
