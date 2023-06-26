<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
             <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <a-col :lg="8">
              <a-form-item label="查询日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择创建日期" v-model:value="queryParam.createTime" />
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQueryList">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <CtDailySummaryModal ref="registerModal" @success="handleSuccess"></CtDailySummaryModal>
  </div>
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
    <!-- 显示折线图 -->
  <div v-if="lineIndex==0?true:false">
      <LineMulti :chartData="lineMultiData" height="50vh" type="line" :option="barOption" ></LineMulti>
  </div>
  <div v-if="lineIndex==1?true:false">
      <LineMulti :chartData="linePeopleData" height="50vh" type="line" :option="peopleOption" ></LineMulti>
  </div>
  <div v-if="lineIndex==2?true:false">
      <LineMulti :chartData="lineNewPeopleData" height="50vh" type="line" :option="newPeopleOption" ></LineMulti>
  </div>
  <div v-if="lineIndex==3?true:false">
      <LineMulti :chartData="lineAccountData" height="50vh" type="line" :option="accountOption" ></LineMulti>
  </div>
  <div v-if="lineIndex==4?true:false">
      <LineMulti :chartData="lineOnlineData" height="50vh" type="line" :option="onlineOption" ></LineMulti>
  </div>

</template>

<script lang="ts" name="count-ctDailySummary" setup>
  import { Icon } from '/@/components/Icon';
  import { Card } from 'ant-design-vue';
  import { ref, reactive} from 'vue';
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import { useListPage } from '/@/hooks/system/useListPage';
  import {  columns,GrowCardItems } from './CtDailySummary.data';
  import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CtDailySummary.api';
  import CtDailySummaryModal from './components/CtDailySummaryModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';
  const queryParam = ref<any>({createTime:formatToDate(new Date())});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const colors = ref(0);
  const lineIndex = ref(0);
  const showCard = (e)=>{
  colors.value = e;
  lineIndex.value = e;
}

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

//折线图配置
const lineMultiData = ref([]);
const linePeopleData = ref([]);
const lineNewPeopleData = ref([]);
const lineAccountData = ref([]);
const lineOnlineData = ref([]);


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

  //设置x,y轴对应的属性
