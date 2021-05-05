import Vue from 'vue'
import Component from 'vue-class-component'
import { namespace } from 'vuex-class'

const SocketModule = namespace('SocketModule')

@Component
export default class SocketStore extends Vue {
  // setters
  @SocketModule.Mutation('setMessage')
  public setMessage!: (message: string) => void

  @SocketModule.Mutation('setConnectedState')
  public setConnectedState!: (isConnected: boolean) => void

  // getters
  @SocketModule.Getter('getMessage')
  public getMessage!: string

  @SocketModule.Getter('isConnected')
  public isConnected!: boolean
}
