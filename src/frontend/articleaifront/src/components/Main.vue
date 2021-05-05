<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-dialog(v-model="isLoginVisible" @hide='hideLoginForm')
      login-form
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Для начала анализа загрузите вашу публикацию.
              .text-subtitle2.text-black Разрешённые типы документов:
              .text-subtitle2.text-black .doc .docx .pdf
              q-file(filled='' bottom-slots='' multiple v-model='files' label='Публикация' counter='' max-files='1')
                template(v-slot:before='')
                  q-icon(name='folder_open')
                template(v-slot:hint='')
                  | Выберите публикацию для анализа
                template(v-slot:append='')
                  q-btn(round='' dense='' flat='' icon='add' @click.stop='')
            q-card-section
              q-btn.bg-indigo.text-white(flat='' label='Анализ' @click.stop='setVisibleContent')
                q-tooltip(content-class='bg-indigo' content-style="font-size: 16px" :offset='[10, 10]')
                  | Анализировать публикацию и получить рекомендации
            q-slide-transition
              div(v-show='visible')
                q-separator(dark='' inset='')
                Recommendation(v-if='visible && this.files' :files.sync='this.files')

</template>

<script lang="ts">
import { Component, Mixins, Watch } from 'vue-property-decorator'
import RequestService from 'src/services/implementation/RequestService'
import ArticleFile from 'src/models/ArticleFile/ArticleFile'
import { Class } from 'src/models/Class'
import Recommendation from 'components/recomendation/Recommendation.vue'
import LoginStore from 'src/store/LoginStore'
import LoginForm from 'components/account/LoginForm.vue'

@Component({
  components: {
    Recommendation,
    LoginForm
  }
})
export default class Main extends Mixins(RequestService, LoginStore) {
  private visible = false
  private files: File[] | null = null
  private isLoginVisible = false

  @Watch('windowVisible')
  private void (): void {
    this.isLoginVisible = this.windowVisible
  }

  private hideLoginForm (): void {
    this.setVisible(false)
  }

  private setVisibleContent (): void {
    if (this.files) {
      this.visible = true
    } else {
      this.$q.notify({
        icon: 'warning',
        message: 'Загрузите публикацию.',
        caption: 'Для продолжения - загрузите публикацию.',
        color: 'primary'
      })
    }
  }

  a = [
    { name: 'cluster', label: 'Класс', field: 'cluster', align: 'center', style: 'width: 10px' },
    { name: 'keyword', label: 'Ключевая фраза', field: 'keyword', align: 'center', style: 'width: 10px' },
    { name: 'actuality', label: 'Актаульность', field: 'actuality', align: 'center', style: 'width: 10px' }
  ]

  b = [
    { name: 'name', label: 'Класс', field: 'name', align: 'center', style: 'width: 10px' },
    { name: 'classActuality', label: 'Актуальность', field: 'classActuality', align: 'center', style: 'width: 10px' }
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
      numberOfKeywords: 20,
      text: ''
    }
  }
}
</script>
