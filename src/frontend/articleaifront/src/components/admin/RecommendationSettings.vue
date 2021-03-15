<template lang="pug">
  q-dialog(v-model='sDialog' persistent='' :maximized='maximizedToggle' transition-show='slide-up' transition-hide='slide-down')
    q-card.text-white
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
        q-table(
          title='Класс -> Актуальность'
          :data='this.sRecommendation.classesActuality'
          :separator='separator'
          virtual-scroll
          :columns='b'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='name')
        q-table(
          title='Ключевые слова -> Классы -> актуальность класса'
          :data='this.sRecommendation.classKeywordPairs'
          :separator='separator'
          virtual-scroll
          :columns='a'
          pagination.sync="pagination"
          :rows-per-page-options="[0]"
          row-key='keyword')
</template>

<script lang="ts">
import { Component, PropSync, Vue, Watch } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'

@Component
export default class RecommendationSettings extends Vue {
  @PropSync('dialog', { type: Boolean }) sDialog!: boolean
  @PropSync('recommendation') sRecommendation: Recommendations = {
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

  @Watch('sRecommendation')
  qwe (): void {
    console.log(this.sRecommendation)
  }

  private maximizedToggle = false

  a = [
    { name: 'cluster', label: 'Класс', field: 'cluster', align: 'center', style: 'width: 10px' },
    { name: 'keyword', label: 'Ключевая фраза', field: 'keyword', align: 'center', style: 'width: 10px' },
    { name: 'actuality', label: 'Актаульность', field: 'actuality', align: 'center', style: 'width: 10px' }
  ]

  b = [
    { name: 'name', label: 'Класс', field: 'name', align: 'center', style: 'width: 10px' },
    { name: 'classActuality', label: 'Актуальность', field: 'classActuality', align: 'center', style: 'width: 10px' }
  ]
}

</script>
