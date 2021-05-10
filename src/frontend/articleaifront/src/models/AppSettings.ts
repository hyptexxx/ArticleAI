export interface AppSettings{
  yakeParams: YakeParams
  coefficient: number
}

export default interface YakeParams {
  language: string
  // eslint-disable-next-line camelcase
  max_ngram_size: number
  // eslint-disable-next-line camelcase
  deduplication_thresold: number
  // eslint-disable-next-line camelcase
  deduplication_algo: string
  windowSize: number
  // eslint-disable-next-line camelcase
  number_of_keywords: number
}
