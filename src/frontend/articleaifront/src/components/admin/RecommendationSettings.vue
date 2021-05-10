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
          title='Результаты анализа текста в YAKE'
          :data='this.srecommendation.yakeResponse'
          :separator='separator'
          virtual-scroll
          style='width: 700px'
          :columns='yakeColumns'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='ngram')
        q-table(
          :title = 'this.nlpFilterAvg'
          :data='this.srecommendation.payload'
          :separator='separator'
          virtual-scroll
          style='width: 700px'
          :columns='nlpPayloadColumns'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='ngram')
        q-table(
          title='Актуальность ключевых словосочетаний'
          :data='this.srecommendation.keywordActuality'
          :separator='separator'
          virtual-scroll
          style='width: 700px'
          :columns='keywordActualityColumns'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='keyword')

</template>

<script lang="ts">
import { Component, Mixins, PropSync, Watch } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'
import RecommendationStore from 'src/store/RecommendationStore'

@Component
export default class RecommendationSettings extends Mixins(RecommendationStore) {
  private separator = 'cell'
  private nlpFilterAvg = '';

  @PropSync('dialog', { type: Boolean }) sDialog!: true
  private srecommendation: Recommendations = {
    payload: [],
    actuality: 0,
    hasTags: [''],
    topSubjects: [],
    yakeResponse: [],
    classesActuality: [],
    keywordActuality: []
  }

  @Watch('recommendations')
  recommendationWatcher (): void {
    this.srecommendation = this.recommendations
    this.nlpFilterAvg = 'Результаты фильтрации через NLP модуль. Среднее: ' + (this.srecommendation.payload[0].avg as unknown as string)
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
    { name: 'value', label: 'Степень уверенности', field: 'value', align: 'center', style: 'width: 10px' },
    { name: 'isGood', label: 'Прошёл фильтр', field: 'isGood', align: 'center', style: 'width: 10px' }
  ]

  yakeColumns = [
    { name: 'ngram', label: 'Ключевая фраза', field: 'ngram', align: 'center', style: 'width: 10px' },
    { name: 'score', label: 'Важность', field: 'score', align: 'center', style: 'width: 10px' }
  ]

  keywordActualityColumns = [
    { name: 'keyword', label: 'Ключевая фраза', field: 'keyword', align: 'center', style: 'width: 10px' },
    { name: 'classActuality', label: 'Актуальность', field: 'classActuality', align: 'center', style: 'width: 10px' }
  ]
}
</script>
