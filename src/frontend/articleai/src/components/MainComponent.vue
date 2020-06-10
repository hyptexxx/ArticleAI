<template lang="pug">
  <div>
  input(v-model="language", type="text")
  input(v-model="maxNgramSize", type="number")
  input(v-model="numberOfKeywords", type="number")
  input(v-model="text", type="text")
  button(@click="sendRequest", style="width: 100px; height: 21px")
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
import axios from 'axios'

  @Component
export default class MainComponent extends Vue {
    @Prop()
    private msg!: string;

    private language = ''
    private maxNgramSize = 0
    private numberOfKeywords = 0
    private text = '';

    private sendRequest (): void {
      axios.post('http://10.10.1.30:5000/yake/', {
        language: this.language,
        // eslint-disable-next-line @typescript-eslint/camelcase
        max_ngram_size: this.maxNgramSize,
        // eslint-disable-next-line @typescript-eslint/camelcase
        number_of_keywords: this.numberOfKeywords,
        text: this.text
      }, {
        headers: {
          'content-type': 'text/json'
        }
      })
    }
}
</script>
