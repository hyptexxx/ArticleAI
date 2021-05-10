<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Глобальные параметры системы
              .text-subtitle2.text-black Будьте внимательны при изменении настроек.
            q-list.rounded-borders(bordered='' style='max-width: 900px; position: relative; margin: 0 auto; margin-bottom: 20px:')
              q-item-label.bg-grey-3(header='') Коэфициент умножения при определении актуальности слов.
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  Коэфициент умножения:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.coefficient' type='number' outlined :dense="true" label='Коэфициент умножения' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.coefficient.required && $v.appSettings.coefficient.$params.required" class="error-label") Обязательно
            q-list.rounded-borders(bordered='' style='max-width: 900px; position: relative; margin: 0 auto; margin-bottom: 20px: display: flex; justify-content: space-evently')
              q-item-label.bg-grey-3(header='') Гиперпараметры Y.A.K.E.
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  language:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.language' outlined :dense="true" label='language' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.language.required && $v.appSettings.yakeParams.language.$params.required" class="error-label") Обязательно
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  max_ngram_size:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.max_ngram_size' type='number' outlined :dense="true" label='max_ngram_size' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.max_ngram_size.required && $v.appSettings.yakeParams.max_ngram_size.$params.required" class="error-label") Обязательно
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  deduplication_thresold:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.deduplication_thresold' type='number' outlined :dense="true" label='deduplication_thresold' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.deduplication_thresold.required && $v.appSettings.yakeParams.deduplication_thresold.$params.required" class="error-label") Обязательно
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  deduplication_algo:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.deduplication_algo' outlined :dense="true" label='deduplication_algo' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.deduplication_algo.required && $v.appSettings.yakeParams.deduplication_algo.$params.required" class="error-label") Обязательно
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  window_size:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.windowSize' type='number' outlined :dense="true" label='window_size' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.windowSize.required && $v.appSettings.yakeParams.windowSize.$params.required" class="error-label") Обязательно
              q-item
                q-item-section(avatar='' top='')
                  q-icon.text-indigo(name='settings' color='black' size='34px')
                q-item-section.col-2.gt-sm(top='')
                  q-item-label.q-mt-sm  number_of_keywords:
                q-item-section(top='' side='')
                  .text-grey-8.q-gutter-xs
                    q-input(v-model='appSettings.yakeParams.number_of_keywords' type='number' outlined :dense="true" label='number_of_keywords' @input='saveButtonVisible = true')
                    span.text-red-10(v-if="!$v.appSettings.yakeParams.number_of_keywords.required && $v.appSettings.yakeParams.number_of_keywords.$params.required" class="error-label") Обязательно
          q-page-sticky(position='right' :offset='[18, 18]')
            q-btn(icon='add' color='green' label='Сохранить' @click="confirm = true" v-if='saveButtonVisible')
          q-dialog(v-model='confirm' persistent='')
            q-card
              q-card-section.row.items-center
                span.q-ml-sm Сохранить изменения?
              q-card-actions(align='right')
                q-btn(flat='' label='Отмена' color='red' v-close-popup='')
                q-btn(flat='' label='Да' color='green' v-close-popup='' @click='save')

</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import SocketInitializer from 'src/boot/socket'
import SocketStore from 'src/store/SocketStore'
import { AppSettings } from 'src/models/AppSettings'
import { validationMixin } from 'vuelidate'
import AppSettingsValidation from 'src/validation/AppSettingsValidation'
@Component({
  mixins: [validationMixin],
  validations: AppSettingsValidation
})
export default class PageIndex extends Mixins(SocketInitializer, SocketStore) {
  private appSettings: AppSettings = {
    yakeParams: {
      language: '',
      max_ngram_size: 0,
      deduplication_thresold: 0,
      deduplication_algo: '',
      windowSize: 0,
      number_of_keywords: 0
    },
    coefficient: 0
  }

  private confirm = false
  private saveButtonVisible = false

  private async mounted (): Promise<void> {
    this.connect()
    const result = await this.$axios.get<AppSettings>('/settings')
    switch (result.status) {
      case 200:
        this.appSettings = result.data
    }
  }

  private async save (): Promise<void> {
    this.$v.$touch()

    if (!this.$v.$anyError) {
      const formData: FormData = new FormData()
      formData.append('settings', JSON.stringify(this.appSettings))
      const result = await this.$axios.post<AppSettings>('/settings', formData)
      switch (result.status) {
        case 200:
          this.$q.notify({
            color: 'positive',
            message: 'Настройки успешно внесены',
            icon: 'report_problem',
            progress: true,
            position: 'bottom'
          })
      }
    } else {
      this.$q.notify({
        color: 'negative',
        message: 'Ошибка валидации',
        caption: 'Проверьте заполненность полей настроек.',
        icon: 'report_problem',
        progress: true,
        position: 'bottom'
      })
    }
  }
}
</script>
