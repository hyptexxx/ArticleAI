import { Vue, Component } from 'vue-property-decorator'
import RequestServiceInterface from '@/services/interface/RequestServiceInterface'
import axios from 'axios'
import YakeResponse from '@/models/YakeResponse'

@Component
export default class RequestService extends Vue implements RequestServiceInterface {
  sendRequestAndSaveFile (file: File): void {
    console.log(file)
    const formData: FormData = new FormData()
    formData.append('file', file)
    axios.post('http://localhost:8080/api/files/analyze', formData).then(response => (
      console.log(response)
    ))
  }

  async sendRequestToYake (language: string, maxNgramSize: number, numberOfKeywords: number, text: string): Promise<YakeResponse[]> {
    const response = await axios.post<YakeResponse[]>('http://10.10.1.30/yake/', {
      language: language,
      max_ngram_size: maxNgramSize,
      number_of_keywords: numberOfKeywords,
      text: text
    }, {
      headers: {
        'content-type': 'text/json',
        'Access-Control-Allow-Origin': '*'
      }
    })
    return response.data
  }
}
