import { Recommendations } from 'src/models/Recommendation'
import AnalyseResponse from 'src/models/AnalyseResponse'

export default interface RecommendationInterface {
  recommendations: Recommendations | null
  yakeResponse: AnalyseResponse | null
}
