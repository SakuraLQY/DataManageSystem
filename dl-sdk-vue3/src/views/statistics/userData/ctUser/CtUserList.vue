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
      <template #blockadeIp="{ record }">
        <Tag color="green" >
          <Popconfirm title="确认封锁此IP的登录？" @confirm="blockadeFunc(record, 3)">
            <a-button v-if="record.ipState === 0"  type="ghost" > 封IP</a-button>
          </Popconfirm>
          <Popconfirm title="确认解封此IP的登录？" @confirm="blockadeFunc(record, 4)">
            <a-button v-if="record.ipState === 1"  type="error" > 解封IP</a-button>
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
    <CtUserModal ref="registerModal" @success="handleSuccess"></CtUserModal>
    <BasicModal title="查看日志时间范围(不选默认不限制)" width="800px" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': false } }"  @cancel="handleCancel" cancelText="关闭">
        <a-form :labelCol="labelCol2" :wrapperCol="wrapperCol2">
          <a-row>
            <a-col :span="24">
              <a-form-item label="登录开始时间" >
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择登录开始时间" v-model:value="loginLogParam.startTime" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="登录结束时间" >
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择登录结束时间" v-model:value="loginLogParam.endTime" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </BasicModal>
      <BasicModal title="用户登录日志" width="800px" :body-style="bodystyle" :visible="userLoginVisible" :okButtonProps="{ class: { 'jee-hidden': 'false' } }"  @cancel="handleCancel" cancelText="关闭">
        <a-form :labelCol="labelCol2" :wrapperCol="wrapperCol2">
          <a-row>
            <a-col :span="24" v-for="item in userLoginList">
              <a-form-item>
                {{item}}
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </BasicModal>
  </div>
</template>

<script lang="ts" name="count-ctUser" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './CtUser.data';
  import { list, blockade, getUserLoginLog, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CtUser.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import CtUserModal from './components/CtUserModal.vue'
  import PkgOptionMoreSelect from '/@/views/common/pkgOptionMoreSelect.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import { Popconfirm } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';

  const queryParam = ref<any>({});
  const loginLogParam = reactive<Record<string, any>>({
    userId: undefined,
    startTime: undefined,
    endTime: undefined,
  })
  const bodystyle = {
    height: '700px',
    overflow: 'hidden',
    overflowY: 'scroll',
  }
  const labelCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const visible = ref<boolean>(false);
  const userLoginVisible = ref<boolean>(false);
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
  const registerModal = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ct_user',
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

  /**
   * 封号、封IP操作
   */
  async function blockadeFunc(record, type) {
    record.type = type
    blockade(record).then((res: any)=>{
      reload()
    })
  }

  //导入导出方法
  const { handleExportXls, handleImportXls } = useMethods();

  /**
   * 导出事件
   */
  function exportXlS() {
    return handleExportXls("用户数据", "/count/ctUser/exportXls", queryParam.value);
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
   * 登录日志
   */
  function handleLoginLog(record: Recordable) {
    visible.value = true
    loginLogParam.userId = record.userId
  }

  let userLoginList = ref([])

  /**
   * 确定按钮点击事件
   */
  function handleOk() {
    visible.value = false
    getUserLoginLog(loginLogParam).then((res: any)=>{
      userLoginList.value = res
    })
    userLoginVisible.value = true
  }

  function handleCancel() {
    visible.value = false
    userLoginVisible.value = false
    loginLogParam.userId = undefined
    loginLogParam.startTime = undefined
    loginLogParam.endTime = undefined

  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '登录日志',
        onClick: handleLoginLog.bind(null, record),
      }
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
</style>
