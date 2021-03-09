<template lang="pug">
  q-card(style='width: 100%; height: 100%; padding: 20px')
    q-card-section
      q-stepper(v-model='step' ref='stepper' color='primary' animated='')
        q-step(:name='1' title='Загрузите публикацию' icon='settings' :done='step > 1' style='min-height: 200px;')
          q-checkbox(v-model='isExperementalEnabled' label='Включить эксперементальный режим')
          q-file(filled='' bottom-slots='' multiple v-model='files' label='Публикация' counter='' max-files='1000')
            template(v-slot:before='')
              q-icon(name='folder_open')
            template(v-slot:hint='')
              | Выберите публикацию для анализа
            template(v-slot:append='')
              q-btn(round='' dense='' flat='' icon='add' @click.stop='')

        q-step(:name='2' caption='не обязательно' title='Определение параметров YAKE' icon='assignment' style='min-height: 200px;')
          .q-pa-md
            q-input(v-if='files' v-model='articleFile.meta.language' label='language')
            q-input(v-if='files' v-model='articleFile.meta.maxNgramSize' label='maxNgramSize')
            q-input(v-if='files' v-model='articleFile.meta.deduplicationThresold' label='deduplication_thresold')
            q-input(v-if='files' v-model='articleFile.meta.deduplicationAlgo' label='deduplication_algo')
            q-input(v-if='files' v-model='articleFile.meta.windowSize' label='windowSize')
            q-input(v-if='files' v-model='articleFile.meta.numberOfKeywords' label='numberOfKeywords')

        q-step(:name='3' title='Определение ключевых слов' icon='create_new_folder' :done='step > 2' style='min-height: 200px;')
          q-btn(color='green' :disable='loading' label='Добавить ключевое слово' @click='addRow')
          q-item.q-item__label--header
          q-table(
            title='Ключевые слова'
            :data='data.yakeResponse'
            :separator='separator'
            virtual-scroll
            :columns='columns'
            :loading="loading"
            pagination.sync="pagination"
            :rows-per-page-options="[0]"
            row-key='ngram')
            template(v-slot:header='props')
              q-tr(:props='props')
                q-th(auto-width='')
                  | Удалить ключевое слово
                q-th(v-for='col in props.cols' :key='col.name' :props='props')
                  | {{ col.label }}
            template(v-slot:body='props')
              q-tr(:props='props')
                q-td(auto-width='')
                  q-btn(size='sm' color='red' dense='' @click='removeRow' class='remove-row-btn' :icon="'remove'")

                q-td(key='ngram' :props='props')
                  | {{ props.row.ngram }}
                  q-popup-edit(v-model='props.row.ngram' title='Редактировать ключевое слово' buttons='')
                    q-input(type='text' v-model='props.row.ngram' dense='' autofocus='')

                q-td(key='score' :props='props')
                  | {{ props.row.score }}
                  q-popup-edit(v-model='props.row.score' title='Редактировать значение важности' buttons='')
                    q-input(type='number' v-model='props.row.score' dense='' autofocus='')
          q-separator
          q-table(
            title='Отфильтрованые ключевые слова'
            :data='nlpResponse'
            :separator='separator'
            virtual-scroll
            :columns='nlpResponseColumns'
            :loading="loading"
            pagination.sync="pagination"
            :rows-per-page-options="[0]"
            row-key='ngram',
            v-if='isExperementalEnabled')
        q-step(:name='4' title='Анализ актуальности' icon='assignment' style='min-height: 200px;')
          q-table(
            title='Класс-актуальность'
            :data='classes'
            :separator='separator'
            virtual-scroll
            :columns='classColumns'
            :loading="loading"
            pagination.sync="pagination"
            :rows-per-page-options="[0]"
            row-key='classId')

        q-step(:name='5' title='Рекомендации' icon='add_comment' style='min-height: 200px;')
          .q-pa-md.row.items-start.q-gutter-md
            q-card.my-card.bg-indigo-7.text-white
              q-card-section
                .text-h6 Общие рекомендации по увеличению актуальности
                .text-subtitle2 Предложения для правок в тексте
              q-card-section
                q-chip(square='' text-color='white' class='bg-indigo-5' icon-right='edit')
                  | Внесите предложенные правки в ключевые слова
              q-card-section
                .text-subtitle2 Не вносите в текст следующие ключевые слова:
                q-chip(square='' text-color='white' color='red' icon-right='remove')
                  | {{this.recomendation.classKeywordPairMin.keyword}}
              q-separator(dark='')
              q-card-actions
                q-btn(flat='') Принять
                q-btn(flat='') Отклонить

            q-card.my-card.bg-indigo-7.text-white
              q-card-section
                .text-h6 Рекомендации по редактированию ключевых слов
                .text-subtitle2 Ключевые слова
              q-card-section
                q-chip(square='' text-color='white' color='green' icon-right='add')
                  | {{this.recomendation.classKeywordPairMax.keyword}}
              q-separator(dark='')
              q-card-actions
                q-btn(flat='') Принять
                q-btn(flat='') Отклонить

            q-card.my-card.bg-indigo-7.text-white
              q-card-section
                .text-h6 Предполагаемые темы по ключевым словам текста статьи
                .text-subtitle2 Ключевые слова
              q-card-section
                q-chip(square='' text-color='white' color='green' v-for="pair in this.recomendation.classKeywordPairs")
                  | {{pair.cluster}}
              q-separator(dark='')

            q-card.my-card.bg-indigo-7.text-white
              q-card-section
                .text-h6 Общая статистика
                .text-subtitle2 Результаты анализа
              q-card-section
                q-chip(square='' v-if="this.recomendation.actuality > 50" color='green' text-color='white' icon-right='star')
                  | Актуальность составляет {{this.recomendation.actuality}}%
                q-chip(square='' v-else color='yellow' text-color='black' icon-right='star')
                  | Актуальность составляет {{this.recomendation.actuality}}%

        template(v-slot:navigation='')
          q-stepper-navigation
            q-btn(@click='$refs.stepper.next(), nextHandler()' color='primary' :label="step === 4 ? 'Получить рекомендации' : 'Следующий шаг'" v-if="files && step !== 5")
            q-btn.q-ml-sm(v-if='step > 1' flat='' color='primary' @click='$refs.stepper.previous()' label='Назад')
        template(v-slot:message='')
          q-banner.bg-purple-8.text-white.q-px-lg(v-if='step === 1')
            | Загрузите публикацию
          q-banner.bg-orange-8.text-white.q-px-lg(v-else-if='step === 2')
            | Определение параметров YAKE
          q-banner.bg-cyan-8.text-white.q-px-lg(v-else-if='step === 3')
            | Определение ключевых слов
          q-banner.bg-green-8.text-white.q-px-lg(v-else-if='step === 4')
            | Анализ актуальности
          q-banner.bg-blue-8.text-white.q-px-lg(v-else='')
            | Рекомендации
</template>

<script lang="ts">
import { Component, Mixins, Watch } from 'vue-property-decorator'
import RequestService from 'src/services/implementation/RequestService'
import ArticleFile from 'src/models/ArticleFile/ArticleFile'
import AnalyseResponse, { YakeResponse } from 'src/models/AnalyseResponse'
import { Class } from 'src/models/Class'
import { Recommendations } from 'src/models/Recommendation'
import { NlpResponse } from 'src/models/NlpResponse'

@Component
export default class ClassComponent extends Mixins(RequestService) {
  private files: File[] | null = null
  private separator = 'cell'
  private pagination = { rowsPerPage: 0 }
  private step = 1
  nlpResponse: NlpResponse[] = []

  private articleId: number | null = null
  private isExperementalEnabled = false
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

  @Watch('isExperementalEnabled')
  qwe () {
    console.log(this.isExperementalEnabled)
  }

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
    classKeywordPairs: [{
      actuality: 0,
      cluster: '',
      keyword: ''
    }],
    keywordClassMax: ''
  }

  nlpResponseColumns = [
    { name: 'ngram', label: 'Ключевое слово', field: 'ngram', align: 'center', style: 'width: 10px' },
    { name: 'value', label: 'Степень уверенности', field: 'value', align: 'center', style: 'width: 10px' },
    { name: 'isGood', label: 'Превышение порога', field: 'isGood', align: 'center', style: 'width: 10px' }
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
      case 3:
        break
      case 4:
        this.classes.splice(0, this.classes.length)
        if (this.data.generatedArticleId) {
          this.classes = await this.classesAnalyseRequest(this.data, this.data.generatedArticleId)
          if (this.classes.length === 0) {
            this.$q.notify({
              color: 'warning',
              message: 'Не удалось получить по актуальности',
              icon: 'report_problem',
              progress: true,
              position: 'bottom'
            })
          }
        }
        break
      case 5:
        this.data.yakeResponse = this.data.yakeResponse.splice(0, this.data.yakeResponse.length)
        // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-call,  @typescript-eslint/no-unsafe-member-access
        this.nlpResponse = this.nlpResponse.splice(0, this.nlpResponse.length)
        if (this.articleFile) {
          this.articleFile.files = this.files
          this.data = await this.sendAndAnalyse(this.articleFile)
          if (this.isExperementalEnabled) {
            if (this.data && this.data.yakeResponse.length) {
              // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment, @typescript-eslint/no-unsafe-call, @typescript-eslint/no-unsafe-member-access
              this.recomendation = await this.sendToNlp(this.data.yakeResponse)
              if (this.nlpResponse.length === 0) {
                this.$q.notify({
                  color: 'warning',
                  message: 'Не удалось отфильтровать данные по публикации',
                  icon: 'report_problem',
                  progress: true,
                  position: 'bottom'
                })
              }
            } else {
              this.$q.notify({
                color: 'warning',
                message: 'Не удалось получить данные по публикации',
                icon: 'report_problem',
                progress: true,
                position: 'bottom'
              })
            }
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
