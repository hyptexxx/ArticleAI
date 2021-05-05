<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Описание
              .text-subtitle2.text-black Данная разработка призвана помочь вам в определении степени актуальности теста вашей публикации.
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import SocketInitializer from 'boot/socket'
import SocketStore from 'src/store/SocketStore'

@Component
export default class About extends Mixins(SocketInitializer, SocketStore) {
  private mounted (): void {
    this.connect()
  }
}
</script>
