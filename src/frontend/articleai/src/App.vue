<template>
  <div id="app">
    <MainComponent response="response" />
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
