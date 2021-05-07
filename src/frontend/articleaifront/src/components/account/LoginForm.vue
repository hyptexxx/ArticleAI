<template lang="pug">
  q-card.text-black(style='width: 300px')
    q-card-section
      .text-h6 Авторизация
    q-card-section
      q-input(filled v-model="userCredentials.login" label="Логин" type='text' v-on:keypress.enter="authorizeUser")
      span.text-red-10(v-if="!$v.userCredentials.login.required && $v.userCredentials.login.$params.required" class="error-label") Обязательно
    q-card-section
      q-input(filled v-model="userCredentials.password" label="Пароль" type='password' v-on:keypress.enter="authorizeUser")
      span.text-red-10(v-if="!$v.userCredentials.password.required && $v.userCredentials.password.$params.required" class="error-label") Обязательно
    q-card-actions.bg-white.text-black(align='right')
      q-btn(flat='' label='OK' @click="authorizeUser")
</template>

<script lang="ts">

import { Component, Mixins } from 'vue-property-decorator'
import { validationMixin } from 'vuelidate'
import { AuthResponse, BaseClientConfig, User, UserCredentials } from 'src/models/auth'
import LoginValidation from 'src/validation/LoginValidation'
import LoginStore from 'src/store/LoginStore'
import SocketInitializer from 'boot/socket'

@Component({
  mixins: [validationMixin],
  validations: LoginValidation
})
export default class LoginForm extends Mixins(LoginStore, SocketInitializer) {
  private userCredentials: UserCredentials = {
    login: '',
    password: ''
  }

  private async authorizeUser (): Promise<void> {
    this.$v.$touch()
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
    this.$store.state.windowVisible = false
    if (!this.$v.$anyError) {
      const formData = new FormData()

      formData.append('login', this.userCredentials.login)
      formData.append('password', this.userCredentials.password)

      const result = await this.$axios.post<AuthResponse>('/auth', formData)

      switch (result.status) {
        case 200:

          this.$q.localStorage.set('isLogged', true)
          this.$q.localStorage.set('user', result.data.user)

          // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
          this.setLoginned(true)
          // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
          this.setResponseUser(result.data.user as unknown as User)
          // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
          this.setAuth(result.data as unknown as AuthResponse)
          this.setClientConfig(result.data.baseClientConfig as unknown as BaseClientConfig)
          this.setVisible(false)
          await this.doRoute()
          break
        case 400:
          result.data.errorsToClient.forEach(value => {
            this.$q.notify({
              color: 'negative',
              message: value.message,
              caption: value.errorName,
              icon: 'report_problem',
              progress: true,
              position: 'bottom'
            })
          })
      }
    } else {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access,@typescript-eslint/no-unsafe-call
      this.setLoginned(false)

      this.$q.notify({
        color: 'negative',
        message: 'Поля не заполнены',
        icon: 'report_problem',
        progress: true,
        position: 'bottom'
      })
    }
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
