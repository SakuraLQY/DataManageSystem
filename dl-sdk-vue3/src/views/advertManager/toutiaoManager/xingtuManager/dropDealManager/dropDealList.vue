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
              <j-search-select
                placeholder="请选择投放账号"
                v-model:value="queryParam.accountId"
                dict="open_gateway.op_jrtt_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id"
                allowClear
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="主播">
              <j-search-select
                placeholder="请选择主播"
                v-model:value="queryParam.anchorId"
                dict="open_gateway.op_anchor_plan,plan_name,id"
                allowClear
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="创建用户">
              <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,realname,username" allowClear />
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
                  <a-select-option value="campaignName">广告组名称</a-select-option>
                  <a-select-option value="campaignId">头条广告ID</a-select-option>
                </a-select>
                <a-input style="width: 60%" v-model:value="selectVal" />
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
        <a-button type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
              <a-menu-item key="2" @click="batchCreatePlan">
                <Icon icon="ant-design:plus-outlined"></Icon>
                批量创建计划
              </a-menu-item>
            </a-menu>
          </template>
          <a-button
            >批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!-- 头条广告 -->
      <template #campaignId_data="{ record }">
        <a-button size="small" type="error" v-if="!record.campaignId" @click="makeUp(record)">补建广告组</a-button>
        <span v-else>{{ record.campaignId }}</span>
      </template>
      <!-- 主播 -->
      <template #anchorPlanName_data="{ record }">
        <a-tag color="success" v-if="record.anchorPlanId">{{ record.anchorPlanName }}</a-tag>
        <a-tag color="error" v-else>未选择</a-tag>
      </template>
      <!-- 下载链接 -->
      <template #pkgUrl_data="{ record }">
        <a-tooltip>
          <template #title>{{ record.downloadUrl }}</template>
          <a-button size="small" type="primary">查看</a-button>
        </a-tooltip>
      </template>
      <!-- 监测链接 -->
      <template #dealArgs_data="{ record }">
        <!-- 文字提示 -->
        <a-tooltip>
          <template #title>{{ record.dealArgs }}</template>
          <a-button size="small" type="primary">查看</a-button>
        </a-tooltip>
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
    <DropDealModal ref="registerModal" @success="handleSuccess"></DropDealModal>
    <XingtuDealPlanModal ref="createPlanModal" @success="handleSuccess"></XingtuDealPlanModal>
    <XingtuDealPlanListModel ref="planListModel"></XingtuDealPlanListModel>
    <!-- 补建广告组 -->
    <a-modal v-model:visible="createVisible" title="补建广告组" :width="800" style="top: 20px" @cancel="handleCancel" @ok="handleMakeUp">
      <PageWrapper title="头条广告参数">
        <a-card>
          <!-- 广告组名称 -->
          <CollapseContainer title="广告组名称">
            <div class="div-style">
              <a-input v-model:value="markUpData.campaignName" placeholder="请输入广告组名称" />
            </div>
          </CollapseContainer>
          <!-- 广告组状态 -->
          <CollapseContainer title="广告组状态">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.operation" button-style="solid">
                <a-radio-button :value="CampaignEnum.OPERATION.ENABLE">开启</a-radio-button>
                <a-radio-button :value="CampaignEnum.OPERATION.DISABLE">关闭</a-radio-button>
              </a-radio-group>
            </div>
          </CollapseContainer>
          <!-- 推广目的 -->
          <CollapseContainer title="推广目的">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.landingType">
                <a-radio-button :value="CampaignEnum.LANDING_TYPE.LIVE" class="radio-button">
                  <div>
                    <div style="font-size: 14px">直播间</div>
                    <div style="font-size: 12px; margin-top: 4px; color: darkgrey">提升直播间的访问、互动、加粉</div>
                  </div>
                </a-radio-button>
                <a-radio-button :value="CampaignEnum.LANDING_TYPE.AWEME" class="radio-button">
                  <div style="font-size: 14px">抖音号</div>
                  <div style="font-size: 12px; margin-top: 4px; color: darkgrey">提升抖音号的访问、互动、加粉</div>
                </a-radio-button>
              </a-radio-group>
            </div>
          </CollapseContainer>
          <!-- 广告组预算 -->
          <CollapseContainer title="广告组预算">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.budgetMode" button-style="solid">
                <a-radio-button :value="CampaignEnum.BUDGET_MODE.INFINITE">不限</a-radio-button>
                <a-radio-button :value="CampaignEnum.BUDGET_MODE.DAY">指定预算</a-radio-button>
              </a-radio-group>
            </div>
          </CollapseContainer>
          <!-- 预算择优分配 -->
          <div v-if="markUpData.budgetMode == CampaignEnum.BUDGET_MODE.DAY">
            <CollapseContainer title="预算择优分配">
              <div class="div-style">
                <a-radio-group v-model:value="markUpData.campaignBudgetOptimization" button-style="solid">
                  <a-radio-button :value="CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.OFF">不开启</a-radio-button>
                  <a-radio-button :value="CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.ON">启用</a-radio-button>
                </a-radio-group>
              </div>
            </CollapseContainer>
            <!-- 日预算 -->
            <CollapseContainer title="日预算">
              <div class="div-style">
                <a-input v-model:value="markUpData.budget" placeholder="请输入" prefix="￥" suffix="元" @change="onChangePrice" />
                <div style="color: crimson">
                  {{ priceTip }}
                </div>
              </div>
            </CollapseContainer>
          </div>
        </a-card>
      </PageWrapper>
    </a-modal>
  </div>
