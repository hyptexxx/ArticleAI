import { Module } from 'vuex'
import SocketInterface from '../interface/SocketInterface'
import { StoreInterface } from 'src/store/interface/StoreInterface'

const SocketModule: Module<SocketInterface, StoreInterface> = {
  namespaced: true,
  state: () => ({
    message: {} as string | null,
    isConnected: {} as boolean
  }),
  mutations: {
    setMessage (state: any, message: string | null): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.message = message
    },

    setConnectedState (state: any, isConnected: boolean): void {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
      state.isConnected = isConnected
    }
  },
  actions: {},
  modules: {},
  getters: {
    getMessage: state => {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return state.message
    },

    isConnected: state => {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return state.isConnected
    }
  }
}

export default SocketModule
