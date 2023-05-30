<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
          <a-col :lg="8">
            <a-form-item label="广告">
                <j-search-select placeholder="请选择广告" v-model:value="queryParam.dealId"  dict="open_gateway.op_deal,deal_name,id" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="成本日期">
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.startTime" allowClear/>至
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.endTime" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <span style="font-size: 20px; color: red">合计&nbsp;&nbsp;成本:{{ totalCost.totalCostMoney }} &nbsp;展示:{{ totalCost.totalExhibition }}&nbsp; 点击:{{ totalCost.totalClick }}&nbsp;下载:{{ totalCost.totalDownload }}</span>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
        </a-dropdown>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
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
    <OpCostModal ref="registerModal" @success="handleSuccess"></OpCostModal>
  </div>
</template>

<script lang="ts" name="advert-opCost" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpCost.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl,total } from './OpCost.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpCostModal from './components/OpCostModal.vue'
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const selectGameForm= ref();
  const selectChannelForm= ref();
  const totalCost = ref({
    totalClick:0,
    totalCostMoney:0,
    totalDownload:0,
    totalExhibition:0
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
  getTotal();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_cost',
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
      name: "op_cost",
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
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    reload();
    getTotal();
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
    getTotal();
  }
  
  /**
   * 获取合计
   */
  function getTotal(){
    total(queryParam.value).then(res=>{
      if(res){
        totalCost.value = res;
      }else{
        totalCost.value = {
          totalClick:0,
          totalCostMoney:0,
          totalDownload:0,
          totalExhibition:0
        }
      }
    });
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
