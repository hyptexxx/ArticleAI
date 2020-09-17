import Vue from 'vue'
import store from '@/store'
import MainComponent from '@/components/MainComponent.vue'
import ArticleFile from '@/components/partials/files/ArticleFile.vue'
import Notifications from 'vue-notification'

Vue.use(Notifications)
const discographyAlbum = document.getElementById('main-component')
Vue.component('file', ArticleFile)
if (discographyAlbum) {
  new Vue({
    store,
    render: h => h(MainComponent)
  }).$mount('#main-component')
}
