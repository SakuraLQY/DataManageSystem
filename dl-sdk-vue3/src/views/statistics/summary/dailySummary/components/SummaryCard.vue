<template>
  <div class="md:flex">
    <template v-for="(item, index) in growDailyCard" :key="item.title">
      <Card
        size="small"
        :loading="loading"
        :title="item.title"
        class="md:w-1/4 w-full !md:mt-0 !mt-4"
        :class="[index + 1 < 5 && '!md:mr-4']"
        @click = "showCard(index)"
        :style="colors==index?'background-color:#dbecf1':''"
        :canExpan="false"
      >
        <div class="py-4 px-4 flex justify-between">
          <Icon :icon="item.icon" :size="40" />
          <span :style="{ fontSize: '30px' ,color:'red' }">{{ item.value }}</span>
        </div>

        <div class="p-2 px-4 flex justify-between">
          <span :style="{ fontSize: '16px'}">{{ item.compare }}</span>
          <!-- <CountTo prefix="$" :startVal="1" :endVal="item.total" /> -->
          <span :style="{ fontSize: '16px'}">{{item.percent}}</span>
        </div>
      </Card>
    </template>
  </div>
</template>
<script lang="ts" setup>


import { GrowCardItems } from '../CtDailySummary.data';
import { Icon } from '/@/components/Icon';
import { Card } from 'ant-design-vue';
import { defineEmits,ref,inject,onMounted,watch } from 'vue';
import{queryList} from '../CtDailySummary.api'
import { parseString } from 'cron-parser';


const totalMoneyPercent = ref(0);
const payUserPercent = ref(0);
const newPayUserPercent =ref(0);
const countUserPercent = ref(0);
const onlineUserPercent = ref(0);

const totalMoney = ref(0);
const payser = ref(0);
const newPayuser = ref(0);
const onlineUser = ref(0);
const countUser = ref(0);

//z注入响应式对象
const params = inject('params')




async function fetchData() {
  const todayData = await queryList(params);
  
    //计算充值金额的数据
  const total1 = todayData[0][0].total;
  // debugger
  const total2 = parseInt(todayData[1][0].total);
  if(total1!==0){
    const value1 = ((total1 - total2) / total1 * 100).toFixed(2) + '%';
    growDailyCard.value[0].percent =  value1;
  }

  //计算充值人数
  const total3 = parseInt(todayData[0][1].total);
  const total4 = parseInt(todayData[1][1].total);
  if(total3!==0){
    const value2 = ((total3 - total4) / total3 * 100).toFixed(2) + '%';
    growDailyCard.value[1].percent = value2;
  }
  //计算新增充值人数
  const total5 = parseInt(todayData[0][2].total);
  const total6 = parseInt(todayData[1][2].total);
  if(total5!==0){
    const value3 = ((total5 - total6) / total5 * 100).toFixed(2) + '%';
    growDailyCard.value[2].percent = value3;
  }
  //计算新增账号
  const total7 = parseInt(todayData[0][3].total);
  const total8 = parseInt(todayData[1][3].total);
  if(total7!==0){
    const personPercent = ((total7 - total8) / total7 * 100).toFixed(2) + '%';
    growDailyCard.value[3].percent =  personPercent;
  }
  //计算在线人数
  const total9 = parseInt(todayData[0][4].total);
  const total10 = parseInt(todayData[1][4].total);
  if(total9!==0){
    const onlinePercent = ((total9 - total10) / total9 * 100).toFixed(2) + '%';
    growDailyCard.value[4].percent = onlinePercent
  }

  growDailyCard.value[0].value =  todayData[0][0].total
  growDailyCard.value[1].value =  todayData[0][1].total
  growDailyCard.value[2].value = todayData[0][2].total
  growDailyCard.value[3].value = todayData[0][3].total
  growDailyCard.value[4].value = todayData[0][4].total

}
  onMounted(()=>{
    fetchData();
  
  })
  watch(
  params,
  () => {
    fetchData();
  }
);
//5张小卡片
const growDailyCard:GrowCardItems[]=ref([{
  title:'充值金额',
  icon:'ant-design:transaction-outlined',
  compare:'vs昨日',
  percent:totalMoneyPercent.value,
  value:totalMoney.value
},{
  title:'充值人数',
  icon:'solar:user-hand-up-broken',
  compare:'vs昨日',
  percent:payUserPercent.value,
  value: payser.value
}
,{
  title:'新增充值人数',
  icon:'ant-design:usergroup-add-outlined',
  compare:'vs昨日',
  percent:newPayUserPercent.value,
  value:newPayuser.value
},{
  title:'新增账号',
  icon:'ant-design:user-add-outlined',
  compare:'vs昨日',
  percent:countUserPercent.value,
  value:countUser.value
},{
  title:'在线人数',
  icon:'ant-design:team-outlined',
  compare:'vs昨日',
  percent:onlineUserPercent.value,
  value:onlineUser.value
}
])

const emit =  defineEmits(["sonDataName"]);
const colors = ref(0);
const showCard = (e)=>{
  colors.value = e;
  emit("sonDataName",e);
}
  defineProps({
    loading: {
      type: Boolean,
    },
  });
</script>