import axios from 'axios'
import NProgress from 'nprogress'

// -------------------------------------------------------------------------------------------------------------------->

// ---------------------------------------------NPROGRESS INTERCEPTORS------------------------------------------------->

axios.interceptors.request.use((config) => {
  NProgress.start()
  return config
}, (error) => {
  return Promise.reject(error)
})

axios.interceptors.response.use((response) => {
  NProgress.done()
  return response
}, (error) => {
  NProgress.done()
  return Promise.reject(error)
})
