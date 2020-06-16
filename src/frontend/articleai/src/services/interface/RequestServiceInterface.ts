import AnalyseResponse from '@/models/AnalyseResponse.js'
import ArticleFile from '@/models/ArticleFile/ArticleFile'

export default interface RequestServiceInterface{
  sendAndAnalyse(articleFile: ArticleFile): Promise<AnalyseResponse[]>;
}
