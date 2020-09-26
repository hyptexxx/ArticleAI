import ArticleFile from '@/models/ArticleFile/ArticleFile'

export default interface ValidationYakeServiceInterface {
  getValidationErrorsFromArticle (articleFile: ArticleFile): [string];
}
