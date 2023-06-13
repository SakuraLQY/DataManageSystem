<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <a-col :lg="8">
              <a-form-item label="投放账号">
                <j-select-multiple placeholder="请选择投放账号" v-model:value="queryParam.accountId"
                  dictCode="open_gateway.op_jrtt_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id"
                  allowClear />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="账号人员">
                <j-search-select v-model:value="queryParam.createUser" dict="sys_user,realname,username"
                  placeholder="请选择账号人员" />
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset"
                  style="margin-left: 8px">重置</a-button>
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
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="exportXlS"> 导出</a-button>
        <!-- <j-upload-button type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
        <a-dropdown v-if="selectedRowKeys.length > 0">
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
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{ text }">
        <div v-html="text"></div>
      </template>
      <template #bill_data="{ record }"> 
        <!-- 文字提示 -->
        <a-tooltip>
          <template #title>{{ record.bill}}</template> 
          <a-button type="primary" shape="round" @click = "showBillDetails(record.id)">查看</a-button> 
        </a-tooltip>
      </template>
     <!--省市区字段回显插槽-->
      <template #pcaSlot="{ text }">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{ text }">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small"
          @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpReportModal ref="registerModal" @success="handleSuccess"></OpReportModal>
    <BillDetail :billDetail="bill" ref="showItem"></BillDetail>
    
  </div>
</template>

<script lang="ts" name="count-opReport" setup>

import { ref, reactive } from 'vue';
import { BasicTable, useTable, TableAction } from '/@/components/Table';
import { useListPage } from '/@/hooks/system/useListPage';
import { columns } from './OpReport.data';
import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl, queryBill } from './OpReport.api';
import { downloadFile } from '/@/utils/common/renderUtils';
import OpReportModal from './components/OpReportModal.vue';
import BillDetail from './components/BillDetail.vue'
import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
import { message } from 'ant-design-vue';
  import { useMethods } from '/@/hooks/system/useMethods';
const showItem = ref();
const queryParam = ref<any>({});
const toggleSearchStatus = ref<boolean>(false);
const registerModal = ref([]);
const bill = ref();
let summaryData = ref([]);
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
//注册table数据
const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
  tableProps: {
    title: '数据报表',
    api: queryList,
    columns,
    canResize: false,
    useSearchForm: false,
    showActionColumn: false,
    summaryData: summaryData,
    pagination:false,
    actionColumn: {
      width: 120,
      fixed: 'right',
    },
    beforeFetch: (params) => {
      return Object.assign(params, queryParam.value);
    },afterFetch: (data) =>{
      
    }
  },
  exportConfig: {
    name: "数据报表",
    url: getExportUrl,
    params:queryParam.value
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
 * 账单信息调用
 * @param id 
 */
function showBillDetails(id){
     //调用对应的函数，可以拿到对应的数据
      queryBill({account:id},handleSuccess).then((res)=>{
        bill.value = res;
        if(res.length==0){
          message.info('没有数据记录');
          return;
        }
        showItem.value.showModal(res);
      })
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
  //导入导出方法
 const { handleExportXls, handleImportXls } = useMethods();

  /**
   * 导出事件
   */
  function exportXlS() {
    return handleExportXls("账号数据表", "/count/opReport/exportExcel", queryParam.value);
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
    text-align: center
  }
}
</style>
