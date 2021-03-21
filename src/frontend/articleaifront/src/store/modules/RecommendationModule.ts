import { Module } from 'vuex'
import RecommendationInterface from '../interface/RecommendationInterface'
import { StoreInterface } from 'src/store/interface/StoreInterface'
import { Recommendations } from 'src/models/Recommendation'

const RecommendationModule: Module<RecommendationInterface, StoreInterface> = {
  namespaced: true,
  state: () => ({
    recommendations: {} as Recommendations | null
  }),
  mutations: {
    setRecommendation (state: any, recommendations: Recommendations | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.recommendations = recommendations
    }
  },
  actions: {},
  modules: {},
  getters: {
    getRecommendation: state => {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return state.recommendations
    }
  }
}

export default RecommendationModule
