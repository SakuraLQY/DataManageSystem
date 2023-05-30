<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="定向包名称">
              <a-input placeholder="请选择定向包名称" v-model:value="queryParam.name" maxLength="20" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="定向包类型">
              <j-dict-select-tag placeholder="请输入定向包类型" v-model:value="queryParam.landingType" dictCode="landing_type" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="投放账号">
              <j-search-select placeholder="请选择投放账号" v-model:value="queryParam.accountId" dict="open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2',nick_name,id" allowClear/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :lg="8">
              <a-form-item label="创建用户">
                <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,realname,username" allowClear/>
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
      <template #deliveryRange="{ record }">
        <Tag color="green" v-if="record.deliveryRange === 'DEFAULT'">
          {{ '默认' }}
        </Tag>
        <Tag color="green" v-if="record.deliveryRange === 'UNION'">
          {{ '穿山甲' }}
        </Tag>
      </template>
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary"  preIcon="ant-design:import-outlined" @click="sync">同步</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls" v-show="false"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls" v-show="false">导入</j-upload-button>
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
        <TableAction :actions="getTableAction(record)"/>
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
    <OpJrttAudienceModal ref="registerModal" @success="handleSuccess"></OpJrttAudienceModal>
    <div>
      <a-modal :title="title" :width="width" :visible="visible" @ok="handleOk"  @cancel="handleCancel" cancelText="关闭">
            <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol2" :wrapperCol="wrapperCol2" >
              <a-row>
                <a-col :span="15">
                  <a-form-item :label="label" >
                    <j-select-multiple placeholder="请选择投放账号" dictCode="open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ,nick_name,id" v-model:value="accountIdList" />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-form>
        </a-modal>
    </div>
  </div>
</template>

<script lang="ts" name="advert-opJrttAudience" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpJrttAudience.data';
  import { list, shareAudience, syncAudience, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpJrttAudience.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpJrttAudienceModal from './components/OpJrttAudienceModal.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JSelectMultiple from '/@/components/Form/src/jeecg/components/JSelectMultiple.vue';
  import func from '../../../../../vue-temp/vue-editor-bridge';
  import { watch } from 'fs-extra';
  import { message } from 'ant-design-vue';
  
  const queryParam = ref<any>({});
  let accountIdList = ref();
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const labelCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  let accountId = ref();
  let title = ref();
  let label = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_jrtt_audience',
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
      name: "op_jrtt_audience",
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
  let opJrttAudience = ref();
  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }

  let type = ref();

  /**
   * 同步定向包
   */
  function sync() {
    visible.value = true;
    type = 1;
    label.value = "投放账号";
    title.value = "同步定向包";
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }

  

  /**
   * 共享事件
   */
  function handleShare(record: Recordable) {
    type = 2;
    visible.value = true;
    label.value = "被共享账号";
    title.value = "共享定向包";
    opJrttAudience.value = record;
  }

  /**
   * 共享事件
   */
  function handleOk() {
    if(accountIdList.value === '' || accountIdList.value === null || accountIdList.value === undefined) {
      message.warning('账号不能为空');
      return false;
    }
    if(type === 2) {
      opJrttAudience.value.accountIdList = accountIdList.value;
      shareAudience(opJrttAudience.value).then((res: any)=>{
          handleSuccess();
        }).finally(() => {
          visible.value = false;
          accountIdList.value = undefined;
        });
    }else {
      syncAudience({accountIdList:accountIdList.value}).then((res: any)=>{
        handleSuccess();
      }).finally(() => {
          visible.value = false;
          accountIdList.value = undefined;
        });
    }
    
  }

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
    accountIdList.value = undefined;
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
      {
        label: '共享',
        onClick: handleShare.bind(null, record),
      },
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
