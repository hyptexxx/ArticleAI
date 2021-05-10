<template lang="pug">
  div
    q-stepper(v-model='step' ref='stepper'
      done-color='green'
      active-color='purple'
      inactive-color='indigo'
      id='publication-analyse-stepper')
      q-step(:name=1 title='Сохранение публикации' icon='settings' :done='step > 1')
      q-step(:name=2 title='Подготовка публикации к анализу' icon='settings' :done='step > 2')
      q-step(:name=3 title='Анализ текста' icon='settings' :done='step > 3')
      q-step(:name=4 title='Фильтрация результатов' icon='settings' :done='step > 4')
      q-step(:name=5 title='Формирование рекомендаций' icon='settings' :done='step > 5')
      q-step(:name=6 title='Завершение' icon='settings' :done='step > 6')
      template(v-slot:message)
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 1')
          | Сохраняем вашу публикацию...
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 2')
          | Подгатавливаем текст публикации для более точного анализа...
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 3')
          | Анализируем текст публикации...
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 4')
          | Фильтруем результаты анализа...
        q-banner.bg-indigo.text-white.q-px-lg(v-if='step === 5')
          | Формируем рекомендации, для повышения актуальности публикации...
        q-banner.bg-green.text-white.q-px-lg(v-if='step === 6')
          | Готово!
          | Просмотрите рекоммендации, оцените их точность и полезность.
    q-card-section
      q-btn.bg-indigo.text-white(flat='' label='Результаты анализа' style='float:right' v-if='step === 6 && this.clientConfig && this.clientConfig.clientUiConfig && this.clientConfig.clientUiConfig.withAnalyseInfoDisplay' @click='dialog = true')
      q-btn.bg-indigo.text-white(flat='' label='Получить сертификат' style='float:right; margin-right: 10px' v-if='step === 6 && this.clientConfig && this.clientConfig.clientUiConfig && this.clientConfig.clientUiConfig.withCertificateGeneration' @click='getCertificate')
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
        RecommendationSettings(v-if='step === 6' :dialog.sync='dialog')
</template>

<script lang="ts">
import { Component, Mixins, PropSync, Watch } from 'vue-property-decorator'
import InnerRecommendation from 'components/recomendation/InnerRecommendation.vue'
import RecommendationSettings from 'components/admin/RecommendationSettings.vue'
import ArticleFile from 'src/models/ArticleFile/ArticleFile'
import AnalyseResponse from 'src/models/AnalyseResponse'
import RequestService from 'src/services/implementation/RequestService'
import { Recommendations } from 'src/models/Recommendation'
import { QStepper } from 'quasar'
import RecommendationStore from 'src/store/RecommendationStore'
import SocketStore from 'src/store/SocketStore'
import LoginStore from 'src/store/LoginStore'

@Component({
  components: {
    InnerRecommendation,
    RecommendationSettings
  }
})
export default class Recommendation extends Mixins(RequestService, RecommendationStore, SocketStore, LoginStore) {
  @PropSync('files') sFiles!: File[]
  private currentStatusText = 'Анализ публикации...'
  private currentStatusTextDescription = 'Пожалуйста дождитесь окончания анализа вашей публикации.'
  private step = 1
  private dialog = false

  private articleFile: ArticleFile = {
    files: null,
    meta: {
      language: 'ru',
      maxNgramSize: 3,
      deduplicationThresold: 1,
      deduplicationAlgo: 'leve',
      windowSize: 1,
      numberOfKeywords: 13,
      text: ''
    }
  }

  data: AnalyseResponse = {
    yakeResponse: [{
      ngram: '',
      score: 0
    }],
    generatedArticleId: 0
  }

  recomendation: Recommendations = {
    payload: [],
    actuality: 0,
    hasTags: [''],
    topSubjects: [],
    yakeResponse: [],
    classesActuality: [],
    keywordActuality: []
  }

  @Watch('step')
  private stepWatcher (): void {
    if (this.step === 6) {
      this.currentStatusText = 'Просмотрите полученные рекоммендации.'
      this.currentStatusTextDescription = 'Пожалуйста выберите Принять / Отклонить. Это необходимо для сбора статистики для улучшения качества и точности рекомендаций в дальнейшем.'
    }
  }

  @Watch('getMessage')
  private socketMessageReceive (): void {
    console.log(this.step)
    console.log(this.getMessage as unknown as number);
    // eslint-disable-next-line @typescript-eslint/no-unsafe-call
    (this.$refs.stepper as QStepper).goTo(++(this.getMessage as unknown as number))
  }

  private async mounted (): Promise<void> {
    if (this.isConnected) {
      if (this.articleFile) {
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
        this.articleFile.files = this.sFiles
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
        this.recomendation = await this.sendAndAnalyse(this.articleFile)
        this.setRecommendation(this.recomendation)
      }
    } else {
      this.$q.notify({
        type: 'negative',
        progress: true,
        message: 'Подключение к сервисам платформы не удалось. Попробуйте обновить страницу'
      })
    }
  }

  private getCertificate (): void {
    window.location.replace((process.env.API_BASE_URL as unknown as string) + 'document')
  }
}
</script>
