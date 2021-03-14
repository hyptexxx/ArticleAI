<template lang="pug">
  .q-pa-md.row.items-start.q-gutter-md
    q-card.recommendation-card-width
      q-card-section.bg-indigo.text-white
        .text-h6 Общие рекоммендации по увеличению актуальности
        .text-subtitle2 Предложения для правок в тексте
      q-card-section
        q-chip(square='' text-color='white' class='bg-indigo-5' icon-right='edit')
          | Внесите предложенные правки в ключевые слова
      q-card-section
        .text-subtitle2 Не вносите в текст следующие ключевые слова:
        q-chip(square='' text-color='white' color='red' icon-right='remove')
          | {{this.recommendation.classKeywordPairMin.keyword}}
      q-separator(inset='')
      q-card-actions
        q-btn.bg-green.text-white Принять
        q-btn.bg-red-4.text-white Отклонить

    q-card.recommendation-card-width
      q-card-section.bg-indigo.text-white
        .text-h6 Рекоммендации по редактированию ключевых слов
        .text-subtitle2 Ключевые слова
      q-card-section
        q-chip(square='' text-color='white' color='green' icon-right='add')
          | {{this.recommendation.classKeywordPairMax.keyword}}
      q-separator(inset='')
      q-card-actions
        q-btn.bg-green.text-white Принять
        q-btn.bg-red-4.text-white Отклонить

    q-card.recommendation-card-width
      q-card-section.bg-indigo.text-white
        .text-h6 Предполагаемые темы по ключевым словам текста статьи
        .text-subtitle2 Ключевые слова
      q-card-section
        q-chip(square='' text-color='white' color='green' v-for="pair in this.recommendation.classKeywordPairs")
          | {{pair.cluster}}
      q-separator(dark='')

    q-card.recommendation-card-width
      q-card-section.bg-indigo.text-white
        .text-h6 Общая статистика
        .text-subtitle2 Результаты анализа
      q-card-section
        q-chip(square='' v-if="this.recommendation.actuality > 50" color='green' text-color='white' icon-right='star')
          | Актуальность составляет {{this.recommendation.actuality}}%
        q-chip(square='' v-else color='yellow' text-color='black' icon-right='star')
          | Актуальность составляет {{this.recommendation.actuality}}%
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'

@Component
export default class InnerRecommendation extends Vue {
  private recommendation: Recommendations = {
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
}
</script>
