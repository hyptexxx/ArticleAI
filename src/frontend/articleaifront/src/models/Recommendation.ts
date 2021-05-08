export interface Recommendations {
  actuality: number;
  hasTags: string[];
  payload: NlpPayload[]
  topSubjects: TopSubject[]
}

export interface ClassKeywordPair{
  cluster: string
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
