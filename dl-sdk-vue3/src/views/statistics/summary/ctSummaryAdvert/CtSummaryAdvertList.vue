<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <a-col :lg="8">
              <a-form-item label="广告">
                <j-select-multiple placeholder="请选择广告" v-model:value="queryParam.dealId"  dictCode="open_gateway.op_deal,deal_name,id" allowClear />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.regStartTime" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.regEndTime" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="展示方式">
                <j-search-select placeholder="展示方式" v-model:value="queryParam.groupType"  dict="summary_advert_group_type" />
            </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
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
    <!-- 图表区域 -->
    <div v-if="showEchart">
      <a-row :gutter="24">
        <!-- 用户首充等级分布数据 -->
        <a-col :lg="8">
          <pie :chartData="dateRateGroup" height="50vh" :option="dateRateOption" />
        </a-col>
        <a-col :lg="8">
          <pie :chartData="aliveMoneyRateGroup" height="50vh" :option="aliveMoneyRateOption" />
        </a-col>
        <a-col :lg="8">
          <pie :chartData="firstMoneyRateGroup" height="50vh" :option="firstMoneyRateOption" />
        </a-col>
      </a-row>
    </div>
    <div v-if="showEchart" ref="chartRef" :style="{ height: '400px', width: '100%' }"></div>    
    <!--引用表格-->
    <BasicTable @register="registerTable">
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>
      <template #dealId="{record}">
        
        <div v-if="queryParam.groupType == 1">
          <a-button @click="clickDealId(record.dealId)" type="link" block>{{record.dealId }}</a-button>
        </div>
        <div v-else>
          {{record.dealId }}
        </div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{text}">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
  </div>
</template>

