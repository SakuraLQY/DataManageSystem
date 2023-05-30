<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <a-col :lg="8">
              <a-form-item label="注册日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择注册开始日期" v-model:value="queryParam.regStartTime" />
                <span>至</span>
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择注册结束日期" v-model:value="queryParam.regEndTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="登录日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择登录开始日期" v-model:value="queryParam.logStartTime" />
                <span>至</span>
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择登录结束日期" v-model:value="queryParam.logEndTime" />
              </a-form-item>
            </a-col>
            <!-- <a-col :lg="8">
              <a-form-item label="充值日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择充值开始日期" v-model:value="queryParam.payStartTime" />
                <span>至</span>
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择充值结束日期" v-model:value="queryParam.payEndTime" />
              </a-form-item>
            </a-col> -->
            <a-col :lg="8">
              <a-form-item label="用户ID">
                <a-input placeholder="请输入用户ID" v-model:value="queryParam.userId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="账号">
                <a-input placeholder="请输入账号" v-model:value="queryParam.userName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="角色名">
                <a-input placeholder="请输入角色名" v-model:value="queryParam.roleName"></a-input>
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
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <template #blockadeUserId="{ record }">
        <Tag color="green" >
          <Popconfirm title="确认封锁此账号的登录？" @confirm="blockadeFunc(record, 1)">
            <a-button v-if="record.userState === 0"  type="ghost" > 封号</a-button>
          </Popconfirm>
          <Popconfirm title="确认解封此账号的登录？" @confirm="blockadeFunc(record, 2)">
            <a-button v-if="record.userState === 1"  type="error" > 解封</a-button>
          </Popconfirm>
        </Tag>
      </template>
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
    <CtRoleModal ref="registerModal" @success="handleSuccess"></CtRoleModal>
    <BasicModal title="查看用户充值数据时间范围(不选默认不限制)" width="800px" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': false } }"  @cancel="handleCancel" cancelText="关闭">
        <a-form :labelCol="labelCol2" :wrapperCol="wrapperCol2">
          <a-row>
            <a-col :span="24">
              <a-form-item label="充值开始日期" >
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择充值开始日期" v-model:value="orderDetailParam.payStartTime" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="充值结束日期" >
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择充值结束日期" v-model:value="orderDetailParam.payEndTime" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </BasicModal>
      <BasicModal :title="title" width="800px" :body-style="bodystyle" :visible="orderDetailVisible" :okButtonProps="{ class: { 'jee-hidden': 'false' } }"  @cancel="handleCancel" cancelText="关闭">
        <a-form :labelCol="labelCol2" :wrapperCol="wrapperCol2">
          <a-row>
            <a-col :span="24" v-for="item in orderDetailList">
              <a-form-item>
                <p>订单号: {{item.orderId}}</p>
                <p>充值金额: {{item.money}}</p>
                <p>充值方式: {{item.payType}}</p>
                <p>充值时间: {{item.createTime}}</p>
                <a-divider />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </BasicModal>
  </div>
</template>

<script lang="ts" name="count-ctRole" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './CtRole.data';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import { list, getOrderDetail, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CtRole.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import CtRoleModal from './components/CtRoleModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import { blockade } from '/@/views/statistics/userData/ctUser/CtUser.api';
  import { message } from 'ant-design-vue';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const selectGameForm= ref();
  const selectDealForm= ref();
  const selectChannelForm= ref();
  const labelCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const visible = ref<boolean>(false);
  const title = ref<string>();
  const orderDetailVisible = ref<boolean>(false);
  const orderDetailParam = reactive<Record<string, any>>({
    userId: undefined,
    dealId: undefined,
    payStartTime: undefined,
    payEndTime: undefined,
  })
  const bodystyle = {
    height: '700px',
    overflow: 'hidden',
    overflowY: 'scroll',
  }
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
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ct_role',
      api: list,
      columns,
      canResize:false,
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
      name: "ct_role",
      url: getExportUrl,
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
   * 封号操作
   */
  async function blockadeFunc(record, type) {
    record.type = type
    blockade(record).then((res: any)=>{
      reload()
    })
  }

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
    return handleExportXls("角色数据", "/count/ctRole/exportXls", queryParam.value);
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
   * 用户充值查询
   */
  function handleUserPay(record: Recordable) {
    visible.value = true
    orderDetailParam.userId = record.userId
    orderDetailParam.dealId = record.dealId
  }

  let orderDetailList = ref([])

  /**
   * 确定按钮点击事件
   */
  function handleOk() {
    visible.value = false
    getOrderDetail(orderDetailParam).then((res: any)=>{
      if(res == null) {
        message.warning('未能查询到此用户订单数据');
        return false;
      }
      orderDetailList.value = res.orderObj
      title.value = '用户订单数据,合计 ' + res.totalMoney + '元'
      orderDetailVisible.value = true
    })

  }

  function handleCancel() {
    visible.value = false
    orderDetailVisible.value = false
    orderDetailParam.userId = undefined
    orderDetailParam.dealId = undefined
    orderDetailParam.payStartTime = undefined
    orderDetailParam.payEndTime = undefined
    title.value = ''
  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '用户充值查询',
        onClick: handleUserPay.bind(null, record),
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
    selectGameForm.value.reload();
    selectDealForm.value.reload();
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
</style>
