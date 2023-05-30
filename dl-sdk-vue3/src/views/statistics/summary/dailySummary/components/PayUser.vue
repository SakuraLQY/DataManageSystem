<template>
    <div ref="chartRef" :style="{ height, width }"></div>
  </template>
  <script lang = "ts" setup>
  import { onMounted, ref, Ref ,inject,watch,watchEffect} from 'vue';
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
      if(newData[0][1].level==='充值人数'){
        ToDayData.value = [ parseInt(newData[0][1].zeroHour), parseInt(newData[0][1].oneHour),parseInt(newData[0][1].twoHour), parseInt(newData[0][1].threeHour),parseInt(newData[0][1].fourHour),parseInt(newData[0][1].fiveHour), parseInt(newData[0][1].sixHour),
        parseInt(newData[0][1].sevenHour), parseInt(newData[0][1].eightHour), parseInt(newData[0][1].nineHour),parseInt(newData[0][1].tenHour),  parseInt(newData[0][1].elevenHour),parseInt(newData[0][1].twelveHour),parseInt(newData[0][1].thirteenHour), parseInt(newData[0][1].fourteenHour),
        parseInt(newData[0][1].fifteenHour), parseInt(newData[0][1].sixteenHour), parseInt(newData[0][1].seventeenHour), parseInt(newData[0][1].eighteenHour), parseInt(newData[0][1].nineteenHour), parseInt(newData[0][1].twentyHour),
        parseInt(newData[0][1].twentyoneHour),parseInt(newData[0][1].twentytwoHour), parseInt(newData[0][1].twentythreeHour)]
      }
      if(newData[1][1].level==='充值人数'){
        yesterDayData.value = [parseInt(newData[1][1].zeroHour), parseInt(newData[1][1].oneHour),parseInt(newData[1][1].twoHour), parseInt(newData[1][1].threeHour),parseInt(newData[1][1].fourHour),parseInt(newData[1][1].fiveHour), parseInt(newData[1][1].sixHour),
        parseInt(newData[1][1].sevenHour), parseInt(newData[1][1].eightHour), parseInt(newData[1][1].nineHour),parseInt(newData[1][1].tenHour),  parseInt(newData[1][1].elevenHour),parseInt(newData[1][1].twelveHour),parseInt(newData[1][1].thirteenHour), parseInt(newData[1][1].fourteenHour),
        parseInt(newData[1][1].fifteenHour), parseInt(newData[1][1].sixteenHour), parseInt(newData[1][1].seventeenHour), parseInt(newData[1][1].eighteenHour), parseInt(newData[1][1].nineteenHour), parseInt(newData[1][1].twentyHour),
        parseInt(newData[1][1].twentyoneHour),parseInt(newData[1][1].twentytwoHour), parseInt(newData[1][1].twentythreeHour)]
      }
      setOptions({
    title: {
    text: '充值人数',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: [
      '今日充值人数',
      '昨日充值人数'
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
      name: '今日充值人数',
      type: 'line',
      data: ToDayData.value
    },
    {
      name: '昨日充值人数',
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