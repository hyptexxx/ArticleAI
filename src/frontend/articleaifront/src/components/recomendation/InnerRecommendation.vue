<template lang="pug">
  .q-pa-md.row.items-start.q-gutter-md
    q-card.recommendation-card-width
    q-card-section
      q-chip(square='' text-color='white' class='bg-indigo' key='i' v-for="pair in this.srecommendation.hasTags")
        | # {{pair}}
    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Предполагаемые темы по ключевым словам текста статьи
        .text-subtitle2 Измените текст вашей публикации, приближая его тематику к одной из предложеных
      q-card-section
        q-chip(square='' text-color='white' class='bg-indigo' key='i' v-for="pair in this.srecommendation.topSubjects")
          | {{pair.name}}
      q-separator(dark='')
      q-card-actions
        q-btn.text-green-7(flat) Принять
        q-btn.text-red-7(flat) Отклонить

    q-card.recommendation-card-width
      q-card-section.bg-grey-4.text-black
        .text-h6 Общая статистика
        .text-subtitle2 Результаты анализа
      q-card-section
        q-chip(square='' v-if="this.srecommendation.actuality > 50" class='bg-green-5' text-color='white' icon-right='star')
          | Актуальность составляет: {{this.srecommendation.actuality}}%
        q-chip(square='' v-else color='yellow' text-color='black' icon-right='star')
          | Актуальность составляет: {{this.srecommendation.actuality}}%
</template>

<script lang="ts">
import { Component, Mixins, Watch } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'
import RecommendationStore from 'src/store/RecommendationStore'

@Component
export default class InnerRecommendation extends Mixins(RecommendationStore) {
  private srecommendation: Recommendations = {
    payload: [],
    actuality: 0,
    hasTags: [''],
    topSubjects: []
  }

  @Watch('recommendations')
  recommendationWatcher (): void {
    this.srecommendation = this.recommendations
  }
}
</script>
