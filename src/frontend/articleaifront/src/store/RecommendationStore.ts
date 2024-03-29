import Vue from 'vue'
import Component from 'vue-class-component'
import { namespace } from 'vuex-class'
import { Recommendations } from 'src/models/Recommendation'
import AnalyseResponse from 'src/models/AnalyseResponse'

const RecommendationModule = namespace('RecommendationModule')

@Component
export default class RecommendationStore extends Vue {
  // setters
  @RecommendationModule.Mutation('setRecommendation')
  public setRecommendation!: (recommendation: Recommendations | null) => void

  @RecommendationModule.Mutation('setYakeResponse')
  public setYakeResponse!: (yakeResponse: AnalyseResponse | null) => void

  // getters
  @RecommendationModule.Getter('getRecommendation')
  public recommendations!: Recommendations

  @RecommendationModule.Getter('getYakeResponse')
  public yakeResponseStore!: AnalyseResponse
}
