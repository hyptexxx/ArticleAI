import { required } from 'vuelidate/lib/validators'

export default {
  appSettings: {
    yakeParams: {
      language: { required },
      max_ngram_size: { required },
      deduplication_thresold: { required },
      deduplication_algo: { required },
      windowSize: { required },
      number_of_keywords: { required }
    },
    coefficient: { required }
  }
}
