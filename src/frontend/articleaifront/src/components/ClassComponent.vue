<template lang="pug">
  q-card(style='width: 100%; height: 100%; padding: 20px')
    q-card-section
      q-stepper(v-model='step' ref='stepper' color='primary' animated='')

        q-step(:name='1' title='Загрузите публикацию' icon='settings' :done='step > 1' style='min-height: 200px;')
          q-file(filled='' bottom-slots='' v-model='file' label='Публикация' counter='' max-files='12')
            template(v-slot:before='')
              q-icon(name='folder_open')
            template(v-slot:hint='')
              | Выберите публикацию для анализа
            template(v-slot:append='')
              q-btn(round='' dense='' flat='' icon='add' @click.stop='')

        q-step(:name='2' caption='не обязательно' title='Определение параметров YAKE' icon='assignment' style='min-height: 200px;')
          .q-pa-md
            q-input(v-model='articleFile.meta.language' label='language')
            q-input(v-model='articleFile.meta.maxNgramSize' label='maxNgramSize')
            q-input(v-model='articleFile.meta.deduplicationThresold' label='deduplication_thresold')
            q-input(v-model='articleFile.meta.deduplicationAlgo' label='deduplication_algo')
            q-input(v-model='articleFile.meta.windowSize' label='windowSize')
            q-input(v-model='articleFile.meta.numberOfKeywords' label='numberOfKeywords')

        q-step(:name='3' title='Определение ключевых слов' icon='create_new_folder' :done='step > 2' style='min-height: 200px;')
          q-btn(color='green' :disable='loading' label='Добавить ключевое слово' @click='addRow')
          q-btn(color='green' :disable='loading' label='Сохранить результат' @click='addRow')
          q-item.q-item__label--header
          q-table(
              title='Ключевые слова'
              :data='data'
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
                  q-btn(size='sm' color='red' dense='' @click='props.expand = !props.expand' :icon="'remove'")

                q-td(key='ngram' :props='props')
                  | {{ props.row.ngram }}
                  q-popup-edit(v-model='props.row.ngram' title='Редактировать ключевое слово' buttons='')
                    q-input(type='text' v-model='props.row.ngram' dense='' autofocus='')

                q-td(key='score' :props='props')
                  | {{ props.row.score }}
                  q-popup-edit(v-model='props.row.score' title='Редактировать значение важности' buttons='')
                    q-input(type='number' v-model='props.row.score' dense='' autofocus='')

        q-step(:name='4' title='Ad template' icon='assignment' style='min-height: 200px;')
          | Анализ актуальности.

        q-step(:name='5' title='Рекомендации' icon='add_comment' style='min-height: 200px;')
          | Try out different ad text to see what brings in the most customers, and learn how to
          | enhance your ads using features like ad extensions. If you run into any problems with
          | your ads, find out how to tell if they&apos;re running and how to resolve approval issues.

        template(v-slot:navigation='')
          q-stepper-navigation
            q-btn(@click='$refs.stepper.next(), nextHandler()' color='primary' :label="step === 4 ? 'Получить рекомендации' : 'Следующий шаг'")
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
import { Component, Mixins } from 'vue-property-decorator'
import { Facultet, FullStudentResultInfo } from 'src/models/FullArticle'
import RequestService from 'src/services/implementation/RequestService'
import ArticleFile from 'src/models/ArticleFile/ArticleFile'
import AnalyseResponse from 'src/models/AnalyseResponse'

@Component
export default class ClassComponent extends Mixins(RequestService) {
  private file: File | null = null
  private separator = 'cell'
  private pagination = { rowsPerPage: 0 }
  private step = 1
  private AnalyseResponse: AnalyseResponse[] = []

  private articleFile: ArticleFile = {
    file: null,
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

  private loading = false;

  private addRow (): void {
    this.loading = true
    const index = this.data.length + 1,
      row = this.original
    const addRow = { ...row }
    this.data = [...this.data.slice(0, index), addRow, ...this.data.slice(index)] as AnalyseResponse[]
    this.loading = false
  }

  private nextHandler (): void {
    if (this.step === 3) {
      // this.loading = true
      this.articleFile.file = this.file
      // this.AnalyseResponse = await this.sendAndAnalyse(this.articleFile)
      this.loading = false
    }
  }

  private columns = [
    {
      name: 'ngram',
      required: true,
      label: 'Ключевое слово',
      align: 'center'
    },
    { name: 'score', label: 'Значение важности', field: 'Значение важности', align: 'center', style: 'width: 10px' }
  ]

  original: AnalyseResponse[] = [{
    ngram: 'Поле для заполнения',
    score: 0.0
  }]

  data: AnalyseResponse[] = [
    {
      ngram: '',
      score: 0
    }
  ]

  private facultetArr: Facultet[] = [{
    facultetId: 0,
    facultetText: ''
  }]

  private facultet: Facultet | null = {
    facultetId: 0,
    facultetText: ''
  };

  private fullDataColumns = [{
    family: 'name',
    required: true,
    label: 'Дата',
    align: 'center',
    field: (row: FullStudentResultInfo) => row.realtime
  }, {
    name: 'score',
    align: 'center',
    label: 'Отметка',
    field: 'score',
    sortable: true
  }]

  private fullData: FullStudentResultInfo[] = [{
    realtime: new Date(),
    score: 0
  }]
}
</script>