<script lang="ts" name="count-ctSummary" setup>
  import { ref, reactive, Ref } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './CtSummaryAdvert.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl,getSummaryAdvertEChart,getSummaryAdvertLine } from './CtSummaryAdvert.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import {formatToDate} from  '/@/utils/dateUtil'
  import Pie from '/@/components/chart/Pie.vue';
  import { useECharts } from '/@/hooks/web/useECharts';
  const showEchart =  ref<boolean>(false); //只有广告展示图表
  const selectGameForm= ref();
  const selectChannelForm= ref();
  const queryParam = ref<any>({
    regStartTime: formatToDate(new Date()),
    regEndTime: formatToDate(new Date()),
    groupType:1
  });
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const groupByDealFlag = ref<boolean>(false); // 根据广告查询flag 用于小图标展示
  const ctDealInfoDataModal = ref();
  let summaryData = ref([]);
  const dateRateGroup = ref([]);
  const aliveMoneyRateGroup = ref([]);
  const firstMoneyRateGroup = ref([]);
  const dateRateOption = ref({ 
    title: { text: '充值用户注册日期分布图', left: 'center' } 
  });
  const firstMoneyRateOption = ref({ 
    title: { text: '新增充值面额占比图', left: 'center' } 
  });
  const aliveMoneyRateOption = ref({ 
    title: { text: '活跃充值面额占比图', left: 'center' } 
  });
  // 初始化echart
  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions, getInstance } = useECharts(chartRef as Ref<HTMLDivElement>);
  // 图标的配置
  const option = reactive({
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow',
        label: {
          show: true,
          backgroundColor: '#333',
        },
      },
    },
    legend: { top: 'bottom' },
    grid: {
      top: 60,
    },
    title: { text: '分时数据', left: 'center'},
    yAxis: [{
      type: 'value',
      name: '人数',
      nameLocation: 'middle',
      nameGap: 45
    },
    {
      type: 'value',
      name: '单价',
      nameLocation: 'middle',
      nameGap: 45
    }],
    xAxis:{
      inverse: true //反向坐标
    },
    series: [],
  });
  setOptions(option);
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      pagination: false, // 是否分页
      showActionColumn: false,
      clickToRowSelect:true,
      showSummary:false, // 是否显示合计行
      summaryData: summaryData,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },afterFetch: (data) =>{
        // 最后一个值是汇总合计
        // summaryData.value = [data.pop()];
        // 设置图表
        showEchart.value = false;
        if(queryParam.value.dealId){
         
         // 饼图
          getSummaryAdvertEChart(queryParam.value).then(res=>{
            showEchart.value = true;
            // 设置首次付费和活跃付费
            dateRateGroup.value = [];
            aliveMoneyRateGroup.value = [];
            firstMoneyRateGroup.value = [];
              debugger
            if(res && res.dateRateGroup){
              res.dateRateGroup.forEach((item)=>{
                dateRateGroup.value.push({name:`日期:${item.day}，数量:${item.count}`, value:item.rate});
              })
            }
            if(res && res.aliveMoneyRateGroup){
              res.aliveMoneyRateGroup.forEach((item)=>{
                aliveMoneyRateGroup.value.push({name:`金额:${item.money}，数量:${item.count}`, value:item.rate});
              })
            }
            if(res && res.firstMoneyRateGroup){
              res.firstMoneyRateGroup.forEach((item)=>{
                firstMoneyRateGroup.value.push({name:`金额:${item.money}，数量:${item.count}`, value:item.rate});
              })
            }
          })
          // 折线图

          getSummaryAdvertLine(queryParam.value).then(res=>{
            option.xAxis.data = [];
            option.series = [];
            let xData = [];
            let countActiveLine = {name:"激活数",type:"line",data:[]}; 
            let countActiveDevLine = {name:"激活设备数",type:"line",data:[]}; 
            let countUserLine = {name:"注册数",type:"line",data:[]}; 
            let countUserDevLine = {name:"注册设备数",type:"line",data:[]}; 
            let firstPayuserLine = {name:"新增付费人数",type:"line",data:[]}; 
            let firstMoneyLine = {name:"新增付费金额",type:"line",data:[],yAxisIndex: 1,}; 
            let totalMoneyLine = {name:"累计付费金额",type:"line",data:[],yAxisIndex: 1,}; 
            let totalPayuserLine = {name:"累计付费人数",type:"line",data:[]}; 
            let aliveMoneyLine = {name:"累计付费人数",type:"line",data:[],yAxisIndex: 1,}; 
            let alivePayuserLine = {name:"活跃付费人数",type:"line",data:[]}; 
            let countDauLine = {name:"DAU",type:"line",data:[]}; 
            if(res.count_active){
              for(const key in res.count_active){
                xData.push(key);
                countActiveLine.data.push(res.count_active[key]);
              }
            }
            if(res.count_active_dev){
              for(const key in res.count_active_dev){
                countActiveDevLine.data.push(res.count_active_dev[key]);
              }
            }
            if(res.count_user){
              for(const key in res.count_user){
                countUserLine.data.push(res.count_user[key]);
              }
            }
            if(res.count_user_dev){
              for(const key in res.count_user_dev){
                countUserDevLine.data.push(res.count_user_dev[key]);
              }
            }
            if(res.first_payuser){
              for(const key in res.first_payuser){
                firstPayuserLine.data.push(res.first_payuser[key]);
              }
            }
            if(res.first_money){
              for(const key in res.first_money){
                firstMoneyLine.data.push(res.first_money[key]);
              }
            }    
            if(res.total_money){
              for(const key in res.total_money){
                totalMoneyLine.data.push(res.total_money[key]);
              }
            }       
            if(res.total_payuser){
              for(const key in res.total_payuser){
                totalPayuserLine.data.push(res.total_payuser[key]);
              }
            }
            if(res.alive_money){
              for(const key in res.alive_money){
                aliveMoneyLine.data.push(res.alive_money[key]);
              }
            }
            if(res.alive_payuser){
              for(const key in res.alive_payuser){
                alivePayuserLine.data.push(res.alive_payuser[key]);
              }
            }
            if(res.count_dau){
              for(const key in res.count_dau){
                countDauLine.data.push(res.count_dau[key]);
              }
            }
            option.series.push(countActiveLine,countActiveDevLine,countActiveDevLine,countUserLine,countUserDevLine,
            firstPayuserLine,firstMoneyLine,totalMoneyLine,totalPayuserLine,aliveMoneyLine,alivePayuserLine,countDauLine);
            option.xAxis.data = xData;
            setOptions(option);
          })
        }
      },
      // pagination:{ pageSize: 20 }
    },
    exportConfig: {
      name: "ct_summary",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });
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
    if(queryParam.value.groupType == 8) {
      groupByDealFlag.value = true;
    }else{
      groupByDealFlag.value = false;
    }
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {
      regStartTime: formatToDate(new Date()),
      regEndTime: formatToDate(new Date()),
      groupType:1
    };
    // selectedRowKeys.value = [];
    if(queryParam.value.groupType == 8) {
      groupByDealFlag.value = true;
    }else{
      groupByDealFlag.value = false;
    }
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    //刷新数据
    reload();
  }

   function clickDealId(dealId) {
    queryParam.value.dealId = dealId;
    reload();
   }
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
