  <template lang="pug">
q-layout(view='hHh lpR lFf')
  q-header(elevated='')
    q-toolbar
      q-btn(flat='' dense='' round='' icon='menu' aria-label='Menu' @click='leftDrawerOpen = !leftDrawerOpen')
      q-toolbar-title
        | Актуальность научных публикаций
      div.row.q-ml-auto
        q-item-section(avatar='')
          q-avatar( size='48px')
            img(src='https://ies.unitech-mo.ru/files/upload/tpl/logo.svg?1609842718')
    q-tabs(align='left' inline-label)
      q-route-tab(to='/' label='Главная' icon='star')
      q-route-tab(to='/about' label='О системе' icon='search')
  q-drawer(v-model='leftDrawerOpen' content-class='q-layout__section--marginal')
    account
    span(style="position: absolute; bottom: 0; font-size: 11px;")
      | {{productVersion}}
  q-page-container
    router-view
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import Account from 'components/account/Account.vue'

@Component({
  components: {
    Account
  }
})
export default class MainLayout extends Vue {
  leftDrawerOpen = true;
  productVersion = 'v3.0.3 alpha.'
}
</script>
