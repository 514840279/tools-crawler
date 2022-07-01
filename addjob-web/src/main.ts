import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import { createPinia } from 'pinia'
import {router} from './router'
// 如果您正在使用CDN引入，请删除下面一行。
import * as ElementPlusIcons from '@element-plus/icons'
import 'dayjs/locale/zh-cn'
import locale from 'element-plus/lib/locale/lang/zh-cn'

import App from './App.vue'


 
const app = createApp(App)

for (const name in ElementPlusIcons){
	app.component(name,(ElementPlusIcons as any)[name])
}

app.use(ElementPlus, { size: 'small', zIndex: 3000, locale });
app.use(VueAxios, axios);
app.use(router);
app.use(createPinia());


app.mount('#app');
