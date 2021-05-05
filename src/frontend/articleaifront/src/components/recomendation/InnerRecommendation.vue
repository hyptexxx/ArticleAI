<template lang="pug">
  .q-pa-md.row.items-start.q-gutter-md
    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Общие рекоммендации по увеличению актуальности
        .text-subtitle2 Предложения для правок в тексте
      q-card-section
        q-chip(square='' text-color='white' class='bg-indigo-5' icon-right='edit')
          | Внесите предложенные правки в ключевые слова
      q-card-section
        .text-subtitle2 Не вносите в текст следующие ключевые слова:
        q-chip(square='' icon-right='remove' text-color='white' class='bg-red-5' key='i' v-for="pair in this.srecommendation.classKeywordPairMin")
          | {{pair.keyword}}
      q-separator(inset='')
      q-card-actions
        q-btn.text-green-7(flat) Принять
        q-btn.text-red-7(flat) Отклонить

    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Рекоммендации по редактированию ключевых слов
        .text-subtitle2 Ключевые слова
      q-card-section
        q-chip(square='' icon-right='add' text-color='white' class='bg-green-5' key='i' v-for="pair in this.srecommendation.classKeywordPairMax")
          | {{pair.keyword}}
      q-separator(inset='')
      q-card-actions
        q-btn.text-green-7(flat) Принять
        q-btn.text-red-7(flat) Отклонить

    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Предполагаемые темы по ключевым словам текста статьи
        .text-subtitle2 Ключевые слова
      q-card-section
        q-chip(square='' text-color='white' color='green' key='i' v-for="pair in this.srecommendation.classKeywordPairs")
          | {{pair.cluster}}
      q-separator(dark='')

    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Общая статистика
        .text-subtitle2 Результаты анализа
      q-card-section
        q-chip(square='' v-if="this.srecommendation.actuality > 50" class='bg-green-5' text-color='white' icon-right='star')
          | Актуальность составляет {{this.srecommendation.actuality}}%
        q-chip(square='' v-else color='yellow' text-color='black' icon-right='star')
          | Актуальность составляет {{this.srecommendation.actuality}}%
</template>

<script lang="ts">
import { Component, Mixins, Watch } from 'vue-property-decorator'
import { ClassKeywordPair, Recommendations } from 'src/models/Recommendation'
import RecommendationStore from 'src/store/RecommendationStore'

@Component
export default class InnerRecommendation extends Mixins(RecommendationStore) {
  private srecommendation: Recommendations = {
    payload: [],
    actuality: 0,
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
    classKeywordPairs: [],
    classesActuality: [],
    keywordClassMax: ''
  }

  @Watch('recommendations')
  recommendationWatcher (): void {
    this.srecommendation = this.recommendations
  }

  private removeDuplicates (): ClassKeywordPair[] {
    return this.srecommendation.classKeywordPairs.filter((value, index, self) => {
      return index === self.findIndex((t) => (
        t.cluster === value.cluster
      ))
    })
  }
}
</script>
