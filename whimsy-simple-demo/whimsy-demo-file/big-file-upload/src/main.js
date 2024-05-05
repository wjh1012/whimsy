import {createApp} from 'vue'
import App from './App.vue'

//使用element-plus需要引入的文件
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

// element-plus icon
import * as ElementPlusIconsVue  from '@element-plus/icons-vue'

// 文件分片上传
import uploader from 'vue-simple-uploader';
import 'vue-simple-uploader/dist/style.css';

let app = createApp(App);
app.use(ElementPlus).use(uploader);

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.mount('#app')
