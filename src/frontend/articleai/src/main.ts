import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false


// Vue.component('main-component',MainComponent)

new Vue({
  render: h => h(App),
}).$mount('#app')
