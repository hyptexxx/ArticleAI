import { CompatClient, IMessage, Stomp } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import { Component, Mixins } from 'vue-property-decorator'
import SocketStore from 'src/store/SocketStore'

@Component
export default class SocketInitializer extends Mixins(SocketStore) {
  private stompClient!: CompatClient

  public connect (): void {
    this.stompClient = Stomp.over(() => new SockJS('/steps'))
    this.stompClient.connect({}, (frame: string) => {
      console.log('Connected: ' + frame)

      this.setConnectedState(true)
      this.stompClient.subscribe('/user/topic/analyseSteps', (messageOutput: IMessage) => {
        this.setMessage(messageOutput.body)
      })
    })
  }
}
