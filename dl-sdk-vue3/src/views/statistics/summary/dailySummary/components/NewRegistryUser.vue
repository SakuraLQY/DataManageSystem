<template>
    <div ref="chartRef" :style="{ height, width }"></div>
  </template>
  <script lang = "ts" setup>
  import { onMounted, ref, Ref ,watch,inject} from 'vue';
  import { useECharts } from '/@/hooks/web/useECharts';
  import { basicProps } from './props';
  import { queryList } from '../CtDailySummary.api';
  import { Item } from '../CtDailySummary.data';
  
  const params = inject('params');

  const responseData = ref<Item[]>([]);
  //拿到今天和昨天的数据
  const fetchData = async()=>{
  const todayData = await queryList(params);
  responseData.value = todayData;
  }
  //参数不一致的时候，数据会发生变化
  watch(
  params,
  () => {
    fetchData();
  }
);
  //Echart数据的展示
  const ToDayData = ref<number[]>([]);
  const yesterDayData = ref<number[]>([]);
    watch(responseData,(newData)=>{
      if(newData[0][3].level==='新增账号'){
        ToDayData.value = [ parseInt(newData[0][3].zeroHour), parseInt(newData[0][3].oneHour),parseInt(newData[0][3].twoHour), parseInt(newData[0][3].threeHour),parseInt(newData[0][3].fourHour),parseInt(newData[0][3].fiveHour), parseInt(newData[0][3].sixHour),
        parseInt(newData[0][3].sevenHour), parseInt(newData[0][3].eightHour), parseInt(newData[0][3].nineHour),parseInt(newData[0][3].tenHour),  parseInt(newData[0][3].elevenHour),parseInt(newData[0][3].twelveHour),parseInt(newData[0][3].thirteenHour), parseInt(newData[0][3].fourteenHour),
        parseInt(newData[0][3].fifteenHour), parseInt(newData[0][3].sixteenHour), parseInt(newData[0][3].seventeenHour), parseInt(newData[0][3].eighteenHour), parseInt(newData[0][3].nineteenHour), parseInt(newData[0][3].twentyHour),
        parseInt(newData[0][3].twentyoneHour),parseInt(newData[0][3].twentytwoHour), parseInt(newData[0][3].twentythreeHour)]
      }
      if(newData[1][3].level==='新增账号'){
        yesterDayData.value = [parseInt(newData[1][3].zeroHour), parseInt(newData[1][3].oneHour),parseInt(newData[1][3].twoHour), parseInt(newData[1][3].threeHour),parseInt(newData[1][3].fourHour),parseInt(newData[1][3].fiveHour), parseInt(newData[1][3].sixHour),
        parseInt(newData[1][3].sevenHour), parseInt(newData[1][3].eightHour), parseInt(newData[1][3].nineHour),parseInt(newData[1][3].tenHour),  parseInt(newData[1][3].elevenHour),parseInt(newData[1][3].twelveHour),parseInt(newData[1][3].thirteenHour), parseInt(newData[1][3].fourteenHour),
        parseInt(newData[1][3].fifteenHour), parseInt(newData[1][3].sixteenHour), parseInt(newData[1][3].seventeenHour), parseInt(newData[1][3].eighteenHour), parseInt(newData[1][3].nineteenHour), parseInt(newData[1][3].twentyHour),
        parseInt(newData[1][3].twentyoneHour),parseInt(newData[1][3].twentytwoHour), parseInt(newData[1][3].twentythreeHour)]
      }
      setOptions({
    title: {
    text: '新增账号',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: [
      '今日新增账号',
      '昨日新增账号'
    ],
    bottom: '0%'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '10%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: [
      '0时',
      '2时',
      '3时',
      '4时',
      '5时',
      '6时',
      '7时',
      '8时',
      '9时',
      '10时',
      '11时',
      '12时',
      '13时',
      '14时',
      '15时',
      '16时',
      '17时',
      '18时',
      '19时',
      '20时',
      '21时',
      '22时',
      '23时'
    ]
  },
  yAxis: {
    type: 'value',
    name: '充值人数',
    nameLocation: 'middle',
    nameGap: 45
  },
  series: [
    {
      name: '今日新增账号',
      type: 'line',
      data: ToDayData.value
    },
    {
      name: '昨日新增账号',
      type: 'line',
      data: yesterDayData.value
    },
  ]
});
    })
  defineProps({
    ...basicProps,
  });
  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions } = useECharts(chartRef as Ref<HTMLDivElement>);
onMounted(()=>{
  fetchData();
});
</script>    