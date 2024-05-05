import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'

// https://vitejs.dev/config/
export default ({mode}) => {
    let env = loadEnv(mode, process.cwd());

    let proxy = {}
    proxy[env['VITE_API_URL']] = {
        target: env['VITE_BASE_URL'],
        changeOrigin: true,
        rewrite: path => path.replace(new RegExp(env['VITE_API_URL_REG']), ''),
    }

    return defineConfig({
        define: {
            'process.env': env
        },
        plugins: [
            vue(),
            AutoImport({
                imports: ['vue', 'vue-router'] //自动导入vue和vue-router相关函数
            })
        ],
        resolve: {
            alias: {
                '@': fileURLToPath(new URL('./src', import.meta.url))
            }
        },
        server: {
            port: env['VITE_PORT'],
            proxy: proxy
            // proxy: {
            //     '/api': {
            //         target: env['VITE_BASE_URL'],
            //         changeOrigin: true,
            //         rewrite: path => path.replace(/^\/api/, ''),
            //     }
            // }
        }
    })
}
