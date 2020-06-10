import YakeResponse from '@/models/YakeResponse'

export default interface RequestServiceInterface{
  sendRequestAndSaveFile(file: File): void;

  sendRequestToYake(language: string, maxNgramSize: number, numberOfKeywords: number, text: string): Promise<YakeResponse[]>;
}
