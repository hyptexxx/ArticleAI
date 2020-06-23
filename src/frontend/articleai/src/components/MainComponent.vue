<template lang="pug">
  div(class="main-component")
    .use-file-to-analyze-container
      label(for="useFileToTextAnalyse" class="label-use-file") Использовать текст файла
      input(v-model="useFileToTextAnalyse", type="checkbox", id="useFileToTextAnalyse")
    file
    div(class="yake-errors-container")
      span(v-for="error in validationErrorsFromArticle" class="yake-errors")
        | {{error}}
    .Yake-form
      label(for="language") language
      input(v-model="articleFile.meta.language", type="text" id="language")
      label(for="maxNgramSize") maxNgramSize
      input(v-model="articleFile.meta.maxNgramSize", type="number" id="maxNgramSize")
      label(for="deduplicationThresold") deduplication_thresold
      input(v-model="articleFile.meta.deduplicationThresold", type="number" id="deduplicationThresold")
      label(for="deduplicationAlgo") deduplication_algo
      input(v-model="articleFile.meta.deduplicationAlgo", type="text" id="deduplicationAlgo")
      label(for="windowSize") windowSize
      input(v-model="articleFile.meta.windowSize", type="number" id="windowSize")
      label(for="numberOfKeywords") numberOfKeywords
      input(v-model="articleFile.meta.numberOfKeywords", type="number" id="numberOfKeywords")
      label(for="text") text
      input(v-model="articleFile.meta.text", type="text" id="text")
      button(@click="sendRequest", class="send-request-button" :disabled="!AnalyseResponse") Отправить
      div(class="yake-response-container")
        span(v-for="response in AnalyseResponse")
          | {{response}}
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import { ArticleMutationModule } from '@/store/ArticleMutationModule'
import ValidationYakeService from '@/services/implementation/ValidationYakeService'

@Component
export default class MainComponent extends Mixins(RequestService, ArticleMutationModule, ValidationYakeService) {
  private useFileToTextAnalyse = false
  private AnalyseResponse: AnalyseResponse[] = []
  private validationErrorsFromArticle: [string] = ['']
  private articleFile: ArticleFile = {
    file: null,
    meta: {
      language: '',
      maxNgramSize: 0,
      deduplicationThresold: 0,
      deduplicationAlgo: '',
      windowSize: 0,
      numberOfKeywords: 0,
      text: ''
    }
  }

  private async sendRequest (): Promise<void> {
    if (this.getValidationErrorsFromArticle(this.articleFile).length > 0) {
      this.validationErrorsFromArticle = this.getValidationErrorsFromArticle(this.articleFile)
    } else {
      this.validationErrorsFromArticle = ['']
      if (this.useFileToTextAnalyse) {
        if (this.articleFile) {
          this.articleFile.file = this.ArticleFile
          this.AnalyseResponse = await this.sendAndAnalyse(this.articleFile)
        }
      } else {
        this.articleFile.file = this.ArticleFile
        this.AnalyseResponse = await this.sendTextAndAnalyse(this.articleFile.meta)
      }
    }
  }
}
</script>
