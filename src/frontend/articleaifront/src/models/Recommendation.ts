export interface Recommendations {
  keywordClassMax: string;
  actuality: number;
  classKeywordPairs: ClassKeywordPair[];
  classKeywordPairMin: ClassKeywordPair;
  classKeywordPairMax: ClassKeywordPair;
}

export interface ClassKeywordPair{
  cluster: string
  keyword: string
  actuality: number
}
