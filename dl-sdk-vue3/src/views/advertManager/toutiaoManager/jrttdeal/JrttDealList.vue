<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
          <a-col :lg="8">
            <a-form-item label="子游戏类型">
              <j-dict-select-tag placeholder="请选择子游戏类型" v-model:value="queryParam.subGameType" :options="subGameTypeOptions" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="游戏包ID">
              <j-search-select placeholder="请选择游戏包" v-model:value="queryParam.pkgId" :dict="jrttPkg" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="投放账号ID">
              <j-search-select placeholder="请选择投放账号" v-model:value="queryParam.accountId" dict="open_gateway.op_jrtt_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="渠道子账号ID">
              <j-search-select
                v-model:value="queryParam.channelSubAccountId"
                dict="open_gateway.op_channel_sub_account where channel_id = 4,sub_account_name,id"
                :disabled="disabled"
                placeholder="请选择渠道子账号ID"
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="打包状态">
              <j-dict-select-tag placeholder="请选择打包状态" v-model:value="queryParam.packState" :options="packTypeOptions" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="创建用户">
              <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,realname,username" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="创建日期">
              <a-range-picker v-model:value="dateRange" />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="更多条件">
              <a-input-group compact>
                <a-select v-model:value="selectMode">
                  <a-select-option value="dealName">广告名称</a-select-option>
                  <a-select-option value="dealId">广告ID</a-select-option>
                </a-select>
                <a-input style="width: 70%" v-model:value="selectVal" />
              </a-input-group>
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
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
              <a-menu-item key="2" @click="batchCreateObject">
                <Icon icon="ant-design:delete-outlined"></Icon>
                创建项目
              </a-menu-item>
              <a-menu-item key="3" @click="batchCreatePromotion">
                <Icon icon="ant-design:delete-outlined"></Icon>
                创建广告
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
        <a-button type="primary" @click="batchCreateSite" v-if="selectedRowKeys.length > 0">
          创建站点
          <Icon icon="ant-design:desktop-outlined"></Icon>
        </a-button>
      </template>
      <!-- 安装包下载链接 -->
      <template #pkgUrl_data="{ record }">
        <div v-if="record.pkgUrl != null">
          <!-- 文字提示 -->
          <a-tooltip>
            <template #title>{{ record.pkgUrl }}</template>
            <a-button type="primary" shape="round">查看</a-button>
          </a-tooltip>
        </div>
        <span v-else>暂无</span>
      </template>
      <!-- 打包状态 -->
      <template #packState_data="{ record }">
          <a-tag color="default" v-if="record.packState == 0">等待操作</a-tag>
          <a-tag color="warning" v-if="record.packState == 1">等待打包</a-tag>
          <a-tag color="processing" v-if="record.packState == 2">正打包中</a-tag>
          <a-tag color="success" v-if="record.packState == 3">打包成功</a-tag>
          <a-button type="primary" v-if="record.packState == 4" @click="reExtend(record.id)">重新打包</a-button>
      </template>
      <!-- 站点状态 -->
      <template #siteStatus_data="{ record }">
        <!-- 文字提示 -->
        <a-tooltip v-if="record.jrttSiteId != null">
          <template #title>站点ID：{{ record.siteId }}  头条站点ID：{{ record.jrttSiteId }}</template>
          <a-button type="primary" shape="round">{{ record.siteName }}</a-button>
        </a-tooltip>
        <a-tag color="error" v-else >没有站点</a-tag>
      </template>
      <!-- 监测链接 -->
      <template #dealArgs_data="{ record }">
        <!-- 文字提示 -->
        <a-tooltip>
          <template #title>{{ record.dealArgs }}</template>
          <a-button type="primary" shape="round">查看</a-button>
        </a-tooltip>
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
    <JrttDealModal ref="registerModal" @success="handleSuccess"></JrttDealModal>
    <OpJrttProjectModal ref="projectRegisterModal"></OpJrttProjectModal>
    <JrttSiteModal ref="siteRegisterModal" :successCallback="reload"></JrttSiteModal>
    <OpJrttPromotionFormModal ref="promotionFormRegisterModal"></OpJrttPromotionFormModal>
    <OpJrttPromotionListModal ref="promotionListRegisterModal"></OpJrttPromotionListModal>
  </div>
</template>

