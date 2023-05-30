<template>
  <a-modal title="事件管理" width="90%" :visible="visible"  :okButtonProps="{ class: { 'jee-hidden': true } }"  @cancel="handleCancel" cancelText="关闭">
    <div style="margin-left:20px;">
      所属资产:<a-input style="margin-left:20px;margin-top:20px;width: 300px" v-model:value="assetObj.assetName" disabled="false"/>
    </div>
    <div>
      <!--查询区域-->
      <div class="jeecg-basic-table-form-container">
        <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-row :gutter="24">
            <a-col :lg="8">
              <a-form-item label="事件名称">
                <j-dict-select-tag  placeholder="请选择事件" v-model:value="queryParam.eventId" dictCode="event-IOS"/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="回传方式">
                <j-dict-select-tag placeholder="请选择回传方式" v-model:value="queryParam.trackType" dictCode="track_type"/>
              </a-form-item>
            </a-col>
            <template v-if="toggleSearchStatus">
            <a-col :lg="8">
              <a-form-item label="创建用户">
                <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,username,username" allowClear/>
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="创建时间">
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择开始时间" v-model:value="queryParam.startTime" allowClear/>至
                <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择结束时间" v-model:value="queryParam.endTime" allowClear/>
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
          <a-button  type="primary" preIcon="ant-design:import-outlined" @click="sync">同步</a-button>
          <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls" v-show="false"> 导出</a-button>
          <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls" v-show="false">导入</j-upload-button>
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
      <OpJrttEventsModal ref="registerModal" @success="handleSuccess"></OpJrttEventsModal>
    </div>
  </a-modal>
</template>

<script lang="ts" name="advert-opJrttEvents" setup>
  import { ref, reactive, defineExpose, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpJrttEvents.data';
  import { list, watch, syncEvents, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpJrttEvents.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpJrttEventsModal from './components/OpJrttEventsModal.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  let assetObj = reactive<Record<string, any>>({
    id: '',
    assetId: '',   
    gameType: undefined,
    assetName: '',   
  });
  // let assetObj = ref<any>({});
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_jrtt_events',
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
      name: "op_jrtt_events",
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
   * 起始
   */
  function handleBegin(record) {
    visible.value = true;
    queryParam.value.assetsId = record.id;
    reload();
    Object.assign(assetObj, record);
  }

  /**
   * 同步
   */
  function sync() {
    syncEvents(assetObj).then((res: any)=>{
        handleSuccess();
      })
  }

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
  }

  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add(assetObj);
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
    registerModal.value.edit(record,assetObj);
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
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
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
  function getDropDownAction(record) {
    return [
      // {
      //   label: '详情',
      //   onClick: handleDetail.bind(null, record),
      // }, {
      //   label: '删除',
      //   popConfirm: {
      //     title: '是否确认删除',
      //     confirm: handleDelete.bind(null, record),
      //   }
      // }
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
    queryParam.value.assetsId = assetObj.id;
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }
  
  defineExpose({
    handleBegin,
  });


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
    .full-modal {
    .ant-modal {
      max-width: 100%;
      top: 0;
      padding-bottom: 0;
      margin: 0;
    }
    }
  }
</style>
