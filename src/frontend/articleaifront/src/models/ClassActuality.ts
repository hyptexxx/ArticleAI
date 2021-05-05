export interface ClassActuality{
  class: number
  actuality: number
}

export interface ActualityStatsDto{
  classId: number
  date: string
  className: string
  actuality: number
}

export interface ClassDto{
  classId: number
  className: string
}

export interface ClassDtoChart{
  x: string
  y: number
}
