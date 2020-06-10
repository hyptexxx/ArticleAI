<template lang="pug">
  <div>
    input(v-model="docInfo.language", type="text")
    input(v-model="docInfo.maxNgramSize", type="number")
    input(v-model="docInfo.numberOfKeywords", type="number")
    input(v-model="docInfo.text", type="text")
    button(@click="sendRequest", style="width: 100px; height: 21px")
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator'
// noinspection SpellCheckingInspection
import axios from 'axios'

@Component
export default class MainComponent extends Vue {
    @Prop()
    private msg!: string;

    private docInfo = {
      language: '',
      maxNgramSize: 0,
      numberOfKeywords: 0,
      text: ''
    }

    private sendRequest (): void {
      axios.post('http://10.10.1.30:5000/yake/', {
        language: this.docInfo.language,
        // eslint-disable-next-line @typescript-eslint/camelcase
        max_ngram_size: this.docInfo.maxNgramSize,
        // eslint-disable-next-line @typescript-eslint/camelcase
        number_of_keywords: this.docInfo.numberOfKeywords,
        text: this.docInfo.text
      }, {
        headers: {
          'content-type': 'text/json'
        }
      })
    }
}
</script>
