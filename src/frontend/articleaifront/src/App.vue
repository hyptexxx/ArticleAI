<template lang="pug">
  #q-app
    router-view
</template>
<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import SocketInitializer from 'src/boot/socket'
import SocketStore from 'src/store/SocketStore'

@Component
export default class App extends Mixins(SocketInitializer, SocketStore) {

}
</script>
