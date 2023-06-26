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
            <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="getPayUserTwoPayData">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-row>
      <a-col :lg="24">
        <a-table :columns="twoPayColumns" :dataSource="twoPayDataSource" :pagination="false" bordered />
      </a-col>
    </a-row>
    <!-- 表单区域 -->
    <UserTwoPayModal ref="registerModal" @success="handleSuccess"></UserTwoPayModal>
  </div>
</template>

<script lang="ts" name="UserTwoPay-userTwoPay" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './UserTwoPay.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, getPayUserTwoPay } from './UserTwoPay.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import UserTwoPayModal from './components/UserTwoPayModal.vue';
  import UserAccumulateLevelModal from './components/UserAccumulateLevelModal.vue';
  import GameThirdMuchOptionForm from '/@/views/common/gameThirdMuchOptionForm.vue';
  import ChannelThirdMuchOptionForm from '/@/views/common/channelThirdMuchOptionForm.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import { formatToDate } from '/@/utils/dateUtil';
  import dayjs, { Dayjs } from 'dayjs';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const dateFormat = 'YYYY-MM-DD';
  const userTime = ref<Moment[]>([dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)]);
  const selectGameForm = ref();
  const selectChannelForm = ref();

  getPayUserTwoPayData();
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'user_two_pay',
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
      name: 'user_two_pay',
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

  const twoPayDataSource = ref();
  const twoPayColumns = [
    {
      title: '首日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserOne',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserOne',
          align: 'center',
        },
      ],
    },
    {
      title: '次日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserTwo',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserTwo',
          align: 'center',
        },
      ],
    },
    {
      title: '三日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserThird',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserThird',
          align: 'center',
        },
      ],
    },
    {
      title: '四日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserFour',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserFour',
          align: 'center',
        },
      ],
    },
    {
      title: '五日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserFive',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserFive',
          align: 'center',
        },
      ],
    },
    {
      title: '六日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserSix',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserSix',
          align: 'center',
        },
      ],
    },
    {
      title: '七日',
      children: [
        {
          title: '付费人数',
          dataIndex: 'payUserSeven',
          align: 'center',
        },
        {
          title: '二次付费人数',
          dataIndex: 'twoPayUserSeven',
          align: 'center',
        },
      ],
    },
  ];

  /**
   * 用户二次付费数据
   */
  function getPayUserTwoPayData() {
    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }

    getPayUserTwoPay(queryParam.value).then((res: any) => {
      twoPayDataSource.value = res;
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
    //刷新数据
    userTime.value = [];
    userTime.value = [dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)];
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    getPayUserTwoPayData();
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
