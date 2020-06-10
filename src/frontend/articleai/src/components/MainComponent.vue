<template lang="pug">
  div(style="display: flex; flex-direction: column;")
    input(v-model="docInfo.language", type="text")
    input(v-model="docInfo.maxNgramSize", type="number")
    input(v-model="docInfo.numberOfKeywords", type="number")
    input(v-model="docInfo.text", type="text")
    button(@click="sendRequest", style="width: 100px; height: 21px;")
    file
    span(v-text="response")
    span(v-for="response in YakeResponse" :key="response.score" v-text="response")
</template>

<script lang="ts">
import { Component, Mixins, Prop } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'
import YakeResponse from '@/models/YakeResponse'

@Component
export default class MainComponent extends Mixins(RequestService) {
  @Prop({ type: String, required: true })
  private response!: string;

  private docInfo = {
    language: '',
    maxNgramSize: 0,
    numberOfKeywords: 0,
    text: ''
  }

  private YakeResponse: YakeResponse[] = []

  private async sendRequest (): Promise<void> {
    this.YakeResponse = await this.sendRequestToYake(
      this.docInfo.language,
      this.docInfo.maxNgramSize,
      this.docInfo.numberOfKeywords,
      this.docInfo.text
    )
  }
}
</script>
