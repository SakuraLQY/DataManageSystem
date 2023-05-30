<template>
  <BasicModal :title="title" :width="width" :visible="visible" :okButtonProps="{ class: { 'jee-hidden': 'false' } }"   @cancel="handleCancel" cancelText="关闭">
    <div>
      <!--查询区域-->
      <div class="jeecg-basic-table-form-container">
        <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-row :gutter="24">

          </a-row>
        </a-form>
      </div>
      <!--引用表格-->
      <BasicTable @register="registerTable" :rowSelection="rowSelection">
        <!--插槽:table标题-->
        <template #tableTitle>
          <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
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
      <OpCommodityModal ref="registerModal" @success="handleSuccess"></OpCommodityModal>
    </div>
  </BasicModal>
</template>

<script lang="ts" name="game-opCommodity" setup>
  import { ref, reactive, defineExpose } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpCommodity.data';
  import { BasicModal } from '/@/components/Modal';
  import { goodsList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpCommodity.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpCommodityModal from './components/OpCommodityModal.vue'
  import {basicProps} from '/@/components/Form/src/props.ts'
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const title = ref<string>('内购商品配置');
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  let gameList = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_commodity',
      api: goodsList,
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
      name: "op_commodity",
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
   * 打开界面传值
   */
  function getList(record) {
    queryParam.value.gameId = record.id;
    reload();
    visible.value = true;
    gameList.value = record;
  }

  defineExpose({
    getList,
  });



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
    registerModal.value.add(gameList.value);
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    record.gameName = gameList.value.gameName
    record.currencyType = record.currencyType + ''
    registerModal.value.edit(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    record.gameName = gameList.value.gameName
    record.currencyType = record.currencyType + ''
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
