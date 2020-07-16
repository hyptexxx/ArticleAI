import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFileMeta from '@/models/ArticleFile/ArticleFileMeta'

export default interface FullArticle{
  analyseResponse: AnalyseResponse[];
  articleYake: ArticleFileMeta;
}
