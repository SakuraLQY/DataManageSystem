<template>
    <div ref="chartRef" :style="{ height, width }"></div>
  </template>
  <script lang = "ts" setup>
  import { onMounted, ref, Ref,watch ,inject} from 'vue';
  import { useECharts } from '/@/hooks/web/useECharts';
  import { basicProps } from './props';
  import { queryList } from '../CtDailySummary.api';
  import { Item } from '../CtDailySummary.data';

  
  const params = inject('params');
  const dailyData = inject('data');

  const responseData:any = ref([]);
  responseData.value = dailyData;



  
  //拿到今天和昨天的数据
  // const fetchData = async()=>{
  // const todayData = await queryList(params);
  // responseData.value = todayData;
  // }
  //Echart数据的展示
  const ToDayData = ref([]);
  const yesterDayData = ref([]);
  
  watch(responseData,(newData)=>{
      if(newData[0][0].level==='充值金额'){
        ToDayData.value = [ parseInt(newData[0][0].zeroHour), parseInt(newData[0][0].oneHour),parseInt(newData[0][0].twoHour), parseInt(newData[0][0].threeHour),parseInt(newData[0][0].fourHour),parseInt(newData[0][0].fiveHour), parseInt(newData[0][0].sixHour),
        parseInt(newData[0][0].sevenHour), parseInt(newData[0][0].eightHour), parseInt(newData[0][0].nineHour),parseInt(newData[0][0].tenHour),  parseInt(newData[0][0].elevenHour),parseInt(newData[0][0].twelveHour),parseInt(newData[0][0].thirteenHour), parseInt(newData[0][0].fourteenHour),
        parseInt(newData[0][0].fifteenHour), parseInt(newData[0][0].sixteenHour), parseInt(newData[0][0].seventeenHour), parseInt(newData[0][0].eighteenHour), parseInt(newData[0][0].nineteenHour), parseInt(newData[0][0].twentyHour),
        parseInt(newData[0][0].twentyoneHour),parseInt(newData[0][0].twentytwoHour), parseInt(newData[0][0].twentythreeHour)]
      }
      if(newData[1][0].level==='充值金额'){
        yesterDayData.value = [parseInt(newData[1][0].zeroHour), parseInt(newData[1][0].oneHour),parseInt(newData[1][0].twoHour), parseInt(newData[1][0].threeHour),parseInt(newData[1][0].fourHour),parseInt(newData[1][0].fiveHour), parseInt(newData[1][0].sixHour),
        parseInt(newData[1][0].sevenHour), parseInt(newData[1][0].eightHour), parseInt(newData[1][0].nineHour),parseInt(newData[1][0].tenHour),  parseInt(newData[1][0].elevenHour),parseInt(newData[1][0].twelveHour),parseInt(newData[1][0].thirteenHour), parseInt(newData[1][0].fourteenHour),
        parseInt(newData[1][0].fifteenHour), parseInt(newData[1][0].sixteenHour), parseInt(newData[1][0].seventeenHour), parseInt(newData[1][0].eighteenHour), parseInt(newData[1][0].nineteenHour), parseInt(newData[1][0].twentyHour),
        parseInt(newData[1][0].twentyoneHour),parseInt(newData[1][0].twentytwoHour), parseInt(newData[1][0].twentythreeHour)]
      }
    setOptions({
    title: {
    text: '充值金额',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: [
      '今日充值金额',
      '昨日充值金额'
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
    name: '充值金额(元)',
    nameLocation: 'middle',
    nameGap: 50
  },
  series: [
    {
      name: '今日充值金额',
      type: 'line',
      data: ToDayData.value

    },
    {
      name: '昨日充值金额',
      type: 'line',
      data:yesterDayData.value 
      // [
      //   979, 933, 841, 814, 1040, 1130, 1220, 1140, 1045, 1234, 1123, 1213,
      //   1251, 1000, 890, 830, 800, 670, 900, 670, 600, 580, 450
      // ]
    },
  ]
});
  }
 );

  defineProps({
    ...basicProps,
  });
  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions } = useECharts(chartRef as Ref<HTMLDivElement>);
// onMounted(()=>{
//   fetchData()
// });
</script>    