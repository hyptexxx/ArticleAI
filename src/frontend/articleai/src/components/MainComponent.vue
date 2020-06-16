<template lang="pug">
  div(style="display: flex; flex-direction: column;")
    input(v-model="articleFile.meta.language", type="text")
    input(v-model="articleFile.meta.maxNgramSize", type="number")
    input(v-model="articleFile.meta.numberOfKeywords", type="number")
    input(v-model="articleFile.meta.text", type="text")
    button(@click="sendRequest", class="send-request-button")
    file
    span(v-for="response in AnalyseResponse" :key="response.score" v-text="response")
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import { ArticleMutationModule } from '@/store/ArticleMutationModule'

@Component
export default class MainComponent extends Mixins(RequestService, ArticleMutationModule) {
  private articleFile: ArticleFile = {
    file: null,
    meta: {
      language: '',
      maxNgramSize: 0,
      numberOfKeywords: 0,
      text: ''
    }
  }

  private AnalyseResponse: AnalyseResponse[] = []
  private async sendRequest (): Promise<void> {
    if (this.articleFile) {
      this.articleFile.file = this.ArticleFile
      this.AnalyseResponse = await this.sendAndAnalyse(this.articleFile)
    }
  }
}
</script>
