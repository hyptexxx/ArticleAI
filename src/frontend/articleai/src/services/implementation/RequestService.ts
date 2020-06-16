import { Vue, Component } from 'vue-property-decorator'
import RequestServiceInterface from '@/services/interface/RequestServiceInterface'
import axios from 'axios'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'

@Component
export default class RequestService extends Vue implements RequestServiceInterface {
  async sendAndAnalyse (articleFile: ArticleFile): Promise<AnalyseResponse[]> {
    const formData: FormData = new FormData()
    if (articleFile.file) {
      formData.append('file', articleFile.file)
    }
    formData.append('language', articleFile.meta.language)
    formData.append('max_ngram_size', articleFile.meta.maxNgramSize.toString())
    formData.append('number_of_keywords', articleFile.meta.numberOfKeywords.toString())
    formData.append('text', articleFile.meta.text)
    const response = await axios.post<AnalyseResponse[]>('http://localhost:8080/api/files/analyze', formData)
    return response.data
  }
}
