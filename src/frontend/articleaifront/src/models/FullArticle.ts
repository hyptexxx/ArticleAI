import AnalyseResponse from '../models/AnalyseResponse'
import ArticleFileMeta from '../models/ArticleFile/ArticleFileMeta'

export interface FullArticle{
  analyseResponse: AnalyseResponse[];
  articleYake: ArticleFileMeta;
}

export interface Facultet {
  facultetId: number
  facultetText: string
}
export interface FullStudentResultInfo {
  score: number
  realtime: Date
}