</template>

<script lang="ts" name="vendor-jrttDeal" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, subGameTypeOptions, jrttPkg } from './dropDeal.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, bind, siteSave, makeUpDeal } from './dropDeal.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import DropDealModal from './components/dropDealModal.vue';
  import XingtuDealPlanModal from './components/XingtuDealPlanModal.vue';
  import XingtuDealPlanListModel from './components/XingtuDealPlanList.vue';
  import GameOptionForm from '/@/views/common/gameOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import * as CampaignEnum from './CampaignEnum';
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import moment, { Moment } from 'moment';

  const selectMode= ref('campaignName');
  const selectVal= ref();
  const dateRange = ref<Moment[]>([])
  const selectForm= ref();

  let priceTip = ref<string>('');
  const createVisible = ref<boolean>(false);
  const { createMessage } = useMessage();
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const createPlanModal = ref();
  const planListModel = ref();
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  };
  const siteRegisterModal = ref();
  const markUpData = reactive<Record<string, any>>({
    id: '',
    campaignName: '',
    operation: CampaignEnum.OPERATION.ENABLE,
    landingType: CampaignEnum.LANDING_TYPE.LIVE,
    budgetMode: CampaignEnum.BUDGET_MODE.INFINITE,
    campaignBudgetOptimization: CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.OFF,
    budget: 0.0,
  });
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ad_deal',
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
      name: 'ad_deal',
      url: getExportUrl,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const [
    registerTable,
    { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource },
    { rowSelection, selectedRowKeys, selectedRows },
  ] = tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  /**
   * 补建广告组
   */
  function makeUp(record) {
    markUpData.id = record.id;
    markUpData.campaignName = record.campaignName;
    createVisible.value = true;
  }

  /**
   * 取消
   */
  function handleCancel() {
    markUpData.id = '';
    markUpData.budget = 0.0;
    markUpData.campaignName = '';
    markUpData.operation = CampaignEnum.OPERATION.ENABLE;
    markUpData.landingType = CampaignEnum.LANDING_TYPE.LIVE;
    markUpData.budgetMode = CampaignEnum.BUDGET_MODE.INFINITE;
    markUpData.campaignBudgetOptimization = CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.OFF;
  }

  /**
   * 补建广告组
   */
  function handleMakeUp() {
    let budget = markUpData.budget;
    let budgetMode = markUpData.budgetMode;
    if (markUpData.campaignName == '' || markUpData.campaignName == null || markUpData.campaignName == undefined) {
      createMessage.warn('广告组名称不能为空');
      return;
    }
    if (budgetMode == CampaignEnum.BUDGET_MODE.DAY) {
      if (budget == undefined || budget == '') {
        createMessage.warn('日预算不能为空');
        return;
      } else {
        budget = budget - 0;
        if (budget < 300 || budget > 9999999.99) {
          createMessage.warn('日预算值不合法');
          return;
        }
      }
    }
    makeUpDeal(markUpData).finally(() => {
      reload();
      createVisible.value = false;
    });
  }

  /**
   * 批量创建计划
   */

  function batchCreatePlan(){
    createPlanModal.value.batchCreatePlan(selectedRows.value)
  }
  /**
   * 日预算变化
   */
  function onChangePrice(e) {
    let value = e.target.value - 0;
    if (value < 100 || value > 9999999.99) {
      priceTip.value = '预算范围，不少于300元，不超过9999999.99元';
    } else {
      priceTip.value = '';
    }
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

  function clickPlanManger(record) {
    // planListModel.value.visible = true;
    // planListModel.value.accountId = record.accountId;
    planListModel.value.handleBegin(record);
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
      {
        label:'计划管理',
        onClick: clickPlanManger.bind(null, record)
      }
    ];
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
    if(selectMode.value === 'campaignName'){
      queryParam.value.campaignId = null;
      queryParam.value.campaignName = selectVal.value;
    }else{
      queryParam.value.campaignId = selectVal.value;
      queryParam.value.campaignName = null;
    }
    reload();
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
  .div-style {
    margin-top: 10px;
  }
  .radio-button {
    width: 208px;
    height: 70px;
    margin-left: 5px;
  }
</style>
