<template lang="pug">
  q-dialog(v-model='sDialog' persistent='' :maximized='maximizedToggle' transition-show='slide-up' transition-hide='slide-down')
    q-card.bg-primary.text-white
      q-bar
        q-space
        q-btn(dense='' flat='' icon='minimize' @click='maximizedToggle = false' :disable='!maximizedToggle')
          q-tooltip(v-if='maximizedToggle' content-class='bg-white text-primary') Minimize
        q-btn(dense='' flat='' icon='crop_square' @click='maximizedToggle = true' :disable='maximizedToggle')
          q-tooltip(v-if='!maximizedToggle' content-class='bg-white text-primary') Maximize
        q-btn(dense='' flat='' icon='close' v-close-popup='')
          q-tooltip(content-class='bg-white text-primary') Close
      q-card-section
        .text-h6 Alert

</template>

<script lang="ts">
import { Component, PropSync, Vue } from 'vue-property-decorator'
import { Recommendations } from 'src/models/Recommendation'

@Component
export default class RecommendationSettings extends Vue {
  @PropSync('dialog', { type: Boolean }) sDialog!: boolean
  @PropSync('recommendation') sRecommendation!: Recommendations
  private maximizedToggle = false
}

</script>
