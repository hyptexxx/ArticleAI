<template lang="pug">
  q-dialog(v-model='sDialog' persistent='' :maximized='maximizedToggle' transition-show='slide-up' transition-hide='slide-down')
    q-card.text-white.bg-indigo
      q-bar
        q-space
        q-btn(dense='' flat='' icon='minimize' @click='maximizedToggle = false' :disable='!maximizedToggle')
          q-tooltip(v-if='maximizedToggle' content-class='bg-white text-primary') Minimize
        q-btn(dense='' flat='' icon='crop_square' @click='maximizedToggle = true' :disable='maximizedToggle')
          q-tooltip(v-if='!maximizedToggle' content-class='bg-white text-primary') Maximize
        q-btn(dense='' flat='' icon='close' v-close-popup='')
          q-tooltip(content-class='bg-white text-primary') Close
      q-card-section
        .text-h6 Техническая информация
      q-card-section.q-gutter-md.row
        q-table(
          title='Класс - актуальность'
          :data='this.srecommendation.classesActuality'
          :separator='separator'
          virtual-scroll
          style='width: 500px'
          :columns='classActuality'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='name')
        q-table(
          title='Ключевые слова, Классы, актуальность класса'
          :data='this.srecommendation.classKeywordPairs'
          :separator='separator'
          virtual-scroll
          style='width: 500px'
          :columns='classKeyActuality'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='keyword')
        q-table(
          title='Результаты анализа текста'
          :data='this.yakeResponse.yakeResponse'
          :separator='separator'
          virtual-scroll
          style='width: 700px'
          :columns='yakeColumns'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='ngram')
        q-table(
          title='Результаты фильтрации'
          :data='this.srecommendation.payload'
          :separator='separator'
          virtual-scroll
          style='width: 700px'
          :columns='nlpPayloadColumns'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='ngram')

</template>

<script lang="ts">
import { Component, Mixins, PropSync, Watch } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'
import RecommendationStore from 'src/store/RecommendationStore'
import AnalyseResponse from 'src/models/AnalyseResponse'

@Component
export default class RecommendationSettings extends Mixins(RecommendationStore) {
  private separator = 'cell'

  @PropSync('dialog', { type: Boolean }) sDialog!: true
  private srecommendation: Recommendations = {
    actuality: 0,
    payload: [{
      avg: 0,
      isGood: 0,
      ngram: '',
      value: 0
    }],
    classKeywordPairMax: [{
      actuality: 0,
      cluster: '',
      keyword: ''
    }],
    classKeywordPairMin: [{
      actuality: 0,
      cluster: '',
      keyword: ''
    }],
    classKeywordPairs: [{
      actuality: 0,
      cluster: '',
      keyword: ''
    }],
    classesActuality: [{
      name: '',
      embedding: '',
      classActuality: 0
    }],
    keywordClassMax: ''
  }

  yakeResponse: AnalyseResponse = {
    yakeResponse: [{
      ngram: '',
      score: 0
    }],
    generatedArticleId: 0
  }

  private mounted (): void {
    this.yakeResponse = this.yakeResponseStore
  }

  @Watch('recommendations')
  recommendationWatcher (): void {
    this.srecommendation = this.recommendations
  }

  private maximizedToggle = true

  classKeyActuality = [
    { name: 'keyword', label: 'Ключевая фраза', field: 'keyword', align: 'center', style: 'width: 10px' },
    { name: 'actuality', label: 'Актаульность', field: 'actuality', align: 'center', style: 'width: 10px' }
  ]

  classActuality = [
    { name: 'name', label: 'Класс', field: 'name', align: 'center', style: 'width: 10px' },
    { name: 'classActuality', label: 'Актуальность', field: 'classActuality', align: 'center', style: 'width: 10px' }
  ]

  nlpPayloadColumns = [
    { name: 'ngram', label: 'Ключевая фраза', field: 'ngram', align: 'center', style: 'width: 10px' },
    { name: 'avg', label: 'Среднее', field: 'avg', align: 'center', style: 'width: 10px' },
    { name: 'value', label: 'Степень уверенности', field: 'value', align: 'center', style: 'width: 10px' },
    { name: 'isGood', label: 'Прошёл фильтр', field: 'isGood', align: 'center', style: 'width: 10px' }
  ]

  yakeColumns = [
    { name: 'ngram', label: 'Ключевая фраза', field: 'ngram', align: 'center', style: 'width: 10px' },
    { name: 'score', label: 'Важность', field: 'score', align: 'center', style: 'width: 10px' }
  ]
}

</script>
