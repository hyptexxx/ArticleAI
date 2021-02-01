export default interface AnalyseResponse{
  generatedArticleId:number
  yakeResponse: YakeResponse[]

}

export interface YakeResponse{
  ngram: string;
  score: number;
}
