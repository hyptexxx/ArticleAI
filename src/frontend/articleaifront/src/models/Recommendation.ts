export interface Recommendations {
  keywordClassMax: string;
  actuality: number;
  classKeywordPairs: ClassKeywordPair[];
}

export interface ClassKeywordPair{
  cluster: string
  keyword: string
}
