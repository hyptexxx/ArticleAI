import { Module } from 'vuex'
import LoginStoreInterface from '../interface/LoginStoreInterface'
import { StoreInterface } from 'src/store/interface/StoreInterface'
import { AuthResponse, BaseClientConfig, User } from 'src/models/auth'

const LoginModule: Module<LoginStoreInterface, StoreInterface> = {
  namespaced: true,
  state: () => ({
    visible: false,
    loginned: false,
    user: {} as User | null,
    auth: {} as AuthResponse | null,
    clientConfig: {} as BaseClientConfig | null
  }),
  mutations: {
    setVisible (state: any, visible: boolean): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.visible = visible
    },
    setLoginned (state: any, visible: boolean): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.loginned = visible
    },
    setResponseUser (state: any, user: User | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.user = user
    },
    setAuth (state: any, auth: AuthResponse | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.auth = auth
    },
    setClientConfig (state: any, clientConfig: BaseClientConfig | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.clientConfig = clientConfig
    }
  },
  actions: {},
  modules: {},
  getters: {
    getVisible: state => {
      return state.visible
    },
    getLoginned: state => {
      return state.loginned
    },
    getResponseUser: state => {
      return state.user
    },
    getAuth: state => {
      return state.auth
    },
    getClientConfig: state => {
      return state.clientConfig
    }
  }
}

export default LoginModule
