<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
            <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
            <a-col :lg="8">
              <a-form-item label="日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
                <span>至</span>
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
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
    <!-- 图标区域 -->
    <div>
      <LineMulti :chartData="lineMultiData" height="50vh" type="line" :option="barOption" ></LineMulti>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
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
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
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
    <!-- 表单区域 -->
    <LtvAnalyzeModal ref="registerModal" @success="handleSuccess"></LtvAnalyzeModal>
  </div>
</template>

<script lang="ts" name="count-ltvAnalyze" setup>
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  // import { columns } from './LtvAnalyze.data';
  import { queryLtvList, deleteOne, batchDelete, getImportUrl, getExportUrl ,getLtvLine} from './LtvAnalyze.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import LtvAnalyzeModal from './components/LtvAnalyzeModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import {formatToDate } from '/@/utils/dateUtil';
  import {formatToDates } from '/@/utils/dateUtil';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';
  const lineMultiData = ref([]);
  const queryParam = ref<any>({startTime:formatToDates(new Date()),endTime:formatToDate(new Date())});
  //查询游戏、渠道等用来联动的
  const selectGameForm= ref();
  const selectChannelForm= ref();
  let getDealVal = (e: any) => {
    queryParam.value.dealId = e.dealId;
  };
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
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();

  let columns:any = ref([{
    title: '日期',
    align: "center",
    dataIndex: 'ltvDate',
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
  ])
  let queryList = ref([]);
  let summaryData = ref([])

  //设置x,y轴对应的属性
  const barOption = ref({
    title: { text: '全部游戏数据', left: 'center'},
    xAxis:{
      inverse: true //反向坐标
    },
      legend: { top: 'bottom' },
      yAxis: {
      type: 'value',
      name: '充值（元）',
      nameLocation: 'middle',
      nameGap: 45
    },
  })
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'LTV分析',
      api: queryLtvList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn: false,
      // summaryData:summaryData,
      // summaryFunc: (res) => {
      //   //拿到合计那一行的数据
      //   return summaryData.value
      // },
      showSummary: true,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
      afterFetch:(result)=>{

        queryList.value = result.slice(0, result.length - 1)
        summaryData.value = []
        if(result.length !== 0) {
          summaryData.value.push(result[result.length - 1])
        }
        lineMultiData.value = [];
        if(!result || result.length==1){
          barOption.value.title.text = "无数据";
        }else{
          barOption.value.title.text = "全部游戏数据";
        }
        for(let i = 0;i<queryList.value.length;i++){   
          let ltvObj = queryList.value[i]; //拿到一行的数据
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV1',value: ltvObj.remainLtv.ltv1});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV2',value: ltvObj.remainLtv.ltv2});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV3',value: ltvObj.remainLtv.ltv3});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV4',value: ltvObj.remainLtv.ltv4});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV5',value: ltvObj.remainLtv.ltv5});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV6',value: ltvObj.remainLtv.ltv6});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV7',value: ltvObj.remainLtv.ltv7});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV15',value: ltvObj.remainLtv.ltv15});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV30',value: ltvObj.remainLtv.ltv30});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV45',value: ltvObj.remainLtv.ltv45});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV60',value: ltvObj.remainLtv.ltv60});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV90',value: ltvObj.remainLtv.ltv90});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV120',value: ltvObj.remainLtv.ltv120});
          lineMultiData.value.push({name:ltvObj.ltvDate,type: 'LTV150',value: ltvObj.remainLtv.ltv150});
        }
      }
    },
    exportConfig: {
      name: "LTV分析",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload,setColumns, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
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
    columns.value = []
    columns.value.push(
      {
    title: '日期',
    align: "center",
    dataIndex: 'ltvDate',
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
    )
    for (let i = 1; i <= 150; i++) {
          columns.value.push({
            title: 'LTV'+i,
            align: 'center',
            dataIndex: ['remainLtv',`ltv${i}`]
            
          })
        }
        setColumns(columns.value);
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    //刷新数据
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
