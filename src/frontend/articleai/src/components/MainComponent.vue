<template lang="pug">
  div(class="main-component")
    notifications(group="foo", position="top right", ignoreDuplicates=true, classes="custom-player-alert ")
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
      span
       | {{yandexResponse}}
      div(v-for="actualityClass in classes")
        | {{actualityClass.className}}   {{actualityClass.keywordText}}   {{actualityClass.classWeight}}
      button(@click="sendRequest", class="send-request-button" :disabled="!AnalyseResponse") Отправить в yake
      button(@click="addNewField", class="send-request-button") Добавить ключевое слово
      button(@click="saveResult", class="send-request-button") Сохранить результат
      button(@click="actualityAnalyse", class="send-request-button") Определить классы
      button(@click="sendRequestToYandex", class="send-request-button") test yandex request
      div(v-for="response in AnalyseResponse")
        input(v-model="response.ngram")
        input(v-model="response.score", type="number")
        button(@click="deleteResult(response)") Удалить
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'
import AnalyseResponse from '@/models/AnalyseResponse'
import ArticleFile from '@/models/ArticleFile/ArticleFile'
import { ArticleMutationModule } from '@/store/ArticleMutationModule'
import ValidationYakeService from '@/services/implementation/ValidationYakeService'
import FullArticle from '@/models/FullArticle'
import ArticleFileMeta from '@/models/ArticleFile/ArticleFileMeta'
import { Class } from '@/models/Class'

@Component
export default class MainComponent extends Mixins(RequestService, ArticleMutationModule, ValidationYakeService) {
  private useFileToTextAnalyse = false
  private AnalyseResponse: AnalyseResponse[] = []
  private classes: Class[] = []
  private yandexResponse = ''
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

  private async sendRequestToYandex (): Promise<void> {
    this.yandexResponse = await this.sendRequestToYandexFromServer()
  }

  private async actualityAnalyse (): Promise<void> {
    this.classes = await this.actualityAnalyseRequest(this.AnalyseResponse)
  }

  private deleteResult (elemToDelete: AnalyseResponse): void {
    this.AnalyseResponse.splice(this.AnalyseResponse.indexOf(elemToDelete), 1)
  }

  private loadResult (): void {
    const results = this.loadSavedResults(1) as unknown as FullArticle
    this.AnalyseResponse = results.analyseResponse
    if (results.analyseResponse.length > 0) {
      this.articleFile = {
        file: null,
        meta: results.articleYake as ArticleFileMeta
      }
    } else {
      this.validationErrorsFromArticle.push('Нет сохранённых результатов')
    }
  }

  private addNewField (): void {
    this.AnalyseResponse.push({
      ngram: '',
      score: 0
    })
  }

  private saveResult (): void {
    if (this.AnalyseResponse && this.articleFile) {
      if (this.getValidationErrorsFromArticle(this.articleFile).length > 0) {
        this.validationErrorsFromArticle = this.getValidationErrorsFromArticle(this.articleFile)
      } else {
        this.validationErrorsFromArticle = ['']
        this.saveResultRequest(this.AnalyseResponse, this.articleFile, this.classes)
      }
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
