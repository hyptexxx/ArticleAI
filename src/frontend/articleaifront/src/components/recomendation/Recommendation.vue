<template lang="pug">
  div
    RecommendationSettings(:dialog.sync='dialog')
    q-stepper(v-model='step' ref='stepper' done-color='green' active-color='purple' inactive-color='indigo' id='publication-analyse-stepper' @click="$refs.stepper.next()")
      q-step(:name='1' title='Сохранение публикации' icon='settings' :done='step > 1')
      q-step(:name='2' title='Подготовка публикации к анализу' icon='settings' :done='step > 2')
      q-step(:name='3' title='Анализ текста' icon='settings' :done='step > 3')
      q-step(:name='4' title='Фильтрация результатов' icon='settings' :done='step > 4')
      q-step(:name='5' title='Формирование рекомендаций' icon='settings' :done='step > 5')
      q-step(:name='6' title='Завершение' icon='settings' :done='step > 6')
      template(v-slot:message='')
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 1')
          | Сохраняем вашу публикацию...
        q-banner.bg-indigo.text-white.q-px-lg(v-else-if='step === 2')
          | Подгатавливаем текст публикации для более точного анализа...
        q-banner.bg-indigo.text-white.q-px-lg(v-else-if='step === 3')
          | Анализируем текст публикации...
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 4')
          | Фильтруем результаты анализа...
        q-banner.bg-indigo.text-white.q-px-lg(v-else-if='step === 5')
          | Формируем рекомендации, для повышения актуальности публикации...
        q-banner.bg-green.text-white.q-px-lg(v-else-if='step === 6')
          | Готово!
          | Просмотрите рекоммендации, оцените их точность и полезность.
    q-card-section
      q-btn(icon='settings' round='' style='float:right' @click='dialog = true')
      .text-h6.text-black {{currentStatusText}}
      .text-subtitle2.text-black {{currentStatusTextDescription}}
      div.q-gutter-md.row
        q-intersection.example-item(v-for='index in 3' :key='index' transition='scale' v-if='step !== 6')
          q-card(style='width: 400px')
            q-item
              q-item-section
                q-item-label
                  q-skeleton(type='text')
                q-item-label(caption='')
                  q-skeleton(type='text')
            q-skeleton(height='100px' square='')
            q-card-actions.q-gutter-md(align='right')
              q-skeleton(type='QBtn')
              q-skeleton(type='QBtn')
        InnerRecommendation(v-if='step === 6')
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import InnerRecommendation from 'components/recomendation/InnerRecommendation.vue'
import RecommendationSettings from 'components/admin/RecommendationSettings.vue'

@Component({
  components: {
    InnerRecommendation,
    RecommendationSettings
  }
})
export default class Recommendation extends Vue {
  private currentStatusText = 'Анализ публикации...'
  private currentStatusTextDescription = 'Пожалуйста дождитесь окончания анализа вашей публикации.'
  private step = 1
  private dialog = false

  @Watch('step')
  private stepWatcher (): void {
    if (this.step === 6) {
      this.currentStatusText = 'Просмотрите полученные рекоммендации.'
      this.currentStatusTextDescription = 'Пожалуйста выберите Принять / Отклонить. Это необходимо для сбора статистики для улучшения качества и точности рекомендаций в дальнейшем.'
    }
  }
}
</script>
