import Vue from 'vue'
import Component from 'vue-class-component'
import {
  State,
  Getter,
  Mutation
} from 'vuex-class'

@Component
export class ArticleMutationModule extends Vue {
  @Mutation('setArticleFile')
  public setArticleFile!: (file: File) => void

  @Getter('getArticleFile')
  public ArticleFile!: File
}
