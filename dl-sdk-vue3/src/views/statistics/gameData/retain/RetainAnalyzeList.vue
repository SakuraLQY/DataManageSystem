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
            <a-form-item label="广告列表">
              <j-search-select placeholder="请选择广告" v-model:value="queryParam.dealId"  dict="open_gateway.op_deal,deal_name,id" allowClear />
            </a-form-item>
          </a-col>
            <a-col :lg="8">
              <a-form-item label="留存类型">
                <j-dict-select-tag  placeholder="请输入留存类型" v-model:value="queryParam.retainType" :options = "retainOption"/>
              </a-form-item>
            </a-col>
            <a-col :lg="8" v-if="queryParam.retainType==='newLoyal'">
              <a-form-item label="充值区间">
                <a-input placeholder="请输入充值区间" v-model:value="queryParam.begSection"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8" v-if="queryParam.retainType==='newLoyal'">
              <a-form-item label="区间结束" >
                <a-input placeholder="请输入区间结束" v-model:value="queryParam.endSection"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="起始时间">
                <a-date-picker placeholder="请输入起始时间" valueFormat="YYYY-MM-DD" v-model:value="queryParam.startTime"></a-date-picker>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="结束时间">
                <a-date-picker placeholder="请输入结束时间" valueFormat="YYYY-MM-DD" v-model:value="queryParam.endTime"></a-date-picker>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="充值限期">
                <a-date-picker placeholder="请输入充值限期" valueFormat="YYYY-MM-DD" v-model:value="queryParam.costTime"></a-date-picker>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="账号人员">
                <j-search-select v-model:value="queryParam.createUser"  dict="sys_user,realname,username" placeholder="请选择账号人员" />
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
    <div v-if="queryParam.retainType==='newLoyal'">
       <LineMulti  :chartData="lineMultiData" height="50vh" type="line" :option="barOption"></LineMulti>
    </div>
    <div v-if="queryParam.retainType==='loyal'">
       <LineMulti  :chartData="lineMultiData" height="50vh" type="line" :option="barOption"></LineMulti>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection" :indexColumnProps="columns"  >
      <!--插槽:table标题-->
      <!-- <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template> -->
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <!-- <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template> -->
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{text}">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <!-- <RetainAnalyzeModal ref="registerModal" @success="handleSuccess"></RetainAnalyzeModal> -->
  </div>
</template>

<script lang="ts" name="count-retainAnalyze" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { retainOption } from './RetainAnalyze.data';
  import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './RetainAnalyze.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import RetainAnalyzeModal from './components/RetainAnalyzeModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import {formatToDates } from '/@/utils/dateUtil';
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import {BasicColumn} from '/@/components/Table';
  const lineMultiData = ref([]);
  const queryParam = ref<any>({retainType:'newLoyal',startTime:formatToDates(new Date()),endTime:formatToDate(new Date()),costTime:formatToDate(new Date())});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  //查询游戏名等进行联动的
  const selectGameForm= ref();
  const selectChannelForm= ref();
  let summaryData = ref([])
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
  
  const barOption = ref({
    title: { text: '', left: 'center'},
     yAxis: {
      type: 'value',
      name: '百分比(%)',
      nameLocation: 'middle',
      nameGap: 45
    },
    xAxis:{
      inverse: true //反向坐标
    },
    legend: { top: 'bottom' }
  })
  const loyalOption = ref({
    title: { text: '', left: 'center'},
     yAxis: {
      type: 'value',
      name: '百分比(%)',
      nameLocation: 'middle',
      nameGap: 45
    },
    xAxis:{
      inverse: true //反向坐标
    },
    legend: { top: 'bottom' }
  })
let columns:any = ref([
{
    title: '日期',
    align: "center",
    dataIndex: 'dateTime'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '推广费用',
    align: "center",
    dataIndex: 'costPay'
  },
  {
    title: '新增付费用户',
    align: "center",
    dataIndex: 'firstPayUser'
  }
  ,
  {
    title: '新增付费次留',
    align: "center",
    dataIndex: 'firstPayLoyal'
  },
  {
    title: '新增付费3留',
    align: "center",
    dataIndex: 'firstPayLoyal3'
  },
  {
    title: '新增付费7留',
    align: "center",
    dataIndex: 'firstPayLoyal7'
  },
  {
    title: '新增付费15留',
    align: "center",
    dataIndex: 'firstPayLoyal15'
  },
  {
    title: '新增付费30留',
    align: "center",
    dataIndex: 'firstPayLoyal30'
  },
  {
    title: '新增付费45留',
    align: "center",
    dataIndex: 'firstPayLoyal45'
  },
  {
    title: '新增付费60留',
    align: "center",
    dataIndex: 'firstPayLoyal60'
  },
  {
    title: '新增付费90留',
    align: "center",
    dataIndex: 'firstPayLoyal90'
  },
  {
    title: '新增付费次留单价',
    align: "center",
    dataIndex: 'firstPayPrice'
  },
  {
    title: '新增付费次留3单价',
    align: "center",
    dataIndex: 'firstPayPrice3'
  }
  ,
  {
    title: '新增付费次留7单价',
    align: "center",
    dataIndex: 'firstPayPrice7'
  }
  ,
  {
    title: '新增付费次留15单价',
    align: "center",
    dataIndex: 'firstPayPrice15'
  }
  ,
  {
    title: '新增付费次留30单价',
    align: "center",
    dataIndex: 'firstPayPrice30'
  }
  ,
  {
    title: '新增付费次留45单价',
    align: "center",
    dataIndex: 'firstPayPrice45'
  }
  ,
  {
    title: '新增付费次留60单价',
    align: "center",
    dataIndex: 'firstPayPrice60'
  }
  ,
  {
    title: '新增付费次留90单价',
    align: "center",
    dataIndex: 'firstPayPrice90'
  }
]);
let tempcolumn = columns.value;


