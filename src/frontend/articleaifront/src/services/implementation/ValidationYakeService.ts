import { Vue, Component } from 'vue-property-decorator'
import ValidationYakeServiceInterface from '@/services/interface/ValidationYakeServiceInterface'
import ArticleFile from '@/models/ArticleFile/ArticleFile'

@Component
export default class ValidationYakeService extends Vue implements ValidationYakeServiceInterface {
  getValidationErrorsFromArticle (articleFile: ArticleFile): [string] {
    const errors: [string] = ['']
    errors.splice(0, errors.length)

    if (articleFile.meta.language === '') {
      errors.push('поле language - пусто')
    }
    if (articleFile.meta.maxNgramSize === null) {
      errors.push('поле maxNgramSize - пусто')
    }
    if (articleFile.meta.deduplicationThresold === null) {
      errors.push('поле deduplicationThresold - пусто')
    }
    if (articleFile.meta.deduplicationAlgo === '') {
      errors.push('поле deduplicationAlgo - пусто')
    }
    if (articleFile.meta.windowSize === null) {
      errors.push('поле windowSize - пусто')
    }
    if (articleFile.meta.numberOfKeywords === null) {
      errors.push('поле numberOfKeywords - пусто')
    }
    if (articleFile.meta.text === '') {
      errors.push('поле text - пусто')
    }
    return errors
  }
}
