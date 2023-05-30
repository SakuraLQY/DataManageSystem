<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdMuchOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdMuchOptionForm>
          <ChannelThirdMuchOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdMuchOptionForm>
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-select-multiple dictCode="open_gateway.op_deal,deal_name,id" v-model:value="queryParam.dealId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="注册日期">
              <a-range-picker v-model:value="userTime" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="模式选择">
              <j-dict-select-tag v-model:value="queryParam.mode" :options="mode" :showChooseOption="false" />
            </a-form-item>
          </a-col>
          <a-col :lg="24">
            <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
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
    <br /><br />
    <!-- 首日充值区域 -->
    <a-col>
      <a-row><div style="display: inline-block; margin: 0 auto">首日付费分布图</div></a-row>
      <pie height="300px" weight="300px" :chart-data="graphical" />
      <br /><br />
      <a-row>
        <!-- <a-table id="firstTable" :columns="firstcolumns" :locale="{}" /> -->
        <a-col :lg="4">
          <a-table id="xxxx" :columns="columnsxx" :locale="{}" />
        </a-col>
        <a-col :lg="20">
          <a-table :columns="columnsxxx" :dataSource="dataSource" :pagination="false" bordered />
        </a-col>
      </a-row>
    </a-col>
    <br /><br />

    <!-- 累计付费区域 -->
    <a-col>
      <a-row><div style="display: inline-block; margin: 0 auto">累计付费分布图</div></a-row>

      <pie height="300px" weight="300px" :chart-data="accumulateGraphical" />
      <br /><br />
      <a-row>
        <!-- <a-table id="firstTable" :columns="firstcolumns" :locale="{}" /> -->
        <a-col :lg="4">
          <a-table id="xxxx" :columns="columnsxx" :locale="{}" />
        </a-col>
        <a-col :lg="20">
          <a-table :columns="columnsxxx" :dataSource="accumulateDataSource" :pagination="false" bordered />
        </a-col>
      </a-row>
    </a-col>

    <!-- 表单区域 -->
    <UserPayDataModal ref="registerModal" @success="handleSuccess"></UserPayDataModal>
  </div>
</template>

