<template lang="pug">
  #file
    input(ref="fileInput" @change="getFile" type="file")
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import RequestService from '@/services/implementation/RequestService'
import { ArticleMutationModule } from '@/store/ArticleMutationModule'

@Component
export default class ArticleFile extends Mixins(RequestService, ArticleMutationModule) {
  private getFile (event: Event): void {
    const file: HTMLInputElement = (event.target as HTMLInputElement)
    if (file !== null && file.files !== null) {
      this.setArticleFile(file.files[0])
    }
  }
}
</script>
