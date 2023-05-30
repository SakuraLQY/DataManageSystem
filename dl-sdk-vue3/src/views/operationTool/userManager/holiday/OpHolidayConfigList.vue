<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="2">
            <h1 style="margin-left:50px;font-size:20px;width:100px">自动同步</h1>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="开始时间" >
              <a-date-picker placeholder="请选择开始时间" v-model:value="queryParam.beginTime" value-format="YYYY-MM-DD"  style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="结束时间" >
              <a-date-picker placeholder="请选择结束时间" v-model:value="queryParam.endTime" value-format="YYYY-MM-DD"  style="width: 100%" />
            </a-form-item>
          </a-col>
          <a-button type="primary" preIcon="ant-design:search-outlined" @click="addSyncBtn">确认添加</a-button>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button type="error" @click="handleDeleteSome"  class="mr-2"> 删除过期时间</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls" v-show="false"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls" v-show="false" >导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-show="true">批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
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
    <OpHolidayConfigModal ref="registerModal" @success="handleSuccess"></OpHolidayConfigModal>
  </div>
</template>

<script lang="ts" name="holiday-opHolidayConfig" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpHolidayConfig.data';
  import { list, deleteSome, addSync, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpHolidayConfig.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpHolidayConfigModal from './components/OpHolidayConfigModal.vue'
  import func from '../../../vue-temp/vue-editor-bridge';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { forEach } from '../../utils/helper/treeHelper';
  import { message } from 'ant-design-vue';
  
  const { createMessage } = useMessage();
  const { success, warning, error } = createMessage;

  // const queryParam = ref<any>({});
  let queryParam = reactive({
    beginTime : undefined,
    endTime: undefined
  })
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_holiday_config',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      // beforeFetch: (params) => {
      //   return Object.assign(params, queryParam.value);
      // },
    },
    exportConfig: {
      name: "op_holiday_config",
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
   * 自动同步
   */
  function addSyncBtn() {
    if(queryParam.beginTime === undefined || queryParam.endTime === undefined) {
      message.warning('时间未选择');
      return false
    }
    if(queryParam.beginTime > queryParam.endTime){
      message.warning('开始时间不能大于结束时间');
      return false
    }
    addSync(queryParam).then((res) => {
      reload();
      res.forEach((element,index) => {
        if(element.indexOf('放假') !== -1 || element.indexOf('周末') !== -1) {
          setTimeout(()=>{
            success(element)
          },1000*index)
        }else if (element.indexOf('已存在') !== -1) {
          setTimeout(()=>{
            warning(element)
          },1000*index)
        }else{
          setTimeout(()=>{
            error(element)
          },1000*index)
        }
      });
    });
    
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
   * 删除过期时间事件
   */
  async function handleDeleteSome() {
    if(confirm('是否删除过期时间？')) {
      await deleteSome(handleSuccess);
    }
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
   * 删除
   */
  function getTableAction(record) {
    return [
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      }
    ];
  }
   
  /**
   * 下拉操作栏
   */
  // function getDropDownAction(record) {
  //   return [
  //     {
  //       label: '详情',
  //       onClick: handleDetail.bind(null, record),
  //     }, {
  //       label: '删除',
  //       popConfirm: {
  //         title: '是否确认删除',
  //         confirm: handleDelete.bind(null, record),
  //       }
  //     }
  //   ]
  // }

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
