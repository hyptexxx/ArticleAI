<template lang="pug">
  q-page.row.items-center.justify-evenly
    Main
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import Main from 'components/Main.vue'
import SocketInitializer from 'src/boot/socket'
import SocketStore from 'src/store/SocketStore'
@Component({
  components: { Main }
})
export default class PageIndex extends Mixins(SocketInitializer, SocketStore) {
  private mounted (): void {
    this.connect()
  }
}
</script>
