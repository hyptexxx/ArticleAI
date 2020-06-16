export default interface ArticleFile {
  file: File | null;
  meta: ArticleFileMeta;
}

interface ArticleFileMeta {
  language: string;
  maxNgramSize: number;
  numberOfKeywords: number;
  text: string;
}
