import AnalyseResponse from '../../models/AnalyseResponse.js'
import ArticleFile from '../../models/ArticleFile/ArticleFile'
import ArticleFileMeta from '../../models/ArticleFile/ArticleFileMeta'
import { FullArticle } from '../../models/FullArticle'
import { Class } from 'src/models/Class'
import { ClassActuality } from 'src/models/ClassActuality'

export default interface RequestServiceInterface{
  sendAndAnalyse(articleFile: ArticleFile): Promise<AnalyseResponse>;
  sendTextAndAnalyse(articleFile: ArticleFileMeta): Promise<AnalyseResponse[]>;
  saveResultRequest(analyseResponse: AnalyseResponse, articleFile: ArticleFile, classes: Class[]): void;
  loadSavedResults(yakeId: number): Promise<FullArticle>;
  getActualityRequest(classes: Class[]): Promise<ClassActuality[]>
}
