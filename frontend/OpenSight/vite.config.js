import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import axios from 'axios';

axios.defaults.baseURL = 'http://192.168.31.168:8080';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  server:{
    proxy:{
      '/api':{
        target : "http://192.168.31.168:8080/api",
        changeOrigin: true,
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})

