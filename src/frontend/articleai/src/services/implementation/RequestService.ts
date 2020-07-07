import { Vue, Component } from 'vue-property-decorator'
import RequestServiceInterface from '@/services/interface/RequestServiceInterface'
import axios from 'axios'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import ArticleFileMeta from '@/models/ArticleFile/ArticleFileMeta'

@Component
export default class RequestService extends Vue implements RequestServiceInterface {
  async sendAndAnalyse (articleFile: ArticleFile): Promise<AnalyseResponse[]> {
    const formData: FormData = new FormData()
    if (articleFile.file) {
      formData.append('file', articleFile.file)
    }
    const response = await axios.post<AnalyseResponse[]>(
      'http://localhost:8080/api/files/analyze',
      this.createFormDataForArticleFile(articleFile, formData)
    )
    return response.data
  }

  async sendTextAndAnalyse (articleFileMeta: ArticleFileMeta): Promise<AnalyseResponse[]> {
    const formData: FormData = new FormData()
    formData.append('language', articleFileMeta.language)
    formData.append('max_ngram_size', articleFileMeta.maxNgramSize.toString())
    formData.append('deduplication_thresold', articleFileMeta.deduplicationThresold.toString())
    formData.append('deduplication_algo', articleFileMeta.deduplicationAlgo.toString())
    formData.append('windowSize', articleFileMeta.windowSize.toString())
    formData.append('number_of_keywords', articleFileMeta.numberOfKeywords.toString())
    formData.append('text', articleFileMeta.text)
    const response = await axios.post<AnalyseResponse[]>('http://localhost:8080/api/yake/analyze', formData)
    return response.data
  }

  saveResultRequest (analyseResponse: AnalyseResponse[], articleFile: ArticleFile): void {
    const formData: FormData = new FormData()
    this.createFormDataForArticleFile(articleFile, formData)
    formData.append('analyseResponse', JSON.stringify(analyseResponse))
    axios.post<AnalyseResponse[]>('http://localhost:8080/api/yake/saveResultEntity', formData)
  }

  private createFormDataForArticleFile (articleFile: ArticleFile, formData: FormData): FormData {
    formData.append('language', articleFile.meta.language)
    formData.append('max_ngram_size', articleFile.meta.maxNgramSize.toString())
    formData.append('deduplication_thresold', articleFile.meta.deduplicationThresold.toString())
    formData.append('deduplication_algo', articleFile.meta.deduplicationAlgo.toString())
    formData.append('windowSize', articleFile.meta.windowSize.toString())
    formData.append('number_of_keywords', articleFile.meta.numberOfKeywords.toString())
    formData.append('text', articleFile.meta.text)
    return formData
  }
}
