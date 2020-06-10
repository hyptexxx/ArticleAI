<template lang="pug">
  div
    input(ref="fileInput" @change="getFile" type="file")
    button(@click="sendFile")
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'

@Component
export default class ArticleFile extends Mixins(RequestService) {
  private file!: File

  private getFile (event: Event): void {
    const file: HTMLInputElement = (event.target as HTMLInputElement)
    if (file !== null && file.files !== null) {
      this.file = file.files[0]
    }
  }

  private sendFile (): void {
    this.sendRequestAndSaveFile(this.file)
  }
}
</script>
