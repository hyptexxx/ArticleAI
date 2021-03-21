export interface Recommendations {
  keywordClassMax: string;
  actuality: number;
  classKeywordPairs: ClassKeywordPair[];
  classesActuality: KeywordClass[];
  classKeywordPairMin: ClassKeywordPair;
  classKeywordPairMax: ClassKeywordPair;
  payload: NlpPayload[]
}

export interface ClassKeywordPair{
  cluster: string
  keyword: string
  actuality: number
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
