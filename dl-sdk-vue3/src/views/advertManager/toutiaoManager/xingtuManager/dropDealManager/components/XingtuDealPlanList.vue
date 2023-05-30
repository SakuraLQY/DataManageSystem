<template>
    <BasicModal title="星图计划管理" :width="width" :visible="visible"  :okButtonProps="{ class: { 'jee-hidden': false } }"  @cancel="handleCancel" cancelText="关闭">
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
            <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button> -->
            <!-- <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
            <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
            <a-dropdown v-if="selectedRowKeys.length > 0">
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" @click="batchHandleDelete">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    删除
                  </a-menu-item>
                  <a-menu-item key="2" @click="batchCreateCreatives">
                    <Icon icon="ant-design:delete-outlined"></Icon>
                    创建创意
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
        <XingtuDealPlanModal ref="registerModal" @success="handleSuccess"></XingtuDealPlanModal>
        <OpXingtuCreativeModal ref="registerCreativeModal" @success="handleSuccess"></OpXingtuCreativeModal>
      </div>
    </BasicModal>
  </template>
  
  <script lang="ts" setup>
    import { ref, reactive } from 'vue';
    import { BasicTable, useTable, TableAction } from '/@/components/Table';
    import { useListPage } from '/@/hooks/system/useListPage';
    import { columns } from '../XingtuDealPlan.data';
    import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from '../XingtuDealPlan.api';
    import { downloadFile } from '/@/utils/common/renderUtils';
    import XingtuDealPlanModal from './XingtuDealPlanModal.vue'
    import {BasicModal} from '/@/components/Modal';
    import OpXingtuCreativeModal from '/@/views/advertManager/toutiaoManager/xingtuManager/opXingtuCreative/components/OpXingtuCreativeModal.vue'
    import { getCreative } from '/@/views/advertManager/toutiaoManager/xingtuManager/opXingtuCreative/OpXingtuCreative.api';
    import { message } from 'ant-design-vue';

    const visible = ref<boolean>(false);
    const queryParam = ref<any>({});
    const toggleSearchStatus = ref<boolean>(false);
    const registerModal = ref();
    const registerCreativeModal = ref();
    const width = ref<number>(800);
    const dealRecord = ref<any>({});
    let accountId = ref<Number>(0);
    //注册table数据
    const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
      tableProps: {
        title: 'op_jrtt_promotion',
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
        name: "op_jrtt_promotion",
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
    queryParam.value.dealId = record.id;
    accountId.value = record.accountId;
    reload();
  }
  
    /**
     * 新增事件
     */
    function handleAdd() {
      registerModal.value.disableSubmit = false;
      // registerModal.value.add([dealRecord.value]);
    }
    
    /**
     * 编辑事件
     */
    function handleEdit(record: Recordable) {
      registerModal.value.disableSubmit = false;
      registerModal.value.edit(accountId.value, record);
    }
    const obj = reactive<Record<string, any>>({
      accountId: undefined,
      adId: undefined,
      adIdLong: undefined,
      trillId: undefined
    })

    let selectRows = ref();
    let res = ref([]);
    function batchCreateCreatives() {
      selectRows.value = tableContext[2].selectedRows.value;
      res.value = [];
      for(let i = 0;i < selectRows.value.length; i++) {
        if(selectRows.value[i].creativeId === null){
          res.value.push(selectRows.value[i]);
        }
      }
      if(res.value.length === 0) {
        message.warning('请选择未创建创意的计划');
        return;
      }
      
      res.value.accountId = accountId.value + '';
      registerCreativeModal.value.disableSubmit = false;
      registerCreativeModal.value.edit(res.value,2);
    }

    /**
     * 广告创意
     */
    function handleCreative(record: Recordable) {
      registerCreativeModal.value.disableSubmit = false;
      getCreative({accountId:accountId.value, adId:record.id}).then((res: any)=>{
        if(res === null) {
          obj.accountId = accountId.value + '';
          obj.adId = record.id + '';
          obj.adIdLong = record.adId;
          obj.trillId = record.awemeAccount;
          registerCreativeModal.value.edit(obj,0);
        }else {
          res.accountId = res.accountId + '';
          res.adId = res.adId + '';
          res.adIdLong = record.adId;
          res.trillId = record.awemeAccount;
          res.dealName = record.dealName;
          res.dealId = record.dealId;
          if(res.isFeedAndFavSee === 1) {
            res.isFeedAndFavSee = true;
          }else {
            res.isFeedAndFavSee = false;
          }
          if(res.enableSmartSource === 'ON') {
            res.enableSmartSource = true;
          }else {
            res.enableSmartSource = false;
          }
          registerCreativeModal.value.edit(res,1);
        }
      })
      
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
  
    function edit(record){
      visible.value = true;
      // 初始化
      queryParam.value.dealId = record.id;
      dealRecord.value = record; 
      reload();
    }
     
    /**
     * 下拉操作栏
     */
    function getDropDownAction(record) {
      return [ {
          label: '删除',
          popConfirm: {
            title: '是否确认删除',
            confirm: handleDelete.bind(null, record),
          }
        },{
          label: '广告创意',
          onClick: handleCreative.bind(null, record),
        }
      ]
    }
  
    function handleCancel() {
      visible.value = false;
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
    
    defineExpose({
      edit,
      visible,
      accountId,
      handleBegin
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
    }
  </style>
  