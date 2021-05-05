export interface AuthResponse {
  user: User
  errorsToClient: ErrorsToClient[]
  baseClientConfig: BaseClientConfig
}

export interface UserCredentials {
  login: string
  password: string
}

export interface User {
  response: string
  id: number
  pin: number
  info: string
  success: boolean
  fio: string
  adminAuth: number
  editorAuth: number
  userType: number
  firstAuth: number
  email: string
  allowed: number
  avatar: string
}

export interface ErrorsToClient {
  cause: string
  message: string
  errorName: string
}

export interface BaseClientConfig {
  clientUiConfig: ClientUiConfig
}

export interface ClientUiConfig {
  withAnalyseInfoDisplay: boolean
  withCertificateGeneration: boolean
  withSettings: boolean
  withHistoryFile: boolean
  withMonitoring: boolean
}
