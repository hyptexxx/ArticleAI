<template lang="pug">
  q-list(bordered class="rounded-borders").text-white
    q-expansion-item.bg-grey-10(v-if="isLoginned === true").text-white
      template(v-slot:header='').text-white
        q-item-section(avatar='')
          q-avatar(rounded='' size='48px')
            img(src='https://cdn.quasar.dev/img/avatar.png')
        q-item-section.text-white
          q-item-label.text-white {{user.login}}
          q-item-label.text-white(caption='') {{user.role}}
        q-item-section(side='').text-white
          | Аккаунт
      q-card.bg-white.text-amber-1
        q-btn.bg-white(align="left" icon="view_compact" flat label="Личный кабинет" style="width: 100%")
        q-btn.bg-white( align="left" icon="directions_run" flat label="Выход" style="width: 100%")
    q-btn.bg-white.text-black(v-else style="width: 100%" outline)
      q-item-section(avatar='')
        q-avatar( size='48px')
          img(src='https://www.shareicon.net/data/512x512/2017/01/06/868320_people_512x512.png')
      q-item-section
        q-item-label Вход
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import { User } from 'src/models/auth/Auth'
@Component
export default class Account extends Vue {
  private isLoginned = false
  private user: User | null = {
    login: '',
    post: '',
    idUser: 0,
    role: 'hr'
  }
}
</script>
