<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="SKD订单号">
              <a-input v-model:value="queryParam.orderId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="CP订单号">
              <a-input v-model:value="queryParam.gameOrderId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="渠道订单号">
              <a-input v-model:value="queryParam.bankOrderId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="用户id">
              <a-input v-model:value="queryParam.userId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="类型">
              <j-dict-select-tag v-model:value="queryParam.type" :options="type" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="充值类型">
              <j-dict-select-tag v-model:value="queryParam.orderType" :options="orderTypes" allowClear />
            </a-form-item>
          </a-col>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <a-col :lg="8" v-if="queryParam.type == 0">
            <a-form-item label="发货状态">
              <j-dict-select-tag v-model:value="queryParam.sendStatus" :options="sendStatus" allowClear />
            </a-form-item>
          </a-col>
          <ChannelThirdOptionForm v-if="queryParam.type == 1" ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
          <a-col :lg="8" v-if="queryParam.type == 1">
            <a-form-item label="广告列表">
              <j-select-multiple dictCode="open_gateway.op_deal,deal_name,id" v-model:value="queryParam.dealId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8" v-if="queryParam.type == 1">
            <a-form-item label="充值方式">
              <j-dict-select-tag v-model:value="queryParam.bankType" :options="bankTypes" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8" v-if="queryParam.type == 1">
            <a-form-item label="手机系统">
              <j-dict-select-tag v-model:value="queryParam.phoneOs" :options="phoneOs" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="支付渠道">
              <j-dict-select-tag dictCode="open_gateway.op_pay_vendor,pay_vendor_name,id" v-model:value="queryParam.payVendorId" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="选择日期">
              <a-range-picker v-model:value="orderTime" />
            </a-form-item>
          </a-col>
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
    <span style="font-size: 20px; color: red">订单总条数：{{ sum }} &nbsp; 订单总金额：{{ totalMoney }}</span>
    <!-- 手动补单弹窗 -->
    <a-modal :title="title" :width="width" :visible="visible" @ok="handleSupplementaryOrder" @cancel="handleCancel" cancelText="关闭">
      <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-row>
          <span aligin="center" style="font-size: 20px">正在修改订单的支付状态，请输入有效密码：</span>
          <a-col :span="24">
            <a-form-item>
              <a-input v-model:value="supplementaryOrderParam.validatePassword"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
    <!--引用表格-->
    <BasicTable @register="registerTable">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button
            >批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :disable="true" />
        <div v-if="flag">
          <TableAction :actions="changeOrderStatus(record)" :disable="false" />
        </div>
      </template>
      <!-- 充值方式回显 -->
      <template #bank_type="{ record }">
        <div v-if="record.bankType == 1">支付宝</div>
        <div v-if="record.bankType == 2">支付宝（网页）</div>
        <div v-if="record.bankType == 3">微信（网页）</div>
        <div v-if="record.bankType == 8">IOS正版支付</div>
      </template>
      <template #order_type="{ record }">
        <div v-if="record.orderType == 0">游戏订单充值</div>
        <div v-if="record.orderTYpe == 2">平台币订单充值</div>
      </template>
      <!-- 银行发货状态 -->
      <template #bankStatus="{ record }">
        <div v-if="record.bankStatus == 1000">成功</div>
        <div v-else>失败</div>
      </template>
      <!-- 游戏发货状态 -->
      <template #gameStatus="{ record }">
        <div v-if="record.gameStatus == 1000">成功</div>
        <div v-else>失败</div>
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
    <PayReportModal ref="registerModal" @success="handleSuccess"></PayReportModal>
  </div>
</template>

<script lang="ts" name="pay_report-payReport" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, type, orderTypes, bankTypes, phoneOs, sendStatus } from './PayReport.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, supplementaryOrderApi, getTotal } from './PayReport.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import PayReportModal from './components/PayReportModal.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';

  const queryParam = ref<any>({ type: 1, sendStatus: 1 });
  const supplementaryOrderParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const flag = ref(false);
  const orderTime = ref<Moment[]>([]);
  const title = ref<string>('');
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const pwd = ref();
  const sum = ref();
  const totalMoney = ref();
  const selectGameForm = ref();
  const selectChannelForm = ref();
  //注册table数据

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
   * 获取数据总数
   */
  getAllToTal();
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'pay_report',
      api: list,
      columns,
      showIndexColumn: false,
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
      name: '充值订单',
      url: getExportUrl,
      params: queryParam.value,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [
    registerTable,
    { reload, setColumns, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource },
    { rowSelection, selectedRowKeys },
  ] = tableContext;
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
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
  }

  /**
   * 手动补单弹窗
   * @param record
   */
  function supplementaryOrder(record) {
    debugger;
    if (queryParam.value.sendStatus == 1) {
      title.value = '手动补单';
    } else {
      title.value = '更改状态';
    }
    visible.value = true;
    supplementaryOrderParam.value.bankStatus = record.bankStatus;
    supplementaryOrderParam.value.gameStatus = record.gameStatus;
    supplementaryOrderParam.value.orderId = record.orderId;
    supplementaryOrderParam.value.sendStatus = record.sendStatus;
  }

  async function handleSupplementaryOrder() {
    await supplementaryOrderApi(supplementaryOrderParam.value).finally(() => {
      visible.value = false;
      reload();
    });

    getAllToTal();
  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
    ];
  }
  function changeOrderStatus(record) {
    if (record.sendStatus == 1) {
      return [
        {
          label: '手动补单',
          onClick: supplementaryOrder.bind(null, record),
        },
      ];
    } else {
      return [
        {
          label: '更改状态',
          onClick: supplementaryOrder.bind(null, record),
        },
      ];
    }
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

  function getAllToTal() {
    getTotal(queryParam.value).then((res: any) => {
      sum.value = res.sum;
      totalMoney.value = res.totalMoney;
    });
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

    if (queryParam.value.type == 0) {
      columns[1].ifShow = false;
      columns[4].ifShow = false;
      columns[5].ifShow = true;
      columns[6].ifShow = true;
      columns[14].ifShow = true;

      flag.value = true;
    } else {
      columns[1].ifShow = true;
      columns[4].ifShow = true;
      columns[5].ifShow = false;
      columns[6].ifShow = false;
      columns[14].ifShow = false;

      flag.value = false;
    }

    setColumns(columns);
    reload();

    getAllToTal();
  }

  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    orderTime.value = [];
    //刷新数据
    selectGameForm.value.reload();
    selectChannelForm.value.reload();
    reload();

    getAllToTal();
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
