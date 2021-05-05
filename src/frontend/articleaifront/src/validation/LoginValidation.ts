import { required } from 'vuelidate/lib/validators'

export default {
  userCredentials: {
    login: {
      required
    },
    password: {
      required
    }
  }
}
