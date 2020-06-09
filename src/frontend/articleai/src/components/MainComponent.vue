<template>
  <h1>{{msg}}</h1>
</template>

<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';

    @Component
    export default class MainComponent extends Vue {
        @Prop()
        private msg!: string;

        private FG!: string;


        public mounted(){
            console.log(1)
        }
    }
</script>
