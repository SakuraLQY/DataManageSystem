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
            <a-form-item label="数据类型">
              <j-dict-select-tag v-model:value="queryParam.dataType" :options="dataType" :showChooseOption="false" />
            </a-form-item>
          </a-col>
          <a-col :lg="24">
            <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="getPayUserStructData">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-row>
      <a-col :lg="24">
        <a-table :columns="payStructColumns" :dataSource="payStructDataSource" :pagination="false" bordered />
      </a-col>
    </a-row>
    <!-- 表单区域 -->
    <PayUserStructModal ref="registerModal" @success="handleSuccess"></PayUserStructModal>
  </div>
</template>

<script lang="ts" name="PayUserStruct-payUserStruct" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, dataType } from './PayUserStruct.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, getPayUserStruct } from './PayUserStruct.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import PayUserStructModal from './components/PayUserStructModal.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { formatToDate } from '/@/utils/dateUtil';
  import dayjs, { Dayjs } from 'dayjs';

  const queryParam = ref<any>({ dataType: 1 });
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const selectGameForm = ref();
  const selectChannelForm = ref();
  const payStructDataSource = ref();
  const dateFormat = 'YYYY-MM-DD';
  const userTime = ref<Moment[]>([dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)]);

  const payStructColumns = [
    {
      title: '充值人数结构',
      children: [
        {
          title: '日期',
          dataIndex: 'createTime',
          align: 'center',
        },
        {
          title: '1-5',
          dataIndex: 'oneInterval',
          align: 'center',
        },
        {
          title: '6-29',
          dataIndex: 'twoInterval',
          align: 'center',
        },
        {
          title: '30-67',
          dataIndex: 'thirdInterval',
          align: 'center',
        },
        {
          title: '68-127',
          dataIndex: 'fourInterval',
          align: 'center',
        },
        {
          title: '128-197',
          dataIndex: 'fiveInterval',
          align: 'center',
        },
        {
          title: '198-327',
          dataIndex: 'sixInterval',
          align: 'center',
        },
        {
          title: '328-647',
          dataIndex: 'sevenInterval',
          align: 'center',
        },
        {
          title: '648-999',
          dataIndex: 'eightInterval',
          align: 'center',
        },
        {
          title: '1000-1999',
          dataIndex: 'nineInterval',
          align: 'center',
        },
        {
          title: '2000-4999',
          dataIndex: 'tenInterval',
          align: 'center',
        },
        {
          title: '5000+',
          dataIndex: 'elevenInterval',
          align: 'center',
        },
      ],
    },
  ];

  getPayUserStructData();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'pay_user_struct',
      api: list,
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
      name: 'pay_user_struct',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
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
   * 充值结构数据
   */
  function getPayUserStructData() {
    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }

    getPayUserStruct(queryParam.value).then((res: any) => {
      payStructDataSource.value = res;
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
    reload();
  }

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    queryParam.value.dataType = 1;
    //刷新数据
    userTime.value = [dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)];
    getPayUserStructData();
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
