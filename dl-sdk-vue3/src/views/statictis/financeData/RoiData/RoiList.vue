<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="广告">
              <j-search-select placeholder="请选择广告" v-model:value="queryParam.dealId"  dict="open_gateway.op_deal,deal_name,id" allowClear />
            </a-form-item>
          </a-col>
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
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
    <div>
      <Bar :chartData="barDataSource" height="50vh" :option="barOption"></Bar>
      <!-- <div class="button-container">
        <a-button  type="primary"  @click="showSeries(1)"> 真实ROI</a-button>
        <a-button  type="primary"  @click="showSeries(2)"> 推广费用</a-button>
        <a-button  type="primary"  @click="showSeries(3)"> SDK分成所得</a-button>
        <a-button  type="primary"  @click="showSeries(4)"> 回收金额</a-button>
      </div> -->
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" >
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="exportXlS"> 导出</a-button>
        <!-- <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
        <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
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
        </a-dropdown> -->
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" />
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
    <CtUserModal ref="registerModal" @success="handleSuccess"></CtUserModal>
  </div>
</template>

<script lang="ts" name="count-ctUser" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  // import { columns } from './RoiData.data';
  import {  deleteOne, batchDelete, getImportUrl, getExportUrl } from '/@/views/statictis/userData/ctUser/CtUser.api';
  import { queryROIList } from './CtDaily.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import CtUserModal from '/@/views/statictis/userData/ctUser/components/CtUserModal.vue'
  import PkgOptionMoreSelect from '/@/views/common/pkgOptionMoreSelect.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { message } from 'ant-design-vue';
  import func from '../../../../../vue-temp/vue-editor-bridge';
  import Bar from '/@/components/chart/Bar.vue';
  
  const queryParam = ref<any>({type:1});
  const barDataSource = ref([]);
  const bodystyle = {
    height: '700px',
    overflow: 'hidden',
    overflowY: 'scroll',
  }
  const selectGameForm= ref();
  const selectChannelForm= ref();
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
  const columns = ref([{
    title: '日期',
    align: "center",
    dataIndex: 'roiDate'
  },{
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
    title: '推广费用',
    align: "center",
    dataIndex: 'promotionCost'
  },
  {
    title: '新增注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '剩余DAU',
    align: "center",
    dataIndex: 'remainDAU'
  },
  {
    title: '付费DAU',
    align: "center",
    dataIndex: 'costDAU'
  },
  {
    title: '注册单价',
    align: "center",
    dataIndex: 'regUnitPrice'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'costCount'
  },
  {
    title: '付费率',
    align: "center",
    dataIndex: 'costProbability'
  },
  {
    title: '付费单价',
    align: "center",
    dataIndex: 'costUnitPrice'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'addCostPrice'
  },
  {
    title: '累计付费金额',
    align: "center",
    dataIndex: 'allCostPrice'
  },
  {
    title: 'sdk分成所得',
    align: "center",
    dataIndex: 'sdkShare'
  },
  {
    title: '利润',
    align: "center",
    dataIndex: 'profit'
  },
  {
    title: '累计ROI',
    align: "center",
    dataIndex: 'allROI'
  },
  {
    title: '首日ROI',
    align: "center",
    dataIndex: 'firstROI'
  },])
  let barOption = ref() 

  let queryList = ref([])
  let data1 = ref([])
  let data2 = ref([])
  let data3 = ref([])
  let data4 = ref([])
  let xData = ref([])
  let summaryData = ref([])
  let promotionCost = ref(0)
  let sdkShare = ref(0)
  let profit = ref(0)
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ROI',
      api: queryROIList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn: false,
      summaryFunc: (res) => {
        return summaryData.value
      },
      showSummary: true,
      // pagination: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        for (let i = 2; i <= 150; i++) {
          columns.value.push({
            title: i + '日ROI(0)',
            align: 'center',
            customRender: ({ record }) => {
              return record.remainROI['day' + i] + '%';
            }
          },)
        }
        return Object.assign(params, queryParam.value);
      },
      afterFetch: (result) => {
        // allData.value = result
        queryList.value = result.slice(0, result.length - 1)
        summaryData.value = []
        if(result.length !== 0) {
          result[result.length - 1].costProbability = result[result.length - 1].costProbability + '%'
          result[result.length - 1].realROI = result[result.length - 1].realROI + '%'
          result[result.length - 1].firstROI = result[result.length - 1].firstROI + '%'
          summaryData.value.push(result[result.length - 1])
        }
        if (queryList.value.length === 0) {
          data1.value = [{ name: '', value: 0 }]
          data2.value = [{ name: '', value: 0 }]
          data3.value = [{ name: '', value: 0 }]
          data4.value = [{ name: '', value: 0 }]
        }else {
          data1.value = []
          data2.value = []
          data3.value = []
          data4.value = []
        }
        xData.value = []
        for (let i = 0; i < queryList.value.length; i++) {
          let roiObj = queryList.value[i];
          xData.value.push(roiObj.roiDate)
          roiObj.costProbability = roiObj.costProbability + '%'
          roiObj.realROI = roiObj.realROI + '%'
          roiObj.firstROI = roiObj.firstROI + '%'
          let obj = { name: '', value: 0 };
          let obj2 = { name: '', value: 0 };
          let obj3 = { name: '', value: 0 };
          let obj4 = { name: '', value: 0 };
          obj.name = roiObj.roiDate;
          obj.value = roiObj.realROI;
          obj2.name = roiObj.roiDate;
          obj2.value = roiObj.promotionCost;
          obj3.name = roiObj.roiDate;
          obj3.value = roiObj.sdkShare;
          obj4.name = roiObj.roiDate;
          obj4.value = roiObj.profit;
          barDataSource.value.push(obj);
          data1.value.push(obj);
          data2.value.push(obj2);
          data3.value.push(obj3);
          data4.value.push(obj4);
        }
        if(summaryData.value.length !== 0) {
          promotionCost.value = summaryData.value[0].promotionCost
          sdkShare.value  = summaryData.value[0].sdkShare
          profit.value  = summaryData.value[0].profit
        }else {
          promotionCost.value = 0
          sdkShare.value = 0
          profit.value = 0
        }
        
        barOption.value =  {
          title: {
              text: 'ROI数据',
              textStyle: {
                color: '#333',
                fontSize: 18,
                fontWeight: 'bold'
            },
            subtext: '[合计] 推广费用： '+promotionCost.value+' SDK分成所得: '+sdkShare.value+' 利润: '+profit.value,
            subtextStyle: {
                color: '#999',
                fontSize: 14
            },
            left: 'center'
          },
          xAxis: {
            type: 'category',
            data: xData.value,
            axisLabel: {
              interval: 2, // 每隔 4 个单位显示一个标签
              rotate: 45
            }
          },
          legend: {
            bottom: -0,
            data: ['真实ROI','推广费用', 'SDK分成所得', '回收金额'],
            selected: {
              '真实ROI': true,
              '推广费用': true,
              'SDK分成所得': true,
              '回收金额': true,
            }
          },
          yAxis: [
            {
              type: 'value',
              max: 120, 
              min: 0,
              axisLabel: {
                show: true,
                formatter: '{value} %' 
              },
              name: 'ROI (%)' // 设置 y 轴名称
            }
          ],
           grid: {
            bottom: 80  // 调整底部留白区域的大小
          },
          series: [{
              name: '真实ROI',
              data: data1.value,
              type: 'bar',
              barWidth: '30%', // 设置柱形图宽度为 80%
              barGap: '10%',    // 设置柱形图间距为 30%
              yAxisIndex: 0
          },
          {
              name: '推广费用',
              data: data2.value,
              type: 'bar',
              barWidth: '30%', // 设置柱形图宽度为 80%
              barGap: '10%',    // 设置柱形图间距为 30%
              yAxisIndex: 0
          },
          {
              name: 'SDK分成所得',
              data: data3.value,
              type: 'bar',
              barWidth: '30%', // 设置柱形图宽度为 80%
              barGap: '10%',    // 设置柱形图间距为 30%
              yAxisIndex: 0
          },
          {
              name: '回收金额',
              data: data4.value,
              type: 'bar',
              barWidth: '30%', // 设置柱形图宽度为 80%
              barGap: '10%',    // 设置柱形图间距为 30%
              yAxisIndex: 0
          }]
      }
      return queryList.value
      },
    },
    exportConfig: {
      name: "用户数据",
      url: getExportUrl,
      params: queryParam.value
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

  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }


  //导入导出方法
  const { handleExportXls, handleImportXls } = useMethods();

  /**
   * 导出事件
   */
  function exportXlS() {
    return handleExportXls("ROI数据表", "/count/ctDaily/roiExportXls", queryParam.value);
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
      // {
      //   label: '订单数据',
      //   onClick: handleUserPay.bind(null, record),
      // }
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

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    queryParam.value.type = 1;
    selectedRowKeys.value = [];
    //刷新数据
    reload();
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
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
  .button-container {
      display: flex;
      justify-content: center;
      gap: 20px;
      margin-bottom: 20px;
    }

</style>
