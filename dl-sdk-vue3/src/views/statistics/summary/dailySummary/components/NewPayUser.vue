<template>
    <div ref="chartRef" :style="{ height, width }"></div>
  </template>
  <script lang = "ts" setup>
  import { onMounted, ref, Ref,inject,watch } from 'vue';
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
//Echart数据的展示
const ToDayData = ref<number[]>([]);
const yesterDayData = ref<number[]>([]);

watch(
  params,
  () => {
    fetchData();
  }
);
watch(responseData,(newData)=>{
      if(newData[0][2].level==='新增充值人数'){
        ToDayData.value = [ parseInt(newData[0][2].zeroHour), parseInt(newData[0][2].oneHour),parseInt(newData[0][2].twoHour), parseInt(newData[0][2].threeHour),parseInt(newData[0][2].fourHour),parseInt(newData[0][2].fiveHour), parseInt(newData[0][2].sixHour),
        parseInt(newData[0][2].sevenHour), parseInt(newData[0][2].eightHour), parseInt(newData[0][2].nineHour),parseInt(newData[0][2].tenHour),  parseInt(newData[0][2].elevenHour),parseInt(newData[0][2].twelveHour),parseInt(newData[0][2].thirteenHour), parseInt(newData[0][2].fourteenHour),
        parseInt(newData[0][2].fifteenHour), parseInt(newData[0][2].sixteenHour), parseInt(newData[0][2].seventeenHour), parseInt(newData[0][2].eighteenHour), parseInt(newData[0][2].nineteenHour), parseInt(newData[0][2].twentyHour),
        parseInt(newData[0][2].twentyoneHour),parseInt(newData[0][2].twentytwoHour), parseInt(newData[0][2].twentythreeHour)]
      }
      if(newData[1][2].level==='新增充值人数'){
        yesterDayData.value = [parseInt(newData[1][2].zeroHour), parseInt(newData[1][2].oneHour),parseInt(newData[1][2].twoHour), parseInt(newData[1][2].threeHour),parseInt(newData[1][2].fourHour),parseInt(newData[1][2].fiveHour), parseInt(newData[1][2].sixHour),
        parseInt(newData[1][2].sevenHour), parseInt(newData[1][2].eightHour), parseInt(newData[1][2].nineHour),parseInt(newData[1][2].tenHour),  parseInt(newData[1][2].elevenHour),parseInt(newData[1][2].twelveHour),parseInt(newData[1][2].thirteenHour), parseInt(newData[1][2].fourteenHour),
        parseInt(newData[1][2].fifteenHour), parseInt(newData[1][2].sixteenHour), parseInt(newData[1][2].seventeenHour), parseInt(newData[1][2].eighteenHour), parseInt(newData[1][2].nineteenHour), parseInt(newData[1][2].twentyHour),
        parseInt(newData[1][2].twentyoneHour),parseInt(newData[1][2].twentytwoHour), parseInt(newData[1][2].twentythreeHour)]
      }
      setOptions({
    title: {
    text: '新增充值人数',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: [
      '今日新增充值人数',
      '昨日新增充值人数'
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
    name: '新增充值人数',
    nameLocation: 'middle',
    nameGap: 45
  },
  series: [
    {
      name: '今日新增充值人数',
      type: 'line',
      data: ToDayData.value
    },
    {
      name: '昨日新增充值人数',
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