<template>
  <div ref="chartRef" :style="{ height, width }"></div>
</template>

<script lang = "ts" setup>
  import { onMounted, ref, Ref,watch ,inject} from 'vue';
  import { useECharts } from '/@/hooks/web/useECharts';
  import { basicProps } from './props';
  import { queryList} from '../CtDayAnalyze.api'
  import { Item } from '../CtDayAnalyze.data';
import { watchEffect } from 'vue';

const params = inject('params');
const responseData = ref<Item[]>([]);
const activationData = ref<number[]>([]);
const activeDevData = ref<number[]>([]);
const registryData = ref<number[]>([]);
const registryDevData = ref<number[]>([]);
const firstPayUserData = ref<number[]>([]);
const firstPayUserMoneyData= ref<number[]>([]);
const totalMoneyData = ref<number[]>([]);
const activePayData = ref<number[]>([]);
const activePayPersonData = ref<number[]>([]);
const dau = ref<number[]>([]);

const fetchData = async()=>{
    const data = await queryList(params);
    responseData.value = data;
  }
watch(params,()=>{
  fetchData();
});


watchEffect(() => {
  responseData.value.forEach((item) => {
    if (item.level === '激活数') { 
      activationData.value = [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)];
    } else if (item.level === '激活设备数') {
      activeDevData.value =[   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)];
    }
    else if(item.level==='注册数'){
      registryData.value = [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='注册设备数'){
      registryDevData.value = [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='新增付费人数'){
      firstPayUserData.value =  [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level === '新增付费金额'){
      firstPayUserMoneyData.value = [  parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='累积付费金额'){
      totalMoneyData.value = [parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='活跃付费人数'){
      activePayData.value = [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='活跃付费金额'){
      activePayPersonData.value =  [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }else if(item.level==='DAU'){
      dau.value = [   parseInt(item.zeroHour), parseInt(item.oneHour),parseInt(item.twoHour), parseInt(item.threeHour),parseInt(item.fourHour),parseInt(item.fiveHour), parseInt(item.sixHour),
        parseInt(item.sevenHour), parseInt(item.eightHour), parseInt(item.nineHour),parseInt(item.tenHour),  parseInt(item.elevenHour),parseInt(item.twelveHour),parseInt(item.thirteenHour), parseInt(item.fourteenHour),
        parseInt(item.fifteenHour), parseInt(item.sixteenHour), parseInt(item.seventeenHour), parseInt(item.eighteenHour), parseInt(item.nineteenHour), parseInt(item.twentyHour),
        parseInt(item.twentyoneHour),parseInt(item.twentytwoHour), parseInt(item.twentythreeHour)]
    }
  
    setOptions({
  title: {
    text: '分时分析数据',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: [
      '激活数',
      '激活设备数',
      '注册数',
      '注册设备数',
      '新增付费人数',
      '新增付费金额',
      '累积付费金额',
      '活跃付费人数',
      '活跃付费金额',
      'DAU'
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
    name: '数值(个)',
    nameLocation: 'middle',
    nameGap: 45
  },
  series: [
    {
      name: '激活数',
      type: 'line',
      data: activationData.value
    },
    {
      name: '激活设备数',
      type: 'line',
      data: activeDevData.value
    },
    {
      name: '注册数',
      type: 'line',
      data: registryData.value 
    },
    {
      name: '注册设备数',
      type: 'line',
      data: registryDevData.value
    },
    {
      name: '新增付费人数',
      type: 'line',
      data: firstPayUserData.value
    },
    {
      name: '新增付费金额',
      type: 'line',
      data: firstPayUserMoneyData.value
    },
    {
      name: '累积付费金额',
      type: 'line',
      data: totalMoneyData.value
    },
    {
      name: '活跃付费人数',
      type: 'line',
      data: activePayData.value
    },
    {
      name: '活跃付费金额',
      type: 'line',
      data: activePayPersonData.value
    },
    {
      name: 'DAU',
      type: 'line',
      data: dau.value
    }
  ]
});


  });
});

  defineProps({
    ...basicProps,
  });
  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions } = useECharts(chartRef as Ref<HTMLDivElement>);

onMounted(()=>{
  fetchData()
});
   
</script>

