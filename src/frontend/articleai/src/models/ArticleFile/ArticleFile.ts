export default interface ArticleFile {
  file: File | null;
  meta: ArticleFileMeta;
}

interface ArticleFileMeta {
  language: string;
  maxNgramSize: number;
  deduplicationThresold: number
  deduplicationAlgo: string
  windowSize: number
  numberOfKeywords: number;
  text: string;
}
