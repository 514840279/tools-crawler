import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 4000,
    open: true,
    // 配置代理
    proxy: {
      // 请求的路径前缀只要是 /testaxios 就会被拦截走这个代理
      '/job': {
        /**
          *  请求的目标资源再经过替换成 /httphwm/getList 后，
          *  会加上 http://127.0.0.1:9693 这个前缀，
          *  最后请求的URL为: http://127.0.0.1:9693/httphwm/getList
          */
        target: 'http://localhost:19011/',
        ws: true,
        changeOrigin: true,
        // 拦截到的请求路径 testaxios/httphwm/getList，/testaxios会被替换成空
        // rewrite: (path) => path.replace(/^\/task/, '/task'),
      },
      
      // 请求的路径前缀只要是 /testaxios 就会被拦截走这个代理
      '/repl': {
        /**
          *  请求的目标资源再经过替换成 /httphwm/getList 后，
          *  会加上 http://127.0.0.1:9693 这个前缀，
          *  最后请求的URL为: http://127.0.0.1:9693/httphwm/getList
          */
        target: 'http://localhost:19011/',
        ws: true,
        changeOrigin: true,
        // 拦截到的请求路径 testaxios/httphwm/getList，/testaxios会被替换成空
        // rewrite: (path) => path.replace(/^\/task/, '/task'),
      },
      '/search': {
        /**
          *  请求的目标资源再经过替换成 /httphwm/getList 后，
          *  会加上 http://127.0.0.1:9693 这个前缀，
          *  最后请求的URL为: http://127.0.0.1:9693/httphwm/getList
          */
        target: 'http://localhost:19011/',
        ws: true,
        changeOrigin: true,
        // 拦截到的请求路径 testaxios/httphwm/getList，/testaxios会被替换成空
        // rewrite: (path) => path.replace(/^\/task/, '/task'),
      },
    }

  
  },

})