// if(queryParam.value.retainType==='newLoyal'){

// }
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: 
    {
      // title: '留存分析',
      api: queryList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn: false,
      showIndexColumn:false,
      summaryData:summaryData,
      summaryFunc: (res) => {
        //拿到合计那一行的数据
        return summaryData.value
      },
      showSummary: true,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        // columns.value = columns2;
        return Object.assign(params, queryParam.value);
      },
      afterFetch:(res)=>{
        summaryData.value = []
        if(res.length !== 0) {
          summaryData.value.push(res[res.length - 1])
        }
        lineMultiData.value = [];
        if(!res || res.length==1){
            barOption.value.title.text = "无数据";
        }else{
          barOption.value.title.text = res[1]?.gameName+"数据";
        }
        if(queryParam.value.retainType==='newLoyal'){
          res.forEach((item) => {
          if(item.dateTime!="合计"){
            lineMultiData.value.push({name: item.dateTime,type:"次日留存",value: parseFloat(item.firstPayLoyal)});
            lineMultiData.value.push({name: item.dateTime,type:"3日留存",value: parseFloat(item.firstPayLoyal3)});
            lineMultiData.value.push({name: item.dateTime,type:"7日留存",value: parseFloat(item.firstPayLoyal7)});
            lineMultiData.value.push({name: item.dateTime,type:"15日留存",value: parseFloat(item.firstPayLoyal15)});
            lineMultiData.value.push({name: item.dateTime,type:"30日留存",value: parseFloat(item.firstPayLoyal30)});
            lineMultiData.value.push({name: item.dateTime,type:"45日留存",value: parseFloat(item.firstPayLoyal45)});
            lineMultiData.value.push({name: item.dateTime,type:"60日留存",value: parseFloat(item.firstPayLoyal60)});
            lineMultiData.value.push({name: item.dateTime,type:"90日留存",value: parseFloat(item.firstPayLoyal90)});
          }
        });
        }else{
          res.forEach((item) => {
            // debugger
          if(item.dateTime!="合计"){
            lineMultiData.value.push({name: item.dateTime,type:"次日留存",value: parseFloat(item.remainLoyal.loyal3)});
            lineMultiData.value.push({name: item.dateTime,type:"3日留存",value: parseFloat(item.remainLoyal.loyal3)});
            lineMultiData.value.push({name: item.dateTime,type:"4日留存",value: parseFloat(item.remainLoyal.loyal4)});
            lineMultiData.value.push({name: item.dateTime,type:"5日留存",value: parseFloat(item.remainLoyal.loyal5)});
            lineMultiData.value.push({name: item.dateTime,type:"6日留存",value: parseFloat(item.remainLoyal.loyal6)});
            lineMultiData.value.push({name: item.dateTime,type:"7日留存",value: parseFloat(item.remainLoyal.loyal7)});
            lineMultiData.value.push({name: item.dateTime,type:"15日留存",value: parseFloat(item.remainLoyal.loyal15)});
            lineMultiData.value.push({name: item.dateTime,type:"30日留存",value: parseFloat(item.remainLoyal.loyal30)});
            lineMultiData.value.push({name: item.dateTime,type:"45日留存",value: parseFloat(item.remainLoyal.loyal45)});
            lineMultiData.value.push({name: item.dateTime,type:"60日留存",value: parseFloat(item.remainLoyal.loyal60)});
            lineMultiData.value.push({name: item.dateTime,type:"90日留存",value: parseFloat(item.remainLoyal.loyal90)});
          }
        });
        }
      }
    },
    exportConfig: {
      name: "留存分析",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  console.log(12345,tableContext);
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource,setColumns }, { rowSelection, selectedRowKeys }] = tableContext;

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
    if(queryParam.value.retainType==='loyal'){
      columns.value = [];
      columns.value.push(
        {
    title: '日期',
    align: "center",
    dataIndex: 'dateTime'
  },
  {
    title: '游戏名',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '渠道名',
    align: "center",
    dataIndex: 'channelName'
  },
  {
    title: '广告名',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '推广费用',
    align: "center",
    dataIndex: 'costPay'
  },
  {
    title: '次日留存',
    align: "center",
    dataIndex: ['remainLoyal','loyal2']
  },
  {
    title: '次日单价留存',
    align: "center",
    dataIndex: ['priceLoyal','price3']
  }
      )
        for (let i = 3; i <= 150; i++) {
          columns.value.push({
            title: i + '日单价留存',
            align: 'center',
            dataIndex: ['priceLoyal',`price${i}`]
          },{
            title: i + '日留存',
            align: 'center',
            dataIndex: ['remainLoyal',`loyal${i}`]
          })
        }
        setColumns(columns.value);
        }else{
          setColumns(tempcolumn);
        }
      
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    //刷新数据
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
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
