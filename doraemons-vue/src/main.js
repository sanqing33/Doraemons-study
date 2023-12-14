import './styles/normalize.css'
import './styles/icon.css'

import {createApp} from 'vue'

import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.config.devtools = true;
app.mount('#app')


