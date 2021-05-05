import Vue from 'vue'
import Component from 'vue-class-component'
import { namespace } from 'vuex-class/lib'
import { AuthResponse, BaseClientConfig, User } from 'src/models/auth'

const LoginModule = namespace('LoginModule')

@Component
export default class LoginStore extends Vue {
  // setters

  @LoginModule.Mutation('setVisible')
  public setVisible!: (isVisible: boolean) => void

  @LoginModule.Mutation('setLoginned')
  public setLoginned!: (isLoginned: boolean) => void

  @LoginModule.Mutation('setResponseUser')
  public setResponseUser!: (user: User | null) => void

  @LoginModule.Mutation('setAuth')
  public setAuth!: (auth: AuthResponse | null) => void

  @LoginModule.Mutation('setClientConfig')
  public setClientConfig!: (auth: BaseClientConfig | null) => void

  // getters

  @LoginModule.Getter('getVisible')
  public windowVisible!: boolean

  @LoginModule.Getter('getLoginned')
  public loginned!: boolean

  @LoginModule.Getter('getResponseUser')
  public responseUser!: User

  @LoginModule.Getter('getAuth')
  public auth!: AuthResponse

  @LoginModule.Getter('getClientConfig')
  public clientConfig!: BaseClientConfig
}
