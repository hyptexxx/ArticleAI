<template lang="pug">
  q-page.row.items-center.justify-evenly
    ClassComponent
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import ClassComponent from 'components/ClassComponent.vue'

@Component({
  components: { ClassComponent }
})
export default class PageIndex extends Vue {

}
</script>
