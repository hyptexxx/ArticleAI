import Vue from 'vue'

import '../node_modules/nprogress/nprogress.css'
import '@/assets/style/main.css'

import '@/components/main'

import '@/interceptors/RequestInterceptor'
import '@/configurations/NProgressConfigurations'

import Notifications from 'vue-notification'

Vue.use(Notifications)
Vue.config.productionTip = false
