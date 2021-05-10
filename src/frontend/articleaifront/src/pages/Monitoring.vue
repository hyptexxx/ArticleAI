<template lang="pug">
  q-page.flex.bg-white(style="width: 100%; height: 100%")
    q-card.bg-white.my-card.text-black(dark='' bordered='' style="width: 100%")
      q-card-section
        q-separator(dark='' inset='')
        q-card-section
          q-card
            q-card-section
              .text-h6.text-black Мониторинг трендов актуальностей классов
              .text-subtitle2.text-black Графики динамики изменения актуальностей классов с интервалом в 24 часа.
            q-separator(spaced='')
            q-card-section.bg-indigo.text-white
              .text-subtitle2.text-white Фильтрация результатов поиска
            q-card-section.flex(style="justify-content: space-evenly;" )
              q-select(bg-color="white"
                outlined
                v-model='currClass'
                style="width: 200px"
                :options='classDto'
                :dense="true"
                option-value='classId'
                option-label='className'
                @input='onClassChange'
                label='Имя Класса')
                template(v-slot:prepend='')
                  q-icon(name='spellcheck')
            q-card-section
              .text-subtitle2.text-white Фильтрация результатов поиска
              chart(width="100%" height="200" type="line" :options="options" :series="series")
</template>

<script lang="ts">
import { Component, Mixins } from 'vue-property-decorator'
import chart from 'vue-apexcharts'
import { ClassDto, ActualityStatsDto, ClassDtoChart } from 'src/models/ClassActuality'
import SocketInitializer from 'boot/socket'
import SocketStore from 'src/store/SocketStore'

@Component({
  components: {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
    chart
  }
})
export default class Monitoring extends Mixins(SocketInitializer, SocketStore) {
  private classDto: ClassDto[] = []
  private actualityStatsDto: ActualityStatsDto[] = []

  private currClass: ClassDto = {
    classId: 0,
    className: ''
  }

  private options = {
    chart: {
      id: 'vuechart'
    },
    stroke: {
      curve: 'straight'
    },
    markers: {
      size: 10
    }
  }

  private series = [{
    name: '',
    data: [{}]
  }]

  private async mounted (): Promise<void> {
    const result = await this.$axios.get<ClassDto[]>('/classes')
    this.connect()
    switch (result.status) {
      case 200:
        this.classDto = result.data as unknown as ClassDto[]
    }
  }

  private async onClassChange (): Promise<void> {
    const result = await this.$axios.get<ActualityStatsDto[]>('/actuality', {
      params: {
        // eslint-disable-next-line
        classId: this.currClass.classId
      }
    })
    // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
    const buffer: ClassDtoChart[] = []
    switch (result.status) {
      case 200:
        this.actualityStatsDto = result.data as unknown as ActualityStatsDto[]

        this.series.splice(0, this.series.length)
        this.actualityStatsDto.forEach(value => {
          buffer.push({
            x: value.date,
            y: value.actuality
          })
        })

        this.series.push({
          name: this.actualityStatsDto[0].className,
          // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
          data: buffer
        })
    }
  }
}
</script>
