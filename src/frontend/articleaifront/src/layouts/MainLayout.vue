<template lang="pug">
q-layout(view='lHh Lpr lFf')
  q-header(elevated='')
    q-toolbar
      q-btn(flat='' dense='' round='' icon='menu' aria-label='Menu' @click='leftDrawerOpen = !leftDrawerOpen')
      q-toolbar-title
        | Актуальность статей
  q-drawer(v-model='leftDrawerOpen'  bordered='' content-class='bg-grey-1')
  q-page-container
    router-view
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class MainLayout extends Vue {
  leftDrawerOpen = false;
}
</script>
