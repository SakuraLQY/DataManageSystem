<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="厂商名">
              <a-input placeholder="请输入厂商名" v-model:value="queryParam.vendorName" allowClear></a-input>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="支付宝">
              <j-search-select
                allowClear
                placeholder="请选择支付宝渠道"
                v-model:value="queryParam.aliPayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 1,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="汇付宝">
              <j-search-select
                allowClear
                placeholder="请选择汇付宝渠道"
                v-model:value="queryParam.heePayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 2,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="易宝">
              <j-search-select
                allowClear
                placeholder="请选择易宝渠道"
                v-model:value="queryParam.yeePayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 3,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="爱贝">
              <j-search-select
                allowClear
                placeholder="请选择爱贝渠道"
                v-model:value="queryParam.iappPayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 4,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="现在支付">
              <j-search-select
                allowClear
                placeholder="请选择现在支付渠道"
                v-model:value="queryParam.ipaynowPayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 5,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="现在支付app">
              <j-search-select
                allowClear
                placeholder="请选择现在支付app渠道"
                v-model:value="queryParam.ipaynowappPayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 6,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="微信">
              <j-search-select
                allowClear
                placeholder="请选择微信渠道"
                v-model:value="queryParam.wxPayVendor"
                dict="open_gateway.op_pay_vendor where pay_type = 7,pay_vendor_name,id"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="创建人">
              <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,realname,username" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="创建日期">
              <a-date-picker valueFormat="YYYY-MM-DD" placeholder="请选择创建日期" v-model:value="queryParam.createTime" />
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
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <!-- <a-button type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button> -->
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
          <a-button
            >批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!-- 列自定义 -->
      <!-- 支付宝 -->
      <template #ali_data="{ record }">
        <div v-if="record.aliPayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.aliPayVendorData.note }}</template>
            {{ record.aliPayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(1, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 汇付宝 -->
      <template #hee_data="{ record }">
        <div v-if="record.heePayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.heePayVendorData.note }}</template>
            {{ record.heePayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(2, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 易宝 -->
      <template #yee_data="{ record }">
        <div v-if="record.yeePayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.yeePayVendorData.note }}</template>
            {{ record.yeePayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(3, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 爱贝 -->
      <template #iapp_data="{ record }">
        <div v-if="record.iappPayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.iappPayVendorData.note }}</template>
            {{ record.iappPayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(4, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 现在支付 -->
      <template #ipaynow_data="{ record }">
        <div v-if="record.ipaynowPayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.ipaynowPayVendorData.note }}</template>
            {{ record.ipaynowPayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(5, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 现在支付app -->
      <template #ipaynowapp_data="{ record }">
        <div v-if="record.ipaynowappPayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.ipaynowappPayVendorData.note }}</template>
            {{ record.ipaynowappPayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(6, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!-- 微信 -->
      <template #wx_data="{ record }">
        <div v-if="record.wxPayVendorData != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.wxPayVendorData.note }}</template>
            {{ record.wxPayVendorData.payVendorName }}
          </a-tooltip>
          <!-- 分割线 -->
          <a-divider type="vertical" />
          <!-- 按钮 -->
          <a-button type="success" @click="sendPayVendorData(7, record)" size="small">查看</a-button>
        </div>
        <span v-else>暂未选择</span>
      </template>
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{ text }">
        <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{ text }">
        {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{ text }">
        <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpVendorModal ref="registerModal" @success="handleSuccess"></OpVendorModal>
    <ModalPayVendor ref="registerPayVendorData" />
  </div>
</template>

<script lang="ts" name="vendor-opVendor" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpVendor.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpVendor.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpVendorModal from './components/OpVendorModal.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import ModalPayVendor from './components/ShowPayVendorData.vue';
 
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const registerPayVendorData = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_vendor',
      api: list,
      columns,
      canResize: false,
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
      name: 'op_vendor',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] =
    tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  /**
   * 发送支付参数配置
   */
  function sendPayVendorData(payType, record) {
    switch (payType) {
      case 1:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.aliPayVendorData.payVendorName, note: record.aliPayVendorData.note };
        if (record.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.aliPayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 2:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.heePayVendorData.payVendorName, note: record.heePayVendorData.note };
        if (record.heePayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.heePayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 3:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.yeePayVendorData.payVendorName, note: record.yeePayVendorData.note };
        if (record.yeePayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.yeePayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 4:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.iappPayVendorData.payVendorName, note: record.iappPayVendorData.note };
        if (record.iappPayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.iappPayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 5:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.ipaynowPayVendorData.payVendorName, note: record.ipaynowPayVendorData.note };
        if (record.ipaynowPayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.ipaynowPayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 6:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.ipaynowappPayVendorData.payVendorName, note: record.ipaynowappPayVendorData.note };
        if (record.ipaynowappPayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.ipaynowappPayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      case 7:
        registerPayVendorData.value.payType = payType;
        registerPayVendorData.value.baseData = { name: record.wxPayVendorData.payVendorName, note: record.wxPayVendorData.note };
        if (record.wxPayVendorData.payVendorConf != '') {
          registerPayVendorData.value.confData = JSON.parse(record.wxPayVendorData.payVendorConf);
        } else {
          registerPayVendorData.value.confData = '';
        }
        break;
      default:
        break;
    }
    registerPayVendorData.value.showModal();
  }

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
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        },
      },
    ];
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
      text-align: center;
    }
  }
</style>
