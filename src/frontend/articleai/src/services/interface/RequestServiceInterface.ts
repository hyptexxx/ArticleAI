import AnalyseResponse from '@/models/AnalyseResponse.js'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import ArticleFileMeta from '@/models/ArticleFile/ArticleFileMeta'

export default interface RequestServiceInterface{
  sendAndAnalyse(articleFile: ArticleFile): Promise<AnalyseResponse[]>;
  sendTextAndAnalyse(articleFile: ArticleFileMeta): Promise<AnalyseResponse[]>;
}
