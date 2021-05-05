import { Module } from 'vuex'
import RecommendationInterface from '../interface/RecommendationInterface'
import { StoreInterface } from 'src/store/interface/StoreInterface'
import { Recommendations } from 'src/models/Recommendation'
import AnalyseResponse from 'src/models/AnalyseResponse'

const RecommendationModule: Module<RecommendationInterface, StoreInterface> = {
  namespaced: true,
  state: () => ({
    recommendations: {} as Recommendations | null,
    yakeResponse: {} as AnalyseResponse | null
  }),
  mutations: {
    setRecommendation (state: any, recommendations: Recommendations | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.recommendations = recommendations
    },
    setYakeResponse (state: any, yakeResponse: AnalyseResponse | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.yakeResponse = yakeResponse
    }
  },
  actions: {},
  modules: {},
  getters: {
    getRecommendation: state => {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return state.recommendations
    },

    getYakeResponse: state => {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return state.yakeResponse
    }
  }
}

export default RecommendationModule
