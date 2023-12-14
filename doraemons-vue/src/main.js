import './styles/normalize.css';
import './styles/icon.css';
import 'element-plus/dist/index.css';

import {createApp} from 'vue';

import App from './App.vue';
import router from './router';
import axios from "axios";

axios.defaults.baseURL = 'http://localhost:8080';

const app = createApp(App)
app.use(router);
app.config.devtools = true;
app.mount('#app')


