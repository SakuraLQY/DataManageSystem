<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
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
      <div ref="chartRef" style="width: 100%; height: 400px;"></div>
      <!-- <LineMulti :chartData="lineMultiData" height="50vh" type="line" :option="option"></LineMulti> -->
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" >
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->
        <!-- <a-button  type="primary" preIcon="ant-design:export-outlined" @click="exportXlS"> 导出</a-button> -->
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
  </div>
</template>

<script lang="ts" name="count-ctUser" setup>
  import { ref, Ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './FinanceUserData.data';
  import {  deleteOne, batchDelete, getImportUrl, getExportUrl } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { queryFinanceUserList } from '/@/views/statistics/financeData/RoiData/CtDaily.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { message } from 'ant-design-vue';
  import func from '../../../../../vue-temp/vue-editor-bridge';
  import LineMulti from '/@/components/chart/LineMulti.vue';
  import { useECharts } from '/@/hooks/web/useECharts';
  import { forEach } from '../../../../utils/helper/treeHelper';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  let date = new Date();
  let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 6);
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let year2 = date2.getFullYear();
  let month2 = date2.getMonth() + 1;
  let day2 = date2.getDate();
  // 格式化年月日信息，回填到时间控件上
  let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
  const queryParam = ref<any>({startTime:dateString2, endTime: dateString});
  const barDataSource = ref([]);
  const selectGameForm= ref();
  const selectDealForm= ref();
  const selectChannelForm= ref();
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  let getDealVal = (e: any) => {
    queryParam.value.dealId = e.dealId;
  };
  let getChannelVal = (e: any) => {
    queryParam.value.channelTypeId = e.channelTypeId;
    queryParam.value.channelId = e.channelId;
    queryParam.value.channelSubAccountId = e.channelSubAccountId;
  };
  const toggleSearchStatus = ref<boolean>(false);

  let option = ref()
  let queryList = ref([])
  let data = ref([])
  let data2 = ref([])
  let data3 = ref([])
  let data4 = ref([])
  let data5 = ref([])
  let data6 = ref([])
  let dateData = ref([])
  // let lineMultiData = ref([])
  let summaryData = ref([])
  const chartRef = ref<HTMLDivElement | null>(null);
  const { setOptions, echarts } = useECharts(chartRef as Ref<HTMLDivElement>);
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'FinanceUserData',
      api: queryFinanceUserList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn: false,
      summaryFunc: (res) => {
        return summaryData.value
      },
      showSummary: true,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
      afterFetch: (result) => {
        summaryData.value = []
        queryList.value = []
        if (result.length !== 0) {
          queryList.value = result.slice(0, result.length - 1)
          summaryData.value.push(result[result.length - 1])
        }
        dateData.value = []
        data.value = []
        data2.value = []
        data3.value = []
        data4.value = []
        data5.value = []
        data6.value = []
        if(queryList.value.length !== 0) {
          queryList.value.forEach(element => {
            dateData.value.push(element.roiDate)
            data.value.push(element.regCount)
            data2.value.push(element.regUnitPrice)
            data3.value.push(element.totalRetention)
            data4.value.push(element.userPayCount)
            data5.value.push(element.costUnitPrice)
            data6.value.push(element.payRetention)
          })
        }
        option.value = {
          title: {
              text: '用户数据',
              textStyle: {
                color: '#333',
                fontSize: 18,
                fontWeight: 'bold'
            },
            left: 'center'
          },
          // 图例
          legend: {
            top: 'bottom',
          },
          xAxis: {
            type: 'category',
            data: dateData.value,
            axisLabel: {
              interval: 9, // 每隔 0 个单位显示一个标签
              rotate: 45
            }
          },
          yAxis: [
            {
            type: 'value',
            name: '人数(人)',
            position: 'left',
            axisLabel: {
              formatter: '{value} 人'
            }
            },
            {
              type: 'value',
              name: '单价(元)',
              position: 'right',
              axisLabel: {
                formatter: '{value} 元'
              }
            }],
            series: [
            {
              name: '注册人数',
              type: 'line',
              data: data.value,
              yAxisIndex: 0
            },
            {
              name: '注册单价',
              type: 'line',
              data: data2.value,
              yAxisIndex: 1
            },
            {
              name: '总留存人数',
              type: 'line',
              data: data3.value,
              yAxisIndex: 0
            },
            {
              name: '付费人数',
              type: 'line',
              data: data4.value,
              yAxisIndex: 0
            },
            {
              name: '付费单价',
              type: 'line',
              data: data5.value,
              yAxisIndex: 1
            },
            {
              name: '付费留存人数',
              type: 'line',
              data: data6.value,
              yAxisIndex: 0
            }
            ],
          // 悬浮提示框
          tooltip: {
            trigger: 'axis',
            // triggerOn: 'mousemove',
            // formatter: function(params) {
            //   // 获取第一个数据点的数值和X轴标签
            //   // var value = params[0].value;
            //   // var label = params[0].axisValue;
            //   // 返回提示框的标题和内容
            //   return '日期：' + params[0].axisValue + '<br/>注册人数: ' + params[0].value + '<br/>注册单价: ' + params[1].value + '<br/>总留存人数: ' + params[2].value + '<br/>付费人数: ' + params[3].value + '<br/>付费单价: ' + params[4].value + '<br/>付费留存人数: ' + params[5].value;
            // }
          },

          grid: {
              bottom: 80  // 调整底部留白区域的大小
            },
      };
      setOptions(option.value);

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
   * 查询
   */
  function searchQuery() {
    reload();
  }

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {startTime:dateString2, endTime: dateString};
    selectedRowKeys.value = [];
    //刷新数据
    reload();
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    selectDealForm.value.reload();

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
