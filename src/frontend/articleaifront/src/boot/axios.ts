import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { boot } from 'quasar/wrappers'
import { AuthResponse } from 'src/models/auth'
import { LocalStorage } from 'quasar'

const config: AxiosRequestConfig = {
  baseURL: process.env.API_BASE_URL
}

declare module 'vue/types/vue' {
  interface Vue {
    $axios: AxiosInstance;
  }
}

const instance = axios.create(config)

export default boot(({ Vue, router }) => {
  // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
  Vue.prototype.$axios = axios
  instance.interceptors.response.use(response => response, async (error: { response: AxiosResponse<AuthResponse>}) => {
    const response: AxiosResponse<AuthResponse> = error.response
    if (response) {
      if (response.status === 401 || response.status === 403) {
        LocalStorage.remove('isLogged')
      }
      return response
    }
    return Promise.reject(error)
  })
  const vue = Vue as {prototype: {$axios: AxiosInstance}}
  vue.prototype.$axios = instance
})
