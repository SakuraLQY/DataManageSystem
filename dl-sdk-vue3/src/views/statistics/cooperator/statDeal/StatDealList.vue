<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-search-select placeholder="请输入广告列表" v-model:value="queryParam.dealId" dict="open_gateway.op_deal,deal_name,id" allowClear/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="8">
              <a-form-item label="起始日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="结束日期">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="归类方式">
                <j-dict-select-tag placeholder="请输入归类方式" v-model:value="queryParam.type" :options = "typeOption"/>
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
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
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
    <StatDealModal ref="registerModal" @success="handleSuccess"></StatDealModal>
  </div>
</template>

<script lang="ts" name="count-statDeal" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { typeOption } from './StatDeal.data';
  import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './StatDeal.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import StatDealModal from './components/StatDealModal.vue'
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  const queryParam = ref<any>({startTime:formatToDate(new Date()),endTime:formatToDate(new Date()),type:'time_daily'});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();


  //   let getChannelVal = (e: any) => {
  //   queryParam.value.channelTypeId = e.channelTypeId;
  //   queryParam.value.channelId = e.channelId;
  //   queryParam.value.channelSubAccountId = e.channelSubAccountId;
  // };

  let columns:any = ref([
      {
    title: '日期',
    align: "center",
    dataIndex: 'dateTime'
  },
  {
    title: '游戏名称',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'validReg'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstPayMoney'
  },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'countDau'
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayUser'
  },
  {
    title: '付费总额',
    align: "center",
    dataIndex: 'totalMoney'
  },
  ])
    let tempcolumn  = columns.value;
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'stat_deal',
      api: queryList,
      columns,
      canResize:false,
      useSearchForm: false,
      pagination:false,
      showActionColumn:false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "stat_deal",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource,setColumns }, { rowSelection, selectedRowKeys }] = tableContext;
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
    if(queryParam.value.type==='deal_id'){
      columns.value = [];
      columns.value.push(
        {
    title: '广告名称',
    align: "center",
    dataIndex: 'dealName'
  },
  {
    title: '游戏名称',
    align: "center",
    dataIndex: 'gameName'
  },
  {
    title: '激活数',
    align: "center",
    dataIndex: 'countActive'
  },
  {
    title: '注册数',
    align: "center",
    dataIndex: 'regCount'
  },
  {
    title: '有效注册数',
    align: "center",
    dataIndex: 'validReg'
  },
  {
    title: '新增付费人数',
    align: "center",
    dataIndex: 'firstPayUser'
  },
  {
    title: '新增付费金额',
    align: "center",
    dataIndex: 'firstPayMoney'
  },
  {
    title: '活跃人数',
    align: "center",
    dataIndex: 'countDau'
  },
  {
    title: '活跃付费人数',
    align: "center",
    dataIndex: 'alivePayUser'
  },
  {
    title: '付费总额',
    align: "center",
    dataIndex: 'totalMoney'
  },
      )
      setColumns(columns.value);
    }else{
      setColumns(tempcolumn);
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
