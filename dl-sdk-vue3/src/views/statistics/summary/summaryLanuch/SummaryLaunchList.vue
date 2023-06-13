<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <DealOptionSelect ref="selectDealForm" @handler="getDealVal"></DealOptionSelect>
            <a-col :lg="8">
              <a-form-item label="投放账号">
                <j-select-multiple placeholder="请选择投放账号" v-model:value="queryParam.accountId" dictCode = "open_gateway.op_put_account,nick_name,id" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="注册时间">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.startTime" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.endTime" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
            <a-form-item label="常用日期">
                <a href="#" @click="handleClick(1)">今天</a> <a href="#" @click="handleClick(2)">昨天</a> <a href="#" @click="handleClick(3)">最近三天</a> <a href="#" @click="handleClick(4)">最近一周</a> <a href="#" @click="handleClick(5)">最近两周</a>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="归类方式">
                <j-dict-select-tag  placeholder="请输入归类方式" v-model:value="queryParam.type" :options="compareTypeOption"/>
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
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="exportXlS"> 导出</a-button>
        <!-- <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
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
    <SummaryLaunchModal ref="registerModal" @success="handleSuccess"></SummaryLaunchModal>
  </div>
</template>

<script lang="ts" name="count-summaryLaunch" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns,compareTypeOption } from './SummaryLaunch.data';
  import { queryList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './SummaryLaunch.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import SummaryLaunchModal from './components/SummaryLaunchModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  import DealOptionSelect from '/@/views/common/dealOptionSelect.vue';
  import { useMethods } from '/@/hooks/system/useMethods';
  const queryParam = ref<any>({type:'cost_day',startTime:formatToDate(new Date()),endTime:formatToDate(new Date())});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
    let date = new Date();
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
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
  let getDealVal = (e: any) => {
    queryParam.value.dealId = e.dealId;
  };
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '数据投放',
      api: queryList,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn:false,
      pagination:false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "数据投放",
      url: getExportUrl
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
    //导入导出方法
 const { handleExportXls, handleImportXls } = useMethods();

/**
 * 导出事件
 */
function exportXlS() {
  return handleExportXls( "数据投放表", "/count/summaryLaunch/exportExcel", queryParam.value);

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
   
  function handleClick(type) {
    let date = new Date();
    //今天
    if(type === 1) {
      // 创建Date对象，获取今天的年、月、日等信息
      date = new Date();
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      queryParam.value.startTime = dateString;
      queryParam.value.endTime = dateString;
    }else if(type === 2) {
      //昨天
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      queryParam.value.startTime = dateString;
      queryParam.value.endTime = dateString;
    }else if(type === 3) {
      //最近三天
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 3);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }else if(type === 4) {
      //最近一周
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 7);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }else if(type === 5) {
      //最近两周
      date = new Date(Date.now() - 24 * 60 * 60 * 1000);
      let date2 = new Date(Date.now() - 24 * 60 * 60 * 1000 * 14);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      let day = date.getDate();
      let year2 = date2.getFullYear();
      let month2 = date2.getMonth() + 1;
      let day2 = date2.getDate();
      // 格式化年月日信息，回填到时间控件上
      let dateString = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
      let dateString2 = year2 + '-' + (month2 < 10 ? '0' + month2 : month2) + '-' + (day2 < 10 ? '0' + day2 : day2);
      queryParam.value.startTime = dateString2;
      queryParam.value.endTime = dateString;
    }
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
