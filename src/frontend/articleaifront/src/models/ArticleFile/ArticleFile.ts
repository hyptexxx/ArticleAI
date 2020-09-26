import ArticleFileMeta from '../../models/ArticleFile/ArticleFileMeta'

export default interface ArticleFile {
  file: File | null;
  meta: ArticleFileMeta;
}

