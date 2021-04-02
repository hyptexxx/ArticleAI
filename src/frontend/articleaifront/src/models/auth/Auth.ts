export interface AuthResponse {
  reason: string
  status: string
}

export interface UserCredentials {
  login: string
  password: string
}

export interface User {
  login: string
  post: string
  idUser: number
  role: string
}
