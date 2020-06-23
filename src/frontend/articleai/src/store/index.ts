import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    file: File
  },
  mutations: {
    setArticleFile (state: any, file: File): void {
      state.file = file
    }
  },
  actions: {},
  modules: {},
  getters: {
    getArticleFile: state => {
      return state.file
    }
  }
})
