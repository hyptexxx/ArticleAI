import { YakeResponse } from 'src/models/AnalyseResponse'

export interface Recommendations {
  actuality: number;
  hasTags: string[];
  payload: NlpPayload[]
  topSubjects: TopSubject[]
  yakeResponse: YakeResponse[]
  classesActuality: KeywordClass[]
  keywordActuality: KeywordClass[]
}

export interface KeywordActuality{
  keyword: string
  actuality: number
}

export interface TopSubject{
  name: string
  percent: number
}

export interface KeywordClass{
  name: string
  embedding: string
  classActuality: number
}

export interface NlpPayload{
  avg: number
  isGood: number
  ngram: string
  value: number
}