const barOption = ref({
    title: { text: '充值金额', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '充值（元）',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  const peopleOption = ref({
    title: { text: '充值人数', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '充值人数',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  const newPeopleOption = ref({
    title: { text: '新增充值人数', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '新增充值人数',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  const accountOption = ref({
    title: { text: '新增账号', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '新增账号',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  const onlineOption = ref({
    title: { text: '在线人数', left: 'center'},
    xAxis:{
      // inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '在线人数',
      nameLocation: 'middle',
      nameGap: 45
    },
  })

  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  let getChannelVal = (e: any) => {
    queryParam.value.channelTypeId = e.channelTypeId;
    queryParam.value.channelId = e.channelId;
    queryParam.value.channelSubAccountId = e.channelSubAccountId;
  };
  let getDealVal = (e: any) => {
    queryParam.value.dealId = e.dealId;
  };
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ct_daily_summary',
      api: queryList,
      columns,
      canResize:false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      }
    },
    exportConfig: {
      name: "ct_daily_summary",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
    
  });


  searchQueryList();
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;


  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });
  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
   
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      }
    ]
  }

  /**
   * 查询
   */
   function searchQuery() {
    reload();
  }

  function searchQueryList(){
    queryList(queryParam.value).then((res:any)=>{

  let total1 = res[0][0].total;
  // debugger
  let total2 = parseInt(res[1][0].total);
  if(total1!==0){
    let value1 = ((total1 - total2) / total1 * 100).toFixed(2) + '%';
    growDailyCard.value[0].percent =  value1;
  }else{
    growDailyCard.value[0].percent = 0;
  }

  //计算充值人数
  let total3 = parseInt(res[0][1].total);
  let total4 = parseInt(res[1][1].total);
  if(total3!==0){
    let value2 = ((total3 - total4) / total3 * 100).toFixed(2) + '%';
    growDailyCard.value[1].percent = value2;
  }else{
    growDailyCard.value[1].percent = 0;
  }
  //计算新增充值人数
  let total5 = parseInt(res[0][2].total);
  let total6 = parseInt(res[1][2].total);
  if(total5!==0){
    let value3 = ((total5 - total6) / total5 * 100).toFixed(2) + '%';
    growDailyCard.value[2].percent = value3;
  }else{
    growDailyCard.value[2].percent = 0;
  }
  //计算新增账号
  let total7 = parseInt(res[0][3].total);
  let total8 = parseInt(res[1][3].total);
  if(total7!==0){
    let personPercent = ((total7 - total8) / total7 * 100).toFixed(2) + '%';
    growDailyCard.value[3].percent =  personPercent;
  }else{
    growDailyCard.value[3].percent =  0;
  }
  //计算在线人数
  let total9 = parseInt(res[0][4].total);
  let total10 = parseInt(res[1][4].total);
  if(total9!==0){
    let onlinePercent = ((total9 - total10) / total9 * 100).toFixed(2) + '%';
    growDailyCard.value[4].percent = onlinePercent
  }else{
    growDailyCard.value[4].percent = 0;
  }

  growDailyCard.value[0].value =  res[0][0].total
  growDailyCard.value[1].value =  res[0][1].total
  growDailyCard.value[2].value = res[0][2].total
  growDailyCard.value[3].value = res[0][3].total
  growDailyCard.value[4].value = res[0][4].total
     //处理折线图
  lineMultiData.value = [];
  linePeopleData.value = [];
  lineNewPeopleData.value = [];
  lineAccountData.value = [];
  lineOnlineData.value = [];

  if(res[0][0].level==="充值金额"){
    lineMultiData.value.push({name: '0时',type:"今日充值金额",value: res[0][0].zeroHour});
    lineMultiData.value.push({name: '1时',type:"今日充值金额",value: res[0][0].oneHour});
    lineMultiData.value.push({name: '2时',type:"今日充值金额",value: res[0][0].twoHour});
    lineMultiData.value.push({name: '3时',type:"今日充值金额",value: res[0][0].threeHour});
    lineMultiData.value.push({name: '4时',type:"今日充值金额",value: res[0][0].fourHour});
    lineMultiData.value.push({name: '5时',type:"今日充值金额",value: res[0][0].fiveHour});
    lineMultiData.value.push({name: '6时',type:"今日充值金额",value: res[0][0].sixHour});
    lineMultiData.value.push({name: '7时',type:"今日充值金额",value: res[0][0].sevenHour});
    lineMultiData.value.push({name: '8时',type:"今日充值金额",value: res[0][0].eightHour});
    lineMultiData.value.push({name: '9时',type:"今日充值金额",value: res[0][0].nineHour});
    lineMultiData.value.push({name: '10时',type:"今日充值金额",value: res[0][0].tenHour});
    lineMultiData.value.push({name: '11时',type:"今日充值金额",value: res[0][0].elevenHour});
    lineMultiData.value.push({name: '12时',type:"今日充值金额",value: res[0][0].twelveHour});
    lineMultiData.value.push({name: '13时',type:"今日充值金额",value: res[0][0].thirteenHour});
    lineMultiData.value.push({name: '14时',type:"今日充值金额",value: res[0][0].fourteenHour});
    lineMultiData.value.push({name: '15时',type:"今日充值金额",value: res[0][0].fifteenHour});
    lineMultiData.value.push({name: '15时',type:"今日充值金额",value: res[0][0].sixteenHour});
    lineMultiData.value.push({name: '17时',type:"今日充值金额",value: res[0][0].seventeenHour});
    lineMultiData.value.push({name: '18时',type:"今日充值金额",value: res[0][0].eighteenHour});
    lineMultiData.value.push({name: '19时',type:"今日充值金额",value: res[0][0].nineteenHour});
    lineMultiData.value.push({name: '20时',type:"今日充值金额",value: res[0][0].twentyHour});
    lineMultiData.value.push({name: '21时',type:"今日充值金额",value: res[0][0].twentyoneHour});
    lineMultiData.value.push({name: '22时',type:"今日充值金额",value: res[0][0].twentytwoHour});
    lineMultiData.value.push({name: '23时',type:"今日充值金额",value: res[0][0].twentythreeHour});

    lineMultiData.value.push({name: '0时',type:"昨日充值金额",value: res[1][0].zeroHour});
    lineMultiData.value.push({name: '1时',type:"昨日充值金额",value: res[1][0].oneHour});
    lineMultiData.value.push({name: '2时',type:"昨日充值金额",value: res[1][0].twoHour});
    lineMultiData.value.push({name: '3时',type:"昨日充值金额",value: res[1][0].threeHour});
    lineMultiData.value.push({name: '4时',type:"昨日充值金额",value: res[1][0].fourHour});
    lineMultiData.value.push({name: '5时',type:"昨日充值金额",value: res[1][0].fiveHour});
    lineMultiData.value.push({name: '6时',type:"昨日充值金额",value: res[1][0].sixHour});
    lineMultiData.value.push({name: '7时',type:"昨日充值金额",value: res[1][0].sevenHour});
    lineMultiData.value.push({name: '8时',type:"昨日充值金额",value: res[1][0].eightHour});
    lineMultiData.value.push({name: '9时',type:"昨日充值金额",value: res[1][0].nineHour});
    lineMultiData.value.push({name: '10时',type:"昨日充值金额",value: res[1][0].tenHour});
    lineMultiData.value.push({name: '11时',type:"昨日充值金额",value: res[1][0].elevenHour});
    lineMultiData.value.push({name: '12时',type:"昨日充值金额",value: res[1][0].twelveHour});
    lineMultiData.value.push({name: '13时',type:"昨日充值金额",value: res[1][0].thirteenHour});
    lineMultiData.value.push({name: '14时',type:"昨日充值金额",value: res[1][0].fourteenHour});
    lineMultiData.value.push({name: '15时',type:"昨日充值金额",value: res[1][0].fifteenHour});
    lineMultiData.value.push({name: '15时',type:"昨日充值金额",value: res[1][0].sixteenHour});
    lineMultiData.value.push({name: '17时',type:"昨日充值金额",value: res[1][0].seventeenHour});
    lineMultiData.value.push({name: '18时',type:"昨日充值金额",value: res[1][0].eighteenHour});
    lineMultiData.value.push({name: '19时',type:"昨日充值金额",value: res[1][0].nineteenHour});
    lineMultiData.value.push({name: '20时',type:"昨日充值金额",value: res[1][0].twentyHour});
    lineMultiData.value.push({name: '21时',type:"昨日充值金额",value: res[1][0].twentyoneHour});
    lineMultiData.value.push({name: '22时',type:"昨日充值金额",value: res[1][0].twentytwoHour});
    lineMultiData.value.push({name: '23时',type:"昨日充值金额",value: res[1][0].twentythreeHour});
  }
  if(res[0][1].level==="充值人数"){
    linePeopleData.value.push({name: '0时',type:"今日充值人数",value: res[0][1].zeroHour});
    linePeopleData.value.push({name: '1时',type:"今日充值人数",value: res[0][1].oneHour});
    linePeopleData.value.push({name: '2时',type:"今日充值人数",value: res[0][1].twoHour});
    linePeopleData.value.push({name: '3时',type:"今日充值人数",value: res[0][1].threeHour});
    linePeopleData.value.push({name: '4时',type:"今日充值人数",value: res[0][1].fourHour});
    linePeopleData.value.push({name: '5时',type:"今日充值人数",value: res[0][1].fiveHour});
    linePeopleData.value.push({name: '6时',type:"今日充值人数",value: res[0][1].sixHour});
    linePeopleData.value.push({name: '7时',type:"今日充值人数",value: res[0][1].sevenHour});
    linePeopleData.value.push({name: '8时',type:"今日充值人数",value: res[0][1].eightHour});
    linePeopleData.value.push({name: '9时',type:"今日充值人数",value: res[0][1].nineHour});
    linePeopleData.value.push({name: '10时',type:"今日充值人数",value: res[0][1].tenHour});
    linePeopleData.value.push({name: '11时',type:"今日充值人数",value: res[0][1].elevenHour});
    linePeopleData.value.push({name: '12时',type:"今日充值人数",value: res[0][1].twelveHour});
    linePeopleData.value.push({name: '13时',type:"今日充值人数",value: res[0][1].thirteenHour});
    linePeopleData.value.push({name: '14时',type:"今日充值人数",value: res[0][1].fourteenHour});
    linePeopleData.value.push({name: '15时',type:"今日充值人数",value: res[0][1].fifteenHour});
    linePeopleData.value.push({name: '15时',type:"今日充值人数",value: res[0][1].sixteenHour});
    linePeopleData.value.push({name: '17时',type:"今日充值人数",value: res[0][1].seventeenHour});
    linePeopleData.value.push({name: '18时',type:"今日充值人数",value: res[0][1].eighteenHour});
    linePeopleData.value.push({name: '19时',type:"今日充值人数",value: res[0][1].nineteenHour});
    linePeopleData.value.push({name: '20时',type:"今日充值人数",value: res[0][1].twentyHour});
    linePeopleData.value.push({name: '21时',type:"今日充值人数",value: res[0][1].twentyoneHour});
    linePeopleData.value.push({name: '22时',type:"今日充值人数",value: res[0][1].twentytwoHour});
    linePeopleData.value.push({name: '23时',type:"今日充值人数",value: res[0][1].twentythreeHour});

    linePeopleData.value.push({name: '0时',type:"昨日充值人数",value: res[1][1].zeroHour});
    linePeopleData.value.push({name: '1时',type:"昨日充值人数",value: res[1][1].oneHour});
    linePeopleData.value.push({name: '2时',type:"昨日充值人数",value: res[1][1].twoHour});
    linePeopleData.value.push({name: '3时',type:"昨日充值人数",value: res[1][1].threeHour});
    linePeopleData.value.push({name: '4时',type:"昨日充值人数",value: res[1][1].fourHour});
    linePeopleData.value.push({name: '5时',type:"昨日充值人数",value: res[1][1].fiveHour});
    linePeopleData.value.push({name: '6时',type:"昨日充值人数",value: res[1][1].sixHour});
    linePeopleData.value.push({name: '7时',type:"昨日充值人数",value: res[1][1].sevenHour});
    linePeopleData.value.push({name: '8时',type:"昨日充值人数",value: res[1][1].eightHour});
    linePeopleData.value.push({name: '9时',type:"昨日充值人数",value: res[1][1].nineHour});
    linePeopleData.value.push({name: '10时',type:"昨日充值人数",value: res[1][1].tenHour});
    linePeopleData.value.push({name: '11时',type:"昨日充值人数",value: res[1][1].elevenHour});
    linePeopleData.value.push({name: '12时',type:"昨日充值人数",value: res[1][1].twelveHour});
    linePeopleData.value.push({name: '13时',type:"昨日充值人数",value: res[1][1].thirteenHour});
    linePeopleData.value.push({name: '14时',type:"昨日充值人数",value: res[1][1].fourteenHour});
    linePeopleData.value.push({name: '15时',type:"昨日充值人数",value: res[1][1].fifteenHour});
    linePeopleData.value.push({name: '15时',type:"昨日充值人数",value: res[1][1].sixteenHour});
    linePeopleData.value.push({name: '17时',type:"昨日充值人数",value: res[1][1].seventeenHour});
    linePeopleData.value.push({name: '18时',type:"昨日充值人数",value: res[1][1].eighteenHour});
    linePeopleData.value.push({name: '19时',type:"昨日充值人数",value: res[1][1].nineteenHour});
    linePeopleData.value.push({name: '20时',type:"昨日充值人数",value: res[1][1].twentyHour});
    linePeopleData.value.push({name: '21时',type:"昨日充值人数",value: res[1][1].twentyoneHour});
    linePeopleData.value.push({name: '22时',type:"昨日充值人数",value: res[1][1].twentytwoHour});
    linePeopleData.value.push({name: '23时',type:"昨日充值人数",value:  res[1][1].twentythreeHour});
  }
  if(res[0][2].level==="新增充值人数"){
    lineNewPeopleData.value.push({name: '0时',type:"今日新增充值人数",value: res[0][2].zeroHour});
    lineNewPeopleData.value.push({name: '1时',type:"今日新增充值人数",value: res[0][2].oneHour});
    lineNewPeopleData.value.push({name: '2时',type:"今日新增充值人数",value: res[0][2].twoHour});
    lineNewPeopleData.value.push({name: '3时',type:"今日新增充值人数",value: res[0][2].threeHour});
    lineNewPeopleData.value.push({name: '4时',type:"今日新增充值人数",value: res[0][2].fourHour});
    lineNewPeopleData.value.push({name: '5时',type:"今日新增充值人数",value: res[0][2].fiveHour});
    lineNewPeopleData.value.push({name: '6时',type:"今日新增充值人数",value: res[0][2].sixHour});
    lineNewPeopleData.value.push({name: '7时',type:"今日新增充值人数",value: res[0][2].sevenHour});
    lineNewPeopleData.value.push({name: '8时',type:"今日新增充值人数",value: res[0][2].eightHour});
    lineNewPeopleData.value.push({name: '9时',type:"今日新增充值人数",value: res[0][2].nineHour});
    lineNewPeopleData.value.push({name: '10时',type:"今日新增充值人数",value: res[0][2].tenHour});
    lineNewPeopleData.value.push({name: '11时',type:"今日新增充值人数",value: res[0][2].elevenHour});
    lineNewPeopleData.value.push({name: '12时',type:"今日新增充值人数",value: res[0][2].twelveHour});
    lineNewPeopleData.value.push({name: '13时',type:"今日新增充值人数",value: res[0][2].thirteenHour});
    lineNewPeopleData.value.push({name: '14时',type:"今日新增充值人数",value: res[0][2].fourteenHour});
    lineNewPeopleData.value.push({name: '15时',type:"今日新增充值人数",value: res[0][2].fifteenHour});
    lineNewPeopleData.value.push({name: '15时',type:"今日新增充值人数",value: res[0][2].sixteenHour});
    lineNewPeopleData.value.push({name: '17时',type:"今日新增充值人数",value: res[0][2].seventeenHour});
    lineNewPeopleData.value.push({name: '18时',type:"今日新增充值人数",value: res[0][2].eighteenHour});
    lineNewPeopleData.value.push({name: '19时',type:"今日新增充值人数",value: res[0][2].nineteenHour});
    lineNewPeopleData.value.push({name: '20时',type:"今日新增充值人数",value: res[0][2].twentyHour});
    lineNewPeopleData.value.push({name: '21时',type:"今日新增充值人数",value: res[0][2].twentyoneHour});
    lineNewPeopleData.value.push({name: '22时',type:"今日新增充值人数",value: res[0][2].twentytwoHour});
    lineNewPeopleData.value.push({name: '23时',type:"今日新增充值人数",value: res[0][2].twentythreeHour});

    lineNewPeopleData.value.push({name: '0时',type:"昨日新增充值人数",value: res[1][2].zeroHour});
    lineNewPeopleData.value.push({name: '1时',type:"昨日新增充值人数",value: res[1][2].oneHour});
    lineNewPeopleData.value.push({name: '2时',type:"昨日新增充值人数",value: res[1][2].twoHour});
    lineNewPeopleData.value.push({name: '3时',type:"昨日新增充值人数",value: res[1][2].threeHour});
    lineNewPeopleData.value.push({name: '4时',type:"昨日新增充值人数",value: res[1][2].fourHour});
    lineNewPeopleData.value.push({name: '5时',type:"昨日新增充值人数",value: res[1][2].fiveHour});
    lineNewPeopleData.value.push({name: '6时',type:"昨日新增充值人数",value: res[1][2].sixHour});
    lineNewPeopleData.value.push({name: '7时',type:"昨日新增充值人数",value: res[1][2].sevenHour});
    lineNewPeopleData.value.push({name: '8时',type:"昨日新增充值人数",value: res[1][2].eightHour});
    lineNewPeopleData.value.push({name: '9时',type:"昨日新增充值人数",value: res[1][2].nineHour});
    lineNewPeopleData.value.push({name: '10时',type:"昨日新增充值人数",value: res[1][2].tenHour});
    lineNewPeopleData.value.push({name: '11时',type:"昨日新增充值人数",value: res[1][2].elevenHour});
    lineNewPeopleData.value.push({name: '12时',type:"昨日新增充值人数",value: res[1][2].twelveHour});
    lineNewPeopleData.value.push({name: '13时',type:"昨日新增充值人数",value: res[1][2].thirteenHour});
    lineNewPeopleData.value.push({name: '14时',type:"昨日新增充值人数",value: res[1][2].fourteenHour});
    lineNewPeopleData.value.push({name: '15时',type:"昨日新增充值人数",value: res[1][2].fifteenHour});
    lineNewPeopleData.value.push({name: '15时',type:"昨日新增充值人数",value: res[1][2].sixteenHour});
    lineNewPeopleData.value.push({name: '17时',type:"昨日新增充值人数",value: res[1][2].seventeenHour});
    lineNewPeopleData.value.push({name: '18时',type:"昨日新增充值人数",value: res[1][2].eighteenHour});
    lineNewPeopleData.value.push({name: '19时',type:"昨日新增充值人数",value: res[1][2].nineteenHour});
    lineNewPeopleData.value.push({name: '20时',type:"昨日新增充值人数",value: res[1][2].twentyHour});
    lineNewPeopleData.value.push({name: '21时',type:"昨日新增充值人数",value: res[1][2].twentyoneHour});
    lineNewPeopleData.value.push({name: '22时',type:"昨日新增充值人数",value: res[1][2].twentytwoHour});
    lineNewPeopleData.value.push({name: '23时',type:"昨日新增充值人数",value:  res[1][2].twentythreeHour});
}
if(res[0][3].level==="新增账号"){
  lineAccountData.value.push({name: '0时',type:"今日新增账号",value: res[0][3].zeroHour});
    lineAccountData.value.push({name: '1时',type:"今日新增账号",value: res[0][3].oneHour});
    lineAccountData.value.push({name: '2时',type:"今日新增账号",value: res[0][3].twoHour});
    lineAccountData.value.push({name: '3时',type:"今日新增账号",value: res[0][3].threeHour});
    lineAccountData.value.push({name: '4时',type:"今日新增账号",value: res[0][3].fourHour});
    lineAccountData.value.push({name: '5时',type:"今日新增账号",value: res[0][3].fiveHour});
    lineAccountData.value.push({name: '6时',type:"今日新增账号",value: res[0][3].sixHour});
    lineAccountData.value.push({name: '7时',type:"今日新增账号",value: res[0][3].sevenHour});
    lineAccountData.value.push({name: '8时',type:"今日新增账号",value: res[0][3].eightHour});
    lineAccountData.value.push({name: '9时',type:"今日新增账号",value: res[0][3].nineHour});
    lineAccountData.value.push({name: '10时',type:"今日新增账号",value: res[0][3].tenHour});
    lineAccountData.value.push({name: '11时',type:"今日新增账号",value: res[0][3].elevenHour});
    lineAccountData.value.push({name: '12时',type:"今日新增账号",value: res[0][3].twelveHour});
    lineAccountData.value.push({name: '13时',type:"今日新增账号",value: res[0][3].thirteenHour});
    lineAccountData.value.push({name: '14时',type:"今日新增账号",value: res[0][3].fourteenHour});
    lineAccountData.value.push({name: '15时',type:"今日新增账号",value: res[0][3].fifteenHour});
    lineAccountData.value.push({name: '15时',type:"今日新增账号",value: res[0][3].sixteenHour});
    lineAccountData.value.push({name: '17时',type:"今日新增账号",value: res[0][3].seventeenHour});
    lineAccountData.value.push({name: '18时',type:"今日新增账号",value: res[0][3].eighteenHour});
    lineAccountData.value.push({name: '19时',type:"今日新增账号",value: res[0][3].nineteenHour});
    lineAccountData.value.push({name: '20时',type:"今日新增账号",value: res[0][3].twentyHour});
    lineAccountData.value.push({name: '21时',type:"今日新增账号",value: res[0][3].twentyoneHour});
    lineAccountData.value.push({name: '22时',type:"今日新增账号",value: res[0][3].twentytwoHour});
    lineAccountData.value.push({name: '23时',type:"今日新增账号",value: res[0][3].twentythreeHour});

    lineAccountData.value.push({name: '0时',type:"昨日新增账号",value: res[1][3].zeroHour});
    lineAccountData.value.push({name: '1时',type:"昨日新增账号",value: res[1][3].oneHour});
    lineAccountData.value.push({name: '2时',type:"昨日新增账号",value: res[1][3].twoHour});
    lineAccountData.value.push({name: '3时',type:"昨日新增账号",value: res[1][3].threeHour});
    lineAccountData.value.push({name: '4时',type:"昨日新增账号",value: res[1][3].fourHour});
    lineAccountData.value.push({name: '5时',type:"昨日新增账号",value: res[1][3].fiveHour});
    lineAccountData.value.push({name: '6时',type:"昨日新增账号",value: res[1][3].sixHour});
    lineAccountData.value.push({name: '7时',type:"昨日新增账号",value: res[1][3].sevenHour});
    lineAccountData.value.push({name: '8时',type:"昨日新增账号",value: res[1][3].eightHour});
    lineAccountData.value.push({name: '9时',type:"昨日新增账号",value: res[1][3].nineHour});
    lineAccountData.value.push({name: '10时',type:"昨日新增账号",value: res[1][3].tenHour});
    lineAccountData.value.push({name: '11时',type:"昨日新增账号",value: res[1][3].elevenHour});
    lineAccountData.value.push({name: '12时',type:"昨日新增账号",value: res[1][3].twelveHour});
    lineAccountData.value.push({name: '13时',type:"昨日新增账号",value: res[1][3].thirteenHour});
    lineAccountData.value.push({name: '14时',type:"昨日新增账号",value: res[1][3].fourteenHour});
    lineAccountData.value.push({name: '15时',type:"昨日新增账号",value: res[1][3].fifteenHour});
    lineAccountData.value.push({name: '15时',type:"昨日新增账号",value: res[1][3].sixteenHour});
    lineAccountData.value.push({name: '17时',type:"昨日新增账号",value: res[1][3].seventeenHour});
    lineAccountData.value.push({name: '18时',type:"昨日新增账号",value: res[1][3].eighteenHour});
    lineAccountData.value.push({name: '19时',type:"昨日新增账号",value: res[1][3].nineteenHour});
    lineAccountData.value.push({name: '20时',type:"昨日新增账号",value: res[1][3].twentyHour});
    lineAccountData.value.push({name: '21时',type:"昨日新增账号",value: res[1][3].twentyoneHour});
    lineAccountData.value.push({name: '22时',type:"昨日新增账号",value: res[1][3].twentytwoHour});
    lineAccountData.value.push({name: '23时',type:"昨日新增账号",value:  res[1][3].twentythreeHour});
}
if(res[0][4].level==="在线人数"){
  lineOnlineData.value.push({name: '0时',type:"今日在线人数",value: res[0][4].zeroHour});
    lineOnlineData.value.push({name: '1时',type:"今日在线人数",value: res[0][4].oneHour});
    lineOnlineData.value.push({name: '2时',type:"今日在线人数",value: res[0][4].twoHour});
    lineOnlineData.value.push({name: '3时',type:"今日在线人数",value: res[0][4].threeHour});
    lineOnlineData.value.push({name: '4时',type:"今日在线人数",value: res[0][4].fourHour});
    lineOnlineData.value.push({name: '5时',type:"今日在线人数",value: res[0][4].fiveHour});
    lineOnlineData.value.push({name: '6时',type:"今日在线人数",value: res[0][4].sixHour});
    lineOnlineData.value.push({name: '7时',type:"今日在线人数",value: res[0][4].sevenHour});
    lineOnlineData.value.push({name: '8时',type:"今日在线人数",value: res[0][4].eightHour});
    lineOnlineData.value.push({name: '9时',type:"今日在线人数",value: res[0][4].nineHour});
    lineOnlineData.value.push({name: '10时',type:"今日在线人数",value: res[0][4].tenHour});
    lineOnlineData.value.push({name: '11时',type:"今日在线人数",value: res[0][4].elevenHour});
    lineOnlineData.value.push({name: '12时',type:"今日在线人数",value: res[0][4].twelveHour});
    lineOnlineData.value.push({name: '13时',type:"今日在线人数",value: res[0][4].thirteenHour});
    lineOnlineData.value.push({name: '14时',type:"今日在线人数",value: res[0][4].fourteenHour});
    lineOnlineData.value.push({name: '15时',type:"今日在线人数",value: res[0][4].fifteenHour});
    lineOnlineData.value.push({name: '15时',type:"今日在线人数",value: res[0][4].sixteenHour});
    lineOnlineData.value.push({name: '17时',type:"今日在线人数",value: res[0][4].seventeenHour});
    lineOnlineData.value.push({name: '18时',type:"今日在线人数",value: res[0][4].eighteenHour});
    lineOnlineData.value.push({name: '19时',type:"今日在线人数",value: res[0][4].nineteenHour});
    lineOnlineData.value.push({name: '20时',type:"今日在线人数",value: res[0][4].twentyHour});
    lineOnlineData.value.push({name: '21时',type:"今日在线人数",value: res[0][4].twentyoneHour});
    lineOnlineData.value.push({name: '22时',type:"今日在线人数",value: res[0][4].twentytwoHour});
    lineOnlineData.value.push({name: '23时',type:"今日在线人数",value: res[0][4].twentythreeHour});

    lineOnlineData.value.push({name: '0时',type:"昨日在线人数",value: res[1][4].zeroHour});
    lineOnlineData.value.push({name: '1时',type:"昨日在线人数",value: res[1][4].oneHour});
    lineOnlineData.value.push({name: '2时',type:"昨日在线人数",value: res[1][4].twoHour});
    lineOnlineData.value.push({name: '3时',type:"昨日在线人数",value: res[1][4].threeHour});
    lineOnlineData.value.push({name: '4时',type:"昨日在线人数",value: res[1][4].fourHour});
    lineOnlineData.value.push({name: '5时',type:"昨日在线人数",value: res[1][4].fiveHour});
    lineOnlineData.value.push({name: '6时',type:"昨日在线人数",value: res[1][4].sixHour});
    lineOnlineData.value.push({name: '7时',type:"昨日在线人数",value: res[1][4].sevenHour});
    lineOnlineData.value.push({name: '8时',type:"昨日在线人数",value: res[1][4].eightHour});
    lineOnlineData.value.push({name: '9时',type:"昨日在线人数",value: res[1][4].nineHour});
    lineOnlineData.value.push({name: '10时',type:"昨日在线人数",value: res[1][4].tenHour});
    lineOnlineData.value.push({name: '11时',type:"昨日在线人数",value: res[1][4].elevenHour});
    lineOnlineData.value.push({name: '12时',type:"昨日在线人数",value: res[1][4].twelveHour});
    lineOnlineData.value.push({name: '13时',type:"昨日在线人数",value: res[1][4].thirteenHour});
    lineOnlineData.value.push({name: '14时',type:"昨日在线人数",value: res[1][4].fourteenHour});
    lineOnlineData.value.push({name: '15时',type:"昨日在线人数",value: res[1][4].fifteenHour});
    lineOnlineData.value.push({name: '15时',type:"昨日在线人数",value: res[1][4].sixteenHour});
    lineOnlineData.value.push({name: '17时',type:"昨日在线人数",value: res[1][4].seventeenHour});
    lineOnlineData.value.push({name: '18时',type:"昨日在线人数",value: res[1][4].eighteenHour});
    lineOnlineData.value.push({name: '19时',type:"昨日在线人数",value: res[1][4].nineteenHour});
    lineOnlineData.value.push({name: '20时',type:"昨日在线人数",value: res[1][4].twentyHour});
    lineOnlineData.value.push({name: '21时',type:"昨日在线人数",value: res[1][4].twentyoneHour});
    lineOnlineData.value.push({name: '22时',type:"昨日在线人数",value: res[1][4].twentytwoHour});
    lineOnlineData.value.push({name: '23时',type:"昨日在线人数",value:  res[1][4].twentythreeHour});
}
    })
  } 
  
    
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {createTime:formatToDate(new Date())};
    selectedRowKeys.value = [];
    //刷新数据
    // selectGameForm.value.reload();
    // selectChannelForm.value.reload();
    reload();
  }
  defineProps({
    loading: {
      type: Boolean,
    },
  });
</script>

<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      width: calc(50% - 15px);
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
  }
</style>
