export interface AuthResponse {
  reason: string
  status: string
}

export interface UserCredentials {
  login: string
  password: string
}

export interface User {
  response: string,
  id: number,
  pin: number,
  info: string,
  success: boolean,
  fio: string,
  adminAuth: number,
  editorAuth: number,
  userType: number,
  firstAuth: number,
  email: string,
  allowed: number,
  avatar: string
}
