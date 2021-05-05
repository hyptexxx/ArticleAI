<template lang="pug">
  q-list(bordered class="rounded-borders").text-white
    q-expansion-item.bg-white(v-if="isLoginned === true").text-black
      template(v-slot:header='')
        q-item-section(avatar='')
          q-avatar(rounded='' size='48px')
            img.avatar(:src='avatar')
        q-item-section
          q-item-label {{user.fio}}
          q-item-label(caption='') {{user.info}}
      q-card.bg-white.text-amber-1
        q-btn.bg-grey-3.text-black(align="left" icon="settings" flat label="Параметры" style="width: 100%" v-if="this.clientConfig && this.clientConfig.clientUiConfig && this.clientConfig.clientUiConfig.withSettings")
        q-btn.bg-grey-3.text-black(align="left" icon="history" flat label="История загрузок" style="width: 100%" @click="showUploadHistory" v-if="this.clientConfig && this.clientConfig.clientUiConfig && this.clientConfig.clientUiConfig.withHistoryFile")
        q-btn.bg-grey-3.text-black(align="left" icon="visibility" flat label="Мониторинг" style="width: 100%" @click="showMonitoring" v-if="this.clientConfig && this.clientConfig.clientUiConfig && this.clientConfig.clientUiConfig.withMonitoring")
        q-separator
        q-btn.bg-grey-3.text-black(@click="logout" align="left" icon="directions_run" flat label="Выход" style="width: 100%")
    q-btn.bg-white.text-black(v-else style="width: 100%" outline  @click="setVisible(true)")
      q-item-section(avatar='')
        q-avatar( size='48px')
          img(src='https://www.shareicon.net/data/512x512/2017/01/06/868320_people_512x512.png')
      q-item-section
        q-item-label Вход
</template>

<script lang="ts">
import { Component, Mixins, Watch } from 'vue-property-decorator'
import { User } from 'src/models/auth/Auth'
import LoginStore from 'src/store/LoginStore'
import { AuthResponse } from 'src/models/auth'
import SocketInitializer from 'boot/socket'
@Component
export default class Account extends Mixins(LoginStore, SocketInitializer) {
  private isLoginned = false
  private user: User | null = {
    response: '',
    id: 0,
    pin: 0,
    info: '',
    success: true,
    fio: '',
    adminAuth: 0,
    editorAuth: 0,
    userType: 0,
    firstAuth: 0,
    email: '',
    allowed: 0,
    avatar: ''
  }

  private avatar = 'https://www.shareicon.net/data/512x512/2017/01/06/868320_people_512x512.png'

  @Watch('loginned')
  private isLoginnedAfterRequest (): void {
    this.isLoginned = this.loginned as unknown as boolean

    if (this.isLoginned) {
      this.user = this.responseUser as unknown as User
      this.avatar = 'https://ies.unitech-mo.ru/img/avatar/' + (this.user.id as unknown as string) + '/' + this.user.avatar
    }
  }

  private async logout (): Promise<void> {
    const result = await this.$axios.post<AuthResponse>('/logout')

    switch (result.status) {
      case 200:
        this.$q.localStorage.remove('isLogged')
        this.$q.localStorage.remove('user')

        // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
        this.setLoginned(false)
        // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
        this.setResponseUser(null)
        this.isLoginned = false
        this.user = null
        this.setClientConfig(null)
        this.setAuth(null)
        await this.doRoute()
        break
    }
  }

  private async created (): Promise<void> {
    await this.$axios.post<AuthResponse>('/heartbeat')
    if (this.$q.localStorage.getItem('isLogged')) {
      this.isLoginned = this.$q.localStorage.getItem('isLogged') as boolean

      if (this.$q.localStorage.getItem('user')) {
        this.user = this.$q.localStorage.getItem('user') as unknown as User
        this.avatar = 'https://ies.unitech-mo.ru/img/avatar/' + (this.user.id as unknown as string) + '/' + this.user.avatar
        this.isLoginned = true
      } else {
        this.isLoginned = false
      }
    } else {
      this.$q.localStorage.remove('isLogged')
      this.$q.localStorage.remove('user')

      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
      this.setLoginned(false)
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
      this.setResponseUser(null)
      this.isLoginned = false
      this.user = null
    }
  }

  private async showUploadHistory (): Promise<void> {
    await this.$router.push({ name: 'fileHistory' })
  }

  private async showMonitoring (): Promise<void> {
    await this.$router.push({ name: 'monitoring' })
  }

  private async doRoute (): Promise<void> {
    if (this.$route.name !== 'main') {
      await this.$router.push({ name: 'main' })
    } else {
      this.$router.go(0)
    }
  }
}
</script>
