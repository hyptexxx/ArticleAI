import { Route } from 'vue-router'
import { Vue } from 'vue-property-decorator'
import { AuthResponse } from 'src/models/auth'
import { LocalStorage, Notify } from 'quasar'

export default class AuthService extends Vue {
  public isAuth = async (to: Route, from: Route, next: (path?: string) => void): Promise<void> => {
    const response = await this.$axios.post<AuthResponse>('/heartbeat')
    if (response.status === 403 || response.status === 401) {
      Notify.create({
        color: 'negative',
        progress: true,
        caption: 'Нет прав для доступа к ресурсу.',
        message: 'Пожалуйста выполните авторизацию.',
        icon: 'report_problem',
        position: 'bottom'
      })
      LocalStorage.remove('isLogged')
      LocalStorage.remove('user')
      next('/')
      return
    }

    next()
  };
}
