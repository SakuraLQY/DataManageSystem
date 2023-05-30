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
              <j-search-select placeholder="请选择游戏包" v-model:value="queryParam.pkgId" :dict="jrttPkg" allowClear />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="投放账号ID">
              <j-search-select
                placeholder="请选择投放账号"
                v-model:value="queryParam.accountId"
                dict="open_gateway.op_ks_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id"
                allowClear
              />
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="渠道子账号ID">
              <j-search-select
                v-model:value="queryParam.channelSubAccountId"
                dict="open_gateway.op_channel_sub_account where channel_id = 9,sub_account_name,id"
                :disabled="disabled"
                placeholder="请选择渠道子账号ID"
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
                  <a-select-option value="dealName">广告组名称</a-select-option>
                  <a-select-option value="campaignId">快手计划ID</a-select-option>
                  <a-select-option value="dealId">广告ID</a-select-option>
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
      <!-- 预算 -->
      <template #budget_data="{ record }">
        <span v-if="record.budgetMode == 1">{{ record.budget == 0 ? '不限' : record.budget }}</span>
        <a-popover title="分日预算" v-else-if="record.budgetMode == 2">
          <template #content>
            <WeekBudgetTable ref="weekRegisterModal" :data="record.budget" @syncWeekData="syncWeekData" :canEdit="false"></WeekBudgetTable>
          </template>
          <a-button size="small" type="primary">分日预算</a-button>
        </a-popover>
        <a-tag color="error" v-else>不限</a-tag>
        <edit-outlined style="float: right; vertical-align: middle" @click="showDditBudget(record)" />
      </template>
      <!-- 打包状态 -->
      <template #pack_state="{ record }">
        <span v-if="record.packState == 0">等待操作</span>
        <span v-if="record.packState == 2">打包中</span>
        <span v-if="record.packState == 3">打包成功</span>
        <span v-if="record.packState == 6">解析成功</span>
        <span v-if="record.packState == 7"> <div style="color: red">解析失败，请重新创建应用</div></span>
        <span v-if="record.packState == 4">
          <a-button size="small" type="primary" @click="pKgAPP(record)">重新打包</a-button>
        </span>
      </template>
      <!-- 下载链接 -->
      <template #pkgUrl_data="{ record }">
        <a-tooltip>
          <template #title>{{ record.pkgUrl }}</template>
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
    <DealPlanModal ref="registerModal" @success="handleSuccess"></DealPlanModal>
    <!-- 补建广告组 -->
    <a-modal
      v-model:visible="createVisible"
      :title="isMakeUp ? '补建广告组' : '修改预算'"
      :width="800"
      style="top: 20px"
      @cancel="handleCancel"
      @ok="handleMakeUp"
    >
      <PageWrapper title="快手广告参数">
        <a-card>
          <!-- 广告组名称 -->
          <CollapseContainer title="广告计划名称" v-if="isMakeUp">
            <div class="div-style">
              <a-input v-model:value="markUpData.dealName" showCount :maxlength="100" placeholder="请输入广告计划名称" />
            </div>
          </CollapseContainer>
          <!-- 推广目的 -->
          <CollapseContainer title="推广目的" v-if="isMakeUp">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.type">
                <a-radio-button :value="CampaignEnum.TYPE.APP" class="radio-button">
                  <div>
                    <div style="font-size: 14px">提升应用安装</div>
                    <div style="font-size: 12px; margin-top: 4px; color: darkgrey">提升用户的下载、安装、激活等行为</div>
                  </div>
                </a-radio-button>
              </a-radio-group>
            </div>
          </CollapseContainer>
          <!-- 投放方式 -->
          <CollapseContainer title="投放方式" v-if="isMakeUp">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.bidType">
                <a-radio-button :value="CampaignEnum.BID_TYPE.DEFAULT" class="radio-button">
                  <div>
                    <div style="font-size: 14px">成本优先</div>
                    <div style="font-size: 12px; margin-top: 4px; color: darkgrey">控制成本，尽可能消耗预算，原常规投放</div>
                  </div>
                </a-radio-button>
                <a-radio-button :value="CampaignEnum.BID_TYPE.MAX" class="radio-button">
                  <div>
                    <div style="font-size: 14px">最大转化</div>
                    <div style="font-size: 12px; margin-top: 4px; color: darkgrey">消耗更多预算，尽可能获取最多的转化次数</div>
                  </div>
                </a-radio-button>
              </a-radio-group>
            </div>
          </CollapseContainer>
          <!-- 计划日预算 -->
          <CollapseContainer title="计划日预算">
            <div class="div-style">
              <a-radio-group v-model:value="markUpData.budgetMode" button-style="solid">
                <a-radio-button :value="CampaignEnum.BUDGET_MODE.INFINITE" :disabled="markUpData.bidType == CampaignEnum.BID_TYPE.MAX ? true : false"
                  >不限</a-radio-button
                >
                <a-radio-button :value="CampaignEnum.BUDGET_MODE.DAY">统一预算</a-radio-button>
                <a-radio-button :value="CampaignEnum.BUDGET_MODE.WEEK">分日预算</a-radio-button>
              </a-radio-group>
            </div>
            <div class="div-style">
              <!-- 统一预算 -->
              <div v-if="markUpData.budgetMode == CampaignEnum.BUDGET_MODE.DAY">
                <a-input-number v-model:value="markUpData.budget" :min="0" :max="100000000" :precision="0" style="width: 80%" />
                <div style="color: crimson"> 不小于 500 元，不超过 100000000 元，0表示预算不限，仅支持输入整数 </div>
              </div>
              <!-- 分日预算 -->
              <div v-if="markUpData.budgetMode == CampaignEnum.BUDGET_MODE.WEEK">
                <WeekBudgetTable ref="weekRegisterModal" :data="markUpData.budgetWeek" @syncWeekData="syncWeekData" :canEdit="true"></WeekBudgetTable>
              </div>
            </div>
          </CollapseContainer>
        </a-card>
      </PageWrapper>
    </a-modal>
  </div>
