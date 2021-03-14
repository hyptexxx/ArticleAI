<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Для начала анализа загрузите вашу публикацию.
              .text-subtitle2.text-black Разрешённые типы документов:
              .text-subtitle2.text-black .doc .docx .pdf
              q-file(filled='' bottom-slots='' multiple v-model='files' label='Публикация' counter='' max-files='1')
                template(v-slot:before='')
                  q-icon(name='folder_open')
                template(v-slot:hint='')
                  | Выберите публикацию для анализа
                template(v-slot:append='')
                  q-btn(round='' dense='' flat='' icon='add' @click.stop='')
            q-card-section
              q-btn.bg-indigo.text-white(flat='' label='Анализ' @click.stop='visible=true')
                q-tooltip(content-class='bg-indigo' content-style="font-size: 16px" :offset='[10, 10]')
                  | Анализировать публикацию и получить рекомендации
            q-slide-transition
              div(v-show='visible')
                q-separator(dark='' inset='')
                Recommendation

</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from 'src/services/implementation/RequestService'
import ArticleFile from 'src/models/ArticleFile/ArticleFile'
import AnalyseResponse, { YakeResponse } from 'src/models/AnalyseResponse'
import { Class } from 'src/models/Class'
import { Recommendations } from 'src/models/Recommendation'
import { NlpResponse } from 'src/models/NlpResponse'
import Recommendation from 'components/recomendation/Recommendation.vue'

@Component({
  components: {
    Recommendation
  }
})
export default class Main extends Mixins(RequestService) {
  private visible = false
  private files: File[] | null = null
  private separator = 'cell'
  private pagination = { rowsPerPage: 0 }
  private step = 1
  nlpResponse: NlpResponse[] = []

  private articleId: number | null = null
  private columns = [{
    name: 'ngram',
    required: true,
    label: 'Ключевое слово',
    align: 'center'
  },
  { name: 'score', label: 'Значение важности', field: 'Значение важности', align: 'center', style: 'width: 10px' }]

  original: AnalyseResponse[] = [{
    yakeResponse: [{
      ngram: '',
      score: 0
    }],
    generatedArticleId: 0
  }]

  data: AnalyseResponse = {
    yakeResponse: [{
      ngram: '',
      score: 0
    }],
    generatedArticleId: 0
  }

  recomendation: Recommendations = {
    actuality: 0,
    classKeywordPairMax: {
      actuality: 0,
      cluster: '',
      keyword: ''
    },
    classKeywordPairMin: {
      actuality: 0,
      cluster: '',
      keyword: ''
    },
    classKeywordPairs: [],
    classesActuality: [],
    keywordClassMax: ''
  }

  a = [
    { name: 'cluster', label: 'Класс', field: 'cluster', align: 'center', style: 'width: 10px' },
    { name: 'keyword', label: 'Ключевая фраза', field: 'keyword', align: 'center', style: 'width: 10px' },
    { name: 'actuality', label: 'Актаульность', field: 'actuality', align: 'center', style: 'width: 10px' }
  ]

  b = [
    { name: 'name', label: 'Класс', field: 'name', align: 'center', style: 'width: 10px' },
    { name: 'classActuality', label: 'Актуальность', field: 'classActuality', align: 'center', style: 'width: 10px' }
  ]

  private classColumns = [
    { name: 'className', label: 'Имя класса', field: 'className', align: 'center', style: 'width: 10px' },
    { name: 'actuality', label: 'Вес класса', field: 'actuality', align: 'center', style: 'width: 10px' },
    { name: 'keywordText', label: 'Ключевое слово', field: 'keywordText', align: 'center', style: 'width: 10px' }]

  classes: Class[] = []

  private articleFile: ArticleFile = {
    files: null,
    meta: {
      language: 'ru',
      maxNgramSize: 3,
      deduplicationThresold: 1,
      deduplicationAlgo: 'leve',
      windowSize: 1,
      numberOfKeywords: 10,
      text: ''
    }
  }

  private removeRow (click: MouseEvent): void {
    for (let i = 0; i < document.getElementsByClassName('remove-row-btn').length; i++) {
      if (document.getElementsByClassName('remove-row-btn')[i] === click.currentTarget) {
        this.data.yakeResponse.splice(i, 1)
      }
    }
  }

  private loading = false;

  private addRow (): void {
    this.loading = true
    const index = this.data.yakeResponse.length + 1,
      row = this.original
    const addRow = { ...row }
    this.data.yakeResponse = [...this.data.yakeResponse.slice(0, index), addRow, ...this.data.yakeResponse.slice(index)] as YakeResponse[]
    this.loading = false
  }

  private async nextHandler (): Promise<void> {
    this.loading = true
    switch (this.step) {
      case 2:
        this.data.yakeResponse = this.data.yakeResponse.splice(0, this.data.yakeResponse.length)
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-call,  @typescript-eslint/no-unsafe-member-access
        this.nlpResponse = this.nlpResponse.splice(0, this.nlpResponse.length)
        if (this.articleFile) {
          this.articleFile.files = this.files
          this.data = await this.sendAndAnalyse(this.articleFile)
          if (this.data && this.data.yakeResponse.length) {
            // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-call, @typescript-eslint/no-unsafe-member-access
            this.recomendation = await this.sendToNlp(this.data.yakeResponse)
          }
        }
        break
    }
    this.loading = false
  }

  private async saveResult (): Promise<void> {
    if (this.data && this.articleFile) {
      const articleId = await this.saveResultRequest(this.data, this.articleFile, this.classes)
      if (articleId) {
        this.articleId = articleId
      }
    }
  }
}
</script>
