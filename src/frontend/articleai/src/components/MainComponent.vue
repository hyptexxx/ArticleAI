<template lang="pug">
  div(class="main-component")
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
    button(@click="sendRequest", class="send-request-button") Отправить
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
        deduplicationThresold: 0,
        deduplicationAlgo: '',
        windowSize: 0,
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