<script lang="ts" name="UserPayData-userPayData" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, mode } from './UserPayData.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, getFirstDayPay, getAccumulatePayData } from './UserPayData.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import UserPayDataModal from './components/UserPayDataModal.vue';
  import { getData } from '/@/views/report/chartdemo/chartdemo.data.ts';
  import Pie from '/@/components/chart/Pie.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';

  const queryParam = ref<any>({ mode: 1 });
  const toggleSearchStatus = ref<boolean>(true);
  const registerModal = ref();
  const userTime = ref<Moment[]>([]);
  const selectGameForm = ref();
  const selectChannelForm = ref();

  const { barDataSource, barMultiData, pieData, barLineData, radarData } = getData;

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

  const columnsxxx = [
    {
      title: '',
      dataIndex: 'Interval1',
    },
    {
      title: '',
      dataIndex: 'Interval2',
    },
    {
      title: '',
      dataIndex: 'Interval3',
    },
    {
      title: '',
      dataIndex: 'Interval4',
    },
    {
      title: '',
      dataIndex: 'Interval5',
    },
    {
      title: '',
      dataIndex: 'Interval6',
    },
    {
      title: '',
      dataIndex: 'Interval7',
    },
    {
      title: '',
      dataIndex: 'Interval8',
    },
    {
      title: '',
      dataIndex: 'Interval9',
    },
    {
      title: '',
      dataIndex: 'Interval10',
    },
    {
      title: '',
      dataIndex: 'Interval11',
    },
    {
      title: '付费ARPU',
      dataIndex: 'arpu',
      customCell: (_, index) => {
        if (index == 0) {
          return {
            rowSpan: 5,
          };
        }
        if (index > 0) {
          return {
            colSpan: 0,
          };
        }
      },
    },
  ];

  const firstcolumns = [
    {
      title: '付费人数',
      children: [
        {
          title: '总金额',
        },
      ],
    },
  ];

  const columnsxx = [
    {
      children: [
        {
          children: [
            {
              title: '付费人数',
              children: [
                {
                  children: [
                    {
                      title: '总付费金额 (元)',
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
    },
    {
      title: '充值区间',
      children: [
        {
          title: '人数（位）',
          children: [
            {
              title: '占比（%）',
              children: [
                {
                  title: '平均数（元）',
                  children: [
                    {
                      title: '总金额',
                      children: [
                        {
                          title: '占比（%）',
                        },
                      ],
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
    },
  ];

  const dataSource = ref();
  const graphical = ref();

  const accumulateDataSource = ref();
  const accumulateGraphical = ref();
  getFirstPay();
  getAccumulatePay();
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'user_pay_data',
      columns,
      canResize: false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: 'user_pay_data',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] =
    tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  /**
   * 首日充值函数
   */
  function getFirstPay() {
    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }

    getFirstDayPay(queryParam.value).then((res: any) => {
      dataSource.value = res.assignment;
      graphical.value = res.graphical;

      if (queryParam.value.mode == 1) {
        columnsxxx[0].title = '2-10元';
        columnsxxx[1].title = '11-29元';
        columnsxxx[2].title = '30-67元';
        columnsxxx[3].title = '68-97元';
        columnsxxx[4].title = '98-127元';
        columnsxxx[5].title = '128-327元';
        columnsxxx[6].title = '328-647元';
        columnsxxx[7].title = '648-999元';
        columnsxxx[8].title = '1000-1999元';
        columnsxxx[9].title = '2000-4999元';
        columnsxxx[10].title = '5000+元	';
      } else if (queryParam.value.mode == 2) {
        columnsxxx[0].title = '6元';
        columnsxxx[1].title = '7-30元';
        columnsxxx[2].title = '31-68元';
        columnsxxx[3].title = '69-128元';
        columnsxxx[4].title = '129-198元';
        columnsxxx[5].title = '199-328元';
        columnsxxx[6].title = '329-648元';
        columnsxxx[7].title = '649-1000元';
        columnsxxx[8].title = '1001-2999元';
        columnsxxx[9].title = '3000-4999元';
        columnsxxx[10].title = '5000+元	';
      }
    });
  }

  /**
   * 累计付费函数
   */
  function getAccumulatePay() {
    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }

    getAccumulatePayData(queryParam.value).then((res: any) => {
      accumulateDataSource.value = res.assignment;
      accumulateGraphical.value = res.graphical;

      if (queryParam.value.mode == 1) {
        columnsxxx[0].title = '2-10元';
        columnsxxx[1].title = '11-29元';
        columnsxxx[2].title = '30-67元';
        columnsxxx[3].title = '68-97元';
        columnsxxx[4].title = '98-127元';
        columnsxxx[5].title = '128-327元';
        columnsxxx[6].title = '328-647元';
        columnsxxx[7].title = '648-999元';
        columnsxxx[8].title = '1000-1999元';
        columnsxxx[9].title = '2000-4999元';
        columnsxxx[10].title = '5000+元	';
      } else if (queryParam.value.mode == 2) {
        columnsxxx[0].title = '6元';
        columnsxxx[1].title = '7-30元';
        columnsxxx[2].title = '31-68元';
        columnsxxx[3].title = '69-128元';
        columnsxxx[4].title = '129-198元';
        columnsxxx[5].title = '199-328元';
        columnsxxx[6].title = '329-648元';
        columnsxxx[7].title = '649-1000元';
        columnsxxx[8].title = '1001-2999元';
        columnsxxx[9].title = '3000-4999元';
        columnsxxx[10].title = '5000+元	';
      }
    });
  }
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
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        },
      },
    ];
  }

  /**
   * 查询
   */
  function searchQuery() {
    getFirstPay();
    getAccumulatePay();
  }

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    userTime.value = [];
    //刷新数据
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    queryParam.value.mode = 1;
    getFirstPay();
    getAccumulatePay();
  }
</script>

<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust {
      width: calc(50% - 15px);
      min-width: 100px !important;
    }
    .query-group-split-cust {
      width: 30px;
      display: inline-block;
      text-align: center;
    }
  }
</style>

<style>
  #xxxx .ant-table-tbody {
    display: none;
  }

  #firstTable .ant-table-tbody {
    display: none;
  }
</style>
