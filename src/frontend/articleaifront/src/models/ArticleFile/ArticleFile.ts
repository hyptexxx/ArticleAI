import ArticleFileMeta from '../../models/ArticleFile/ArticleFileMeta'

export default interface ArticleFile {
  files: File[] | null;
  meta: ArticleFileMeta;
}