<script lang="ts" name="vendor-jrttDeal" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, subGameTypeOptions, packTypeOptions, jrttPkg } from './JrttDeal.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, siteSave, extendPackage } from './JrttDeal.api';
  import { getProjectInfo,getEditInfoUrl } from './JrttProject.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import JrttDealModal from './components/JrttDealModal.vue'
  import GameOptionForm from '/@/views/common/gameOptionForm.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import OpJrttProjectModal from './components/JrttProjectModal.vue';
  import ActionInterest from './components/ActionInterest.vue';
  import { defHttp } from '/@/utils/http/axios';
  import { message } from 'ant-design-vue';
  import JrttSiteModal from './components/JrttSiteModal.vue';
  import OpJrttPromotionFormModal from './components/JrttPromotionFormModal.vue';
  import OpJrttPromotionListModal from './components/JrttPromotionListModal.vue';
  import moment, { Moment } from 'moment';
  
  const selectMode= ref('dealName');
  const selectVal= ref();

  const queryParam = ref<any>({});
  const dateRange = ref<Moment[]>([])
  const selectForm= ref();
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  let getGameVal = (e:any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  }
  const projectRegisterModal = ref();
  const promotionFormRegisterModal = ref();
  const promotionListRegisterModal = ref();
  const siteRegisterModal = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ad_deal',
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
      name: "ad_deal",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys, selectedRows }] = tableContext;
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
   * 批量创建项目
   */
  function batchCreateObject() {
    projectRegisterModal.value.disableSubmit = false;
    let records = selectedRows.value;
    let tempAccountId = records[0];
    if(records.length > 1){
      for(let record of records){
        if(record.accountId != tempAccountId){
          message.warn("请选择同一账号下的广告创建计划");
          return;
        }
      }
    }
    projectRegisterModal.value.batchCreateObject(selectedRows.value);
  }
  /**
   * 编辑项目
   */
   function editProject(record){
    defHttp.get({url:getEditInfoUrl,params:{dealId: record.id}}).then(projectInfo =>{
      if(projectInfo == null){
        message.info("此广告下没有创建项目")
        return
      }
      projectInfo.deliveryRange = JSON.parse(projectInfo.deliveryRange);
      projectInfo.deliverySetting = JSON.parse(projectInfo.deliverySetting);
      projectInfo.optimizeGoal = JSON.parse(projectInfo.optimizeGoal);
      projectInfo.audience = JSON.parse(projectInfo.audience);
      projectRegisterModal.value.edit(record.accountId, projectInfo);
    });
  }

  /**
   * 广告列表
   */
  function editPromotionList(record){
    promotionListRegisterModal.value.edit(record);
  }

  function batchCreateSite() {
    siteRegisterModal.value.visible = true;
    siteRegisterModal.value.dealDataList = selectedRowKeys.value;
  }

  function batchCreatePromotion() {
    let selectRows = tableContext[2].selectedRows.value;
    for(let i=1; i<selectRows.length; i++){
      if(selectRows[i-1].pkgId != (selectRows[i].pkgId)){
        message.warning('需要选择渠道包一致的游戏进行批量操作');
        return;
      }else if(selectRows[i-1].accountId != (selectRows[i].accountId)){
        message.warning('需要选择投放账号一致的游戏进行批量操作');
        return;
      }
    }
    promotionFormRegisterModal.value.disableSubmit = false;
    promotionFormRegisterModal.value.add(selectRows);
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
      },{
        label: '编辑项目',
        onClick: editProject.bind(null, record),
      },{
        label: '编辑广告',
        onClick: editPromotionList.bind(null, record),
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    // 日期范围
    if(dateRange.value != null && dateRange.value.length > 0) {
      queryParam.value.startDate = dateRange.value[0].format('YYYY-MM-DD')
      queryParam.value.endDate = dateRange.value[1].format('YYYY-MM-DD')
    } else {
      queryParam.value.startDate = null;
      queryParam.value.endDate = null;
    }
    // 多条件
    if(selectMode.value === 'dealName'){
      queryParam.value.id = null;
      queryParam.value.dealName = selectVal.value;
    }else{
      queryParam.value.id = selectVal.value;
      queryParam.value.dealName = null;
    }
    reload();
  }

  /**
   * 重新打包
   */
  function reExtend(id){
    extendPackage({id})
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    // 游戏/子游戏重置
    selectForm.value.reload();
    // 日期范围重置
    dateRange.value = null;
    // 多条件重置
    selectVal.value = null;
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
