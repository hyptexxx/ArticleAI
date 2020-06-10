<template>
  <div id="app">
    <MainComponent msg="Welcome to Your Vue.js + TypeScript App" />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import MainComponent from '@/components/MainComponent.vue'

@Component({
  components: {
    MainComponent
  }
})

export default class App extends Vue {}
</script>
