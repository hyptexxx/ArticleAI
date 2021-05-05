<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Ваша история загрузок и результатов анализа.
              .text-subtitle2.text-black За весь период.
              q-separator(spaced='')
              q-list.rounded-borders(bordered='' style='max-width: 900px; position: relative; margin: 0 auto; margin-bottom: 20px' v-for="historyPoint in fileHistory")
                q-item-label(header='') Актуальность: {{historyPoint.actuality}}%
                q-item
                  q-item-section(avatar='' top='')
                    q-icon.text-indigo(name='article' color='black' size='34px')
                  q-item-section.col-2.gt-sm(top='')
                    q-item-label.q-mt-sm  Публикация
                  q-item-section(top='')
                    q-item-label(lines='1')
                      span.text-weight-medium [{{historyPoint.publicationName}}]
                      span.text-grey-8  - Дата загрузки: {{historyPoint.publicationUploadDate}}
                    q-item-label(caption='' lines='1')
                      | Пользователю: {{historyPoint.fio}}
                  q-item-section(top='' side='')
                    .text-grey-8.q-gutter-xs
                      q-btn.gt-xs(size='12px' flat='' dense='' round='' icon='file_download' @click='getFile(historyPoint.publicationId, "publication")')
                q-separator(spaced='')
                q-item
                  q-item-section(avatar='' top='')
                    q-icon.text-green(name='file_download_done' color='black' size='34px')
                  q-item-section.col-2.gt-sm(top='')
                    q-item-label.q-mt-sm Сертификат
                  q-item-section(top='')
                    q-item-label(lines='1')
                      span.text-weight-medium [{{historyPoint.certificateName}}]
                      span.text-grey-8  - Дата выдачи: {{historyPoint.certificateGenerationDate}}
                    q-item-label(caption='' lines='1')
                      | Пользователю: {{historyPoint.fio}}
                  q-item-section(top='' side='')
                    .text-grey-8.q-gutter-xs
                      q-btn.gt-xs(size='12px' flat='' dense='' round='' icon='file_download' @click='getFile(historyPoint.certificateId, "certificate")')

</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import FileHistoryDto from 'src/models/FileHistoryDto'
import SocketInitializer from 'boot/socket'
import SocketStore from 'src/store/SocketStore'

@Component
export default class FileHistory extends Mixins(SocketInitializer, SocketStore) {
  private fileHistory: FileHistoryDto[] = [{
    fio: '',
    publicationName: '',
    certificateName: '',
    publicationUploadDate: '',
    certificateGenerationDate: '',
    actuality: 0,
    publicationId: 0,
    certificateId: 0
  }]

  private async mounted (): Promise<void> {
    const result = await this.$axios.post<FileHistoryDto[]>('/fileHistory')
    this.connect()
    switch (result.status) {
      case 200:
        this.fileHistory = result.data
    }
  }

  private getFile (id: number, type: string): void {
    window.location.replace((process.env.API_BASE_URL as unknown as string) + 'file/' + (id as unknown as string) + '/' + type)
  }
}
</script>
