<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <a-col :lg="8">
            <a-form-item label="星图主播">
              <j-search-select multiple placeholder="请选择星图主播" v-model:value="queryParam.anchorPlanId" dict="open_gateway.op_anchor_plan,plan_name,id" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="日期">
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始日期" v-model:value="queryParam.startTime" />
              <span>至</span>
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <!-- <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a> -->
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" >
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="exportXlS" > 导出</a-button>
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

<script lang="ts" name="count-ctRole" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './XingtuDayReport.data';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import { queryXingtuDayReportList } from '/@/views/statistics/financeData/RoiData/CtDaily.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { blockade } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { message } from 'ant-design-vue';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  let date = new Date(Date.now() - 24 * 60 * 60 * 1000);
  let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 3);
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let year2 = date2.getFullYear();
  let month2 = date2.getMonth() + 1;
  let day2 = date2.getDate();
  // 格式化年月日信息，回填到时间控件上
  let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
  let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
  const queryParam = ref<any>({startTime: dateString2, endTime: dateString});
  // const toggleSearchStatus = ref<boolean>(false);
  const selectGameForm= ref();
  let summaryData = ref([])
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'retention',
      api: queryXingtuDayReportList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn:false,
      pagination: false,
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
      afterFetch: (res) => {
        res.forEach(element => {
          element.clickRate = element.clickRate + '%'
          element.downloadRate = element.downloadRate + '%'
          element.downloadRegRate = element.downloadRegRate + '%'
          element.activeRegRate = element.activeRegRate + '%'
          element.costProbability = element.costProbability + '%'
          element.firstROI = element.firstROI + '%'
          element.recoveryRate = element.recoveryRate + '%'
        });
        summaryData.value = []
        summaryData.value.push(res[res.length - 1])
          return res.slice(0, res.length - 1)
      }
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

  //导入导出方法
  const { handleExportXls, handleImportXls } = useMethods();

  /**
   * 导出事件
   */
  function exportXlS() {
    return handleExportXls("星图日报", "/count/ctDaily/xingtuDayReportExportXls", queryParam.value);
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
    ];
  }

  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
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
    queryParam.value = {startTime: dateString2, endTime: dateString};
    //刷新数据
    reload();
    selectGameForm.value.reload();
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
