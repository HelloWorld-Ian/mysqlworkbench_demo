import { createRouter, createWebHashHistory } from 'vue-router'
import create from '../components/create.vue'
import display from '../components/display.vue'
const routes=[
    {
        name:"create",
        path:"/",
        component:create
    },
    {
        name:"display",
        path:"/display",
        component:display
    }
]

const router=createRouter({
    history:createWebHashHistory(),
    routes
})

export default router