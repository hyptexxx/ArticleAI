import { Vue, Component } from 'vue-property-decorator'
import RequestServiceInterface from '@/services/interface/RequestServiceInterface'
import axios from 'axios'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import ArticleFileMeta from '@/models/ArticleFile/ArticleFileMeta'
import FullArticle from '@/models/FullArticle'
import { Class } from '@/models/Class'
import { ClassActuality } from '@/models/ClassActuality'

@Component
export default class RequestService extends Vue implements RequestServiceInterface {
  async getActualityRequest (classes: Class[]): Promise<ClassActuality[]> {
    const formData: FormData = new FormData()
    formData.append('classes', JSON.stringify(classes))
    const response = await axios.post<ClassActuality[]>(
      '/api/actuality/analyze', formData
    )
    return response.data
  }

  async getRecommendationsRequest (actuality: ClassActuality[]): Promise<string> {
    const formData: FormData = new FormData()
    formData.append('actualityPair', JSON.stringify(actuality))
    const response = await axios.get<string>(
      '/api/actuality/analyze', {
        params: {
          actualityPair: formData
        }
      })
    return response.data
  }

  async sendAndAnalyse (articleFile: ArticleFile): Promise<AnalyseResponse[]> {
    const formData: FormData = new FormData()
    if (articleFile.file) {
      formData.append('file', articleFile.file)
    }
    const response = await axios.post<AnalyseResponse[]>(
      '/api/files/analyze',
      this.createFormDataForArticleFile(articleFile, formData)
    )
    return response.data
  }

  async sendRequestToYandexFromServer (): Promise<string> {
    const result = await axios.get<string>('/api/yandex/search_count')
    return result.data
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
    const response = await axios.post<AnalyseResponse[]>('/api/yake/analyze', formData)
    return response.data
  }

  async saveResultRequest (analyseResponse: AnalyseResponse[], articleFile: ArticleFile, classes: Class[]): Promise<number | null> {
    const formData: FormData = new FormData()
    formData.append('analyseResponse', JSON.stringify(analyseResponse))
    formData.append('classes', JSON.stringify(classes))
    this.createFormDataForArticleFile(articleFile, formData)
    if (articleFile.file) {
      formData.append('file', articleFile.file)
    }
    const result = await axios.post<number>('/api/yake/saveResultEntity', formData)
    if (result.status === 200) {
      this.$notify({
        group: 'foo',
        type: 'success',
        title: 'Результаты сохранены',
        text: 'Сохренено'
      })
      return result.data
    } else {
      this.$notify({
        group: 'foo',
        type: 'error',
        title: 'Ошибка сохренения результатов',
        text: 'Ошибка сохренения'
      })
      return null
    }
  }

  async classesAnalyseRequest (analyseResponse: AnalyseResponse[], articleId: number): Promise<Class[]> {
    const formData: FormData = new FormData()
    formData.append('analyseResponse', JSON.stringify(analyseResponse))
    formData.append('articleId', articleId.toString())
    const result = await axios.post<Class[]>('/api/classes/analyse', formData)
    if (!result.data) {
      this.$notify({
        group: 'foo',
        type: 'error',
        title: 'Классов с весом > 0 не найдено',
        text: 'Анализ классов'
      })
    }
    return result.data
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

  async loadSavedResults (yakeId: number): Promise<FullArticle> {
    const formData: FormData = new FormData()
    formData.append('yakeId', yakeId.toString())
    const response = await axios.post<FullArticle>('/api/yake/response', formData)
    return response.data
  }
}
