<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="用户名">
              <a-input v-model:value="queryParam.userName" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="用户id">
              <a-input v-model:value="queryParam.userId" allowClear :maxLength="10" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-select-multiple dictCode="open_gateway.op_deal,deal_name,id" v-model:value="queryParam.dealId" allowClear />
            </a-form-item>
          </a-col>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
          <a-col :lg="8">
            <a-form-item label="选择日期">
              <a-range-picker v-model:value="orderTime" />
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
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
      </template>
      <template #payType="{ record }">
        <div v-if="record.payType == 1">支付宝</div>
        <div v-if="record.payType == 2">支付宝（网页）</div>
        <div v-if="record.payType == 3">微信（网页）</div>
        <div v-if="record.payType == 8">IOS正版支付</div>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{ text }">
        <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{ text }">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{ text }">
        <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OrderPurchaseVolumeModal ref="registerModal" @success="handleSuccess"></OrderPurchaseVolumeModal>
  </div>
</template>

<script lang="ts" name="OrderPurchaseVolume-orderPurchaseVolume" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OrderPurchaseVolume.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OrderPurchaseVolume.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OrderPurchaseVolumeModal from './components/OrderPurchaseVolumeModal.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import { formatToDate } from '/@/utils/dateUtil';
  import dayjs, { Dayjs } from 'dayjs';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const dateFormat = 'YYYY-MM-DD';
  const orderTime = ref<Moment[]>([dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)]);
  const userTime = ref<Moment[]>([dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)]);
  const selectGameForm = ref();
  const selectChannelForm = ref();

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

  changeDate();
  function changeDate() {
    if (orderTime.value != null && orderTime.value.length > 0) {
      queryParam.value.orderStartTime = orderTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.orderEndTime = orderTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.orderStartTime = null;
      queryParam.value.orderEndTime = null;
    }

    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
    }
  }
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'order_purchase_volume',
      api: list,
      columns,
      canResize: false,
      useSearchForm: false,
      showSummary: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
        defaultHidden: true,
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: '充值订单【买量】',
      url: getExportUrl,
      params: queryParam.value,
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
    if (orderTime.value != null && orderTime.value.length > 0) {
      queryParam.value.orderStartTime = orderTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.orderEndTime = orderTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.orderStartTime = null;
      queryParam.value.orderEndTime = null;
    }

    if (userTime.value != null && userTime.value.length > 0) {
      queryParam.value.userStartTime = userTime.value[0].format('YYYY-MM-DD') + ' 00:00:00';
      queryParam.value.userEndTime = userTime.value[1].format('YYYY-MM-DD') + ' 23:59:59';
    } else {
      queryParam.value.userStartTime = null;
      queryParam.value.userEndTime = null;
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
    userTime.value = [dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)];
    orderTime.value = [dayjs(formatToDate(new Date()), dateFormat), dayjs(formatToDate(new Date()), dateFormat)];
    changeDate();
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