</template>

<script lang="ts" name="vendor-jrttDeal" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, subGameTypeOptions, jrttPkg } from './dealPlan.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, siteSave, makeUpDeal, modifyBudget, createApp } from './dealPlan.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import DealPlanModal from './components/dealPlanModal.vue';
  import GameOptionForm from '/@/views/common/gameOptionForm.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import * as CampaignEnum from './CampaignEnum';
  import { PageWrapper } from '/@/components/Page';
  import { CollapseContainer } from '/@/components/Container/index';
  import WeekBudgetTable from './components/WeekBudgetTable.vue';
  import moment, { Moment } from 'moment';
  import { EditOutlined } from '@ant-design/icons-vue';

  const isMakeUp = ref<boolean>(true);
  const selectMode = ref('dealName');
  const selectVal = ref();
  const dateRange = ref<Moment[]>([]);
  const selectForm = ref();
  let weekData = [0, 0, 0, 0, 0, 0, 0];
  let priceTip = ref<string>('');
  const createVisible = ref<boolean>(false);
  const { createMessage } = useMessage();
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  };
  const siteRegisterModal = ref();
  const markUpData = reactive<Record<string, any>>({
    id: '',
    campaignName: '',
    budget: 0,
    budgetWeek: '0,0,0,0,0,0,0',
    type: CampaignEnum.TYPE.APP,
    bidType: CampaignEnum.BID_TYPE.DEFAULT,
    budgetMode: CampaignEnum.BUDGET_MODE.INFINITE,
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

  let syncWeekData = (e: any) => {
    weekData = e;
  };

  //实时更新打包状态
  window.setInterval(() => {
    list({}).then((res) => {
      for (let i = 0; i < res.records.length; i++) {
        updateTableDataRecord(res.records[i].id, res.records[i]);
      }
    });
  }, 1000 * 5);

  /**
   * 修改预算
   */
  function showDditBudget(record) {
    isMakeUp.value = false;
    makeUp(record);
  }

  /**
   * 补建广告组
   */
  function makeUp(record) {
    markUpData.id = record.id;
    markUpData.dealName = record.dealName;
    markUpData.budgetMode = record.budgetMode;
    if (record.budgetMode == CampaignEnum.BUDGET_MODE.WEEK) {
      markUpData.budgetWeek = record.budget;
      markUpData.budget = 0;
    } else {
      markUpData.budget = record.budget;
      markUpData.budgetWeek = null;
    }
    createVisible.value = true;
  }

  /**
   * 取消
   */
  function handleCancel() {
    markUpData.id = '';
    markUpData.budget = 0.0;
    markUpData.dealName = '';
    markUpData.type = CampaignEnum.TYPE.APP;
    markUpData.bidType = CampaignEnum.BID_TYPE.DEFAULT;
    markUpData.budgetMode = CampaignEnum.BUDGET_MODE.INFINITE;
    markUpData.budgetWeek = null;
    isMakeUp.value = true;
  }

  function pKgAPP(record) {
    createApp({ id: record.id }, handleSuccess);
  }

  /**
   * 补建广告组
   */
  function handleMakeUp() {
    let budget = markUpData.budget;
    let budgetMode = markUpData.budgetMode;
    if (isMakeUp.value) {
      if (markUpData.dealName == '' || markUpData.dealName == null || markUpData.dealName == undefined) {
        createMessage.warning('广告计划名不能为空');
        return;
      }
    }
    // 预算判断
    if (markUpData.budgetMode == CampaignEnum.BUDGET_MODE.WEEK) {
      for (let i in weekData) {
        if (weekData[i] != 0) {
          if (weekData[i] < 500 || weekData[i] > 100000000) {
            createMessage.warning('分日预算有误');
            return;
          }
        } else {
          if (isMakeUp.value && markUpData.bidType == CampaignEnum.BID_TYPE.MAX) {
            createMessage.warning('最大转化必须设置预算');
            return;
          }
        }
      }
      markUpData.budgetWeek = weekData.join(',');
    } else if (markUpData.budgetMode == CampaignEnum.BUDGET_MODE.DAY) {
      if (markUpData.budget != 0) {
        if (markUpData.budget < 500 || markUpData.budget > 100000000) {
          createMessage.warning('日预算有误');
          return;
        }
      }
    } else {
      markUpData.budget = 0;
    }
    if (isMakeUp.value) {
      makeUpDeal(markUpData).finally(() => {
        reload();
        createVisible.value = false;
        isMakeUp.value = true;
      });
    } else {
      modifyBudget(markUpData).finally(() => {
        reload();
        createVisible.value = false;
        isMakeUp.value = true;
      });
    }
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
    // 日期范围
    if (dateRange.value != null && dateRange.value.length > 0) {
      queryParam.value.startDate = dateRange.value[0].format('YYYY-MM-DD');
      queryParam.value.endDate = dateRange.value[1].format('YYYY-MM-DD');
    } else {
      queryParam.value.startDate = null;
      queryParam.value.endDate = null;
    }
    // 多条件
    if (selectMode.value === 'dealName') {
      queryParam.value.id = null;
      queryParam.value.campaignId = null;
      queryParam.value.dealName = selectVal.value;
    } else if (selectMode.value === 'campaignId') {
      queryParam.value.id = null;
      queryParam.value.dealName = null;
      queryParam.value.campaignId = selectVal.value;
    } else {
      queryParam.value.id = selectVal.value;
      queryParam.value.dealName = null;
      queryParam.value.campaignId = null;
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
    width: 230px;
    height: 90px;
    margin-left: 5px;
  }
</style>
