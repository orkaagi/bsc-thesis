import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    rollupOptions: {
      input: {
        index: './index.html',
        help: './help.html',
        start: './start.html',
        login: './login.html',
        register: './register.html',
        results: './results.html',
      }
    }
  },
  plugins: [
    react(), 
  ],
})
