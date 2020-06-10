import Vue from 'vue'
import App from './App.vue'
import File from '@/components/partials/files/ArticleFile.vue'

Vue.config.productionTip = false

Vue.component('file', File)

new Vue({
  render: h => h(App)
}).$mount('#app')
