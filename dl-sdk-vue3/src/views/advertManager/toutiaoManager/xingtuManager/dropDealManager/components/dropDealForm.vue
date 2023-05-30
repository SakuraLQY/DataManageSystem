<template>
  <a-spin :spinning="confirmLoading">
    <a-row>
      <a-col :span="12">
        <PageWrapper title="投放广告参数">
          <a-card :bodyStyle="{ padding: '0' }">
            <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-row>
                <a-col :span="24" v-if="formData.id == ''">
                  <a-form-item label="生成广告组条数">
                    <a-input-number size="large" :min="1" :max="100000" v-model:value="formData.dealNumbers" />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <div class="file-select">
                    <GameOptionModal ref="selectForm" @handlerModal="getGameVal"></GameOptionModal>
                  </div>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="游戏包ID" v-bind="validateInfos.pkgId">
                    <a-tag color="error" v-if="formData.subGameId == undefined || formData.subGameId == '' || formData.gameId == undefined"
                      >请先选择游戏/子游戏</a-tag
                    >
                    <a-select v-model:value="formData.pkgId" placeholder="请选择游戏包ID" @change="selectPkg" :disabled="disabled" v-else>
                      <a-select-option v-for="(item, index) in pkgList" :key="item.nick_name" :value="item.id">
                        {{ item.nick_name }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="投放账号ID" v-bind="validateInfos.accountIds">
                    <a-tag color="error" v-if="formData.subGameId == undefined || formData.subGameId == '' || formData.gameId == undefined"
                      >请先选择游戏/子游戏</a-tag
                    >
                    <a-select
                      v-model:value="formData.accountIds"
                      placeholder="请选择投放账号ID"
                      mode="multiple"
                      :disabled="disabled"
                      v-else-if="formData.id == ''"
                    >
                      <a-select-option v-for="item in accountList" :key="item.id" :value="item.id">
                        {{ item.nickName }}
                      </a-select-option>
                    </a-select>
                    <a-select v-model:value="formData.accountIds" placeholder="请选择投放账号ID" :disabled="disabled" v-else>
                      <a-select-option v-for="item in accountList" :key="item.id" :value="item.id">
                        {{ item.nickName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="主播" v-bind="validateInfos.anchorPlanId">
                    <a-select v-model:value="formData.anchorPlanId" placeholder="请选择主播" @change="selectAnchor" :disabled="disabled">
                      <a-select-option v-for="item in anchorDealList" :key="item.anchorId" :value="item.anchorId">
                        {{ item.anchorName }}
                      </a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="广告组名称" v-bind="validateInfos.campaignName">
                    <a-input v-model:value="formData.campaignName" placeholder="默认填充游戏名" showCount :maxlength="100" :disabled="disabled"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="监测链接" v-bind="validateInfos.dealArgs">
                    <a-textarea
                      v-model:value="formData.dealArgs"
                      placeholder="广告监测链接"
                      showCount :maxlength="512" auto-size
                      :disabled="disabled"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="下载链接" v-bind="validateInfos.downloadUrl">
                    <a-textarea
                      v-model:value="formData.downloadUrl"
                      placeholder="广告下载链接"
                      showCount :maxlength="255" auto-size
                      :disabled="disabled"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="广告组描述">
                    <a-input v-model:value="formData.dealDesc" placeholder="请输入广告组描述" :disabled="disabled"></a-input>
                  </a-form-item>
                </a-col>
              </a-row>
            </a-form>
          </a-card>
        </PageWrapper>
      </a-col>
      <a-col :span="12">
        <PageWrapper title="头条广告参数">
          <a-card>
            <!-- 广告组状态 -->
            <CollapseContainer title="广告组状态">
              <div class="div-style">
                <a-radio-group v-model:value="formData.operation" button-style="solid">
                  <a-radio-button :value="CampaignEnum.OPERATION.ENABLE">开启</a-radio-button>
                  <a-radio-button :value="CampaignEnum.OPERATION.DISABLE">关闭</a-radio-button>
                </a-radio-group>
              </div>
            </CollapseContainer>
            <!-- 推广目的 -->
            <CollapseContainer title="推广目的">
              <div class="div-style">
                <a-radio-group v-model:value="formData.landingType">
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
                <a-radio-group v-model:value="formData.budgetMode" button-style="solid">
                  <a-radio-button :value="CampaignEnum.BUDGET_MODE.INFINITE">不限</a-radio-button>
                  <a-radio-button :value="CampaignEnum.BUDGET_MODE.DAY">指定预算</a-radio-button>
                </a-radio-group>
              </div>
            </CollapseContainer>
            <!-- 预算择优分配 -->
            <div v-if="formData.budgetMode == CampaignEnum.BUDGET_MODE.DAY">
              <CollapseContainer title="预算择优分配">
                <div class="div-style">
                  <a-radio-group v-model:value="formData.campaignBudgetOptimization" button-style="solid">
                    <a-radio-button :value="CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.OFF">不开启</a-radio-button>
                    <a-radio-button :value="CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.ON">启用</a-radio-button>
                  </a-radio-group>
                </div>
              </CollapseContainer>
              <!-- 日预算 -->
              <CollapseContainer title="日预算">
                <div class="div-style">
                  <a-input v-model:value="formData.budget" placeholder="请输入" prefix="￥" suffix="元" @change="onChangePrice" />
                  <div style="color: crimson">
                    {{ priceTip }}
                  </div>
                </div>
              </CollapseContainer>
            </div>
          </a-card>
        </PageWrapper>
      </a-col>
    </a-row>
    <MessageModal ref="messageRegisterModal"></MessageModal>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JCheckbox from '/@/components/Form/src/jeecg/components/JCheckbox.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate, pkgInfoList, info } from '../dropDeal.api';
  import { operationOptions, landingTypeOptions, budgetModeOptions, campaignBudgetOptimizationOptions } from '../dropDeal.data';
  import { Form } from 'ant-design-vue';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { list } from '/@/views/advertManager/toutiaoManager/opputaccount/OpPutAccount.api';
  import MessageModal from '/@/views/common/messageModal.vue';
  import { PageWrapper } from '/@/components/Page';
  import * as CampaignEnum from '../CampaignEnum';
  import { CollapseContainer } from '/@/components/Container/index';
  import { QuestionCircleFilled } from '@ant-design/icons-vue';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => {} },
    formBpm: { type: Boolean, default: true },
  });
  const formRef = ref();
  const messageRegisterModal = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    campaignName: '',
    gameId: undefined,
    subGameId: undefined,
    accountIds: undefined,
    pkgId: undefined,
    dealArgs: '',
    dealDesc: '',
    downloadUrl: '',
    dealNumbers: 1,
    anchorPlanId: undefined,
    operation: CampaignEnum.OPERATION.ENABLE,
    landingType: CampaignEnum.LANDING_TYPE.LIVE,
    budgetMode: CampaignEnum.BUDGET_MODE.INFINITE,
    campaignBudgetOptimization: CampaignEnum.CAMPAIGN_BUDGET_OPTIMIZATION.OFF,
    budget: 0.0,
  });

  const selectForm = ref();
  const accountList = ref();
  let getGameVal = (e: any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
    formData.accountIds = defaultAccount;
    formData.pkgId = defaultpkg;
    defaultpkg = undefined;
    defaultAccount = undefined;
    changeAccountOptions(e.gameId, e.subGameId);
    getPkgList(e.gameId, e.subGameId);
  };
  // 查询投放账号
  const accountQueryParam = ref<any>({});
  accountQueryParam.value.pageNo = 1;
  accountQueryParam.value.pageSize = 9999;
  // 查询游戏包
  const pkgQueryParam = ref<any>({});

  let pkgList = ref([]);
  let responseData = ref([]);
  let anchorDealList = ref([]);
  let defaultpkg = undefined;
  let defaultAccount = undefined;
  let priceTip = ref<string>('');
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);

  //表单验证
  const validatorRules = {
    campaignName: [{ required: true, message: '请输入广告组名称!' }],
    downloadUrl: [{ required: true, message: '请输入下载链接!' }],
    dealArgs: [{ required: true, message: '请输入监测链接!' }],
    gameId: [{ required: true, message: '请输入游戏ID!' }],
    subGameId: [{ required: true, message: '请输入子游戏ID!' }],
    accountIds: [{ required: true, message: '请选择投放账号!' }],
    anchorPlanId: [{ required: true, message: '请选择主播ID!' }],
    pkgId: [{ required: true, message: '请选择游戏包!' }],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  // 表单禁用
  const disabled = computed(() => {
    if (props.formBpm === true) {
      if (props.formData.disabled === false) {
        return false;
      } else {
        return true;
      }
    }
    return props.formDisabled;
  });

  getAnchorDealInfo();
  /**
   * 主播广告信息
   */
  function getAnchorDealInfo() {
    info().then((res: any) => {
      anchorDealList.value = res;
    });
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
   * 游戏/子游戏变化
   */
  function changeAccountOptions(gameId, subGameId) {
    let game = {
      gameId: gameId,
      subGameId: subGameId,
    };
    accountQueryParam.value.subGameIds = JSON.stringify(game);
    list(accountQueryParam.value).then((res: any) => {
      accountList.value = res.records;
    });
  }

  /**
   * 选择游戏包
   */
  function getPkgList(gameId, subGameId) {
    pkgQueryParam.value.gameId = gameId;
    pkgQueryParam.value.subGameId = subGameId;
    pkgQueryParam.value.channelId = 5;
    pkgInfoList(pkgQueryParam.value).then((res: any) => {
      pkgList.value = res;
    });
  }

  /**
   * 选择主播
   */
  function selectAnchor(data, option) {
    for (let i in anchorDealList.value) {
      if (anchorDealList.value[i].anchorId === option.key) {
        formData.dealArgs = anchorDealList.value[i].trackUrl;
        formData.downloadUrl = anchorDealList.value[i].downloadUrl;
        formData.campaignName = anchorDealList.value[i].dealId + '-' + formData.campaignName;
        break;
      }
    }
  }

  /**
   * 游戏/子游戏变化
   */
  function selectPkg(data, option) {
    formData.campaignName = option.key;
  }

  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      selectForm.value.edit({ gameId: record.gameId, subGameId: record.subGameId });
      defaultAccount = record.accountId;
      defaultpkg = record.pkgId;
      formData.accountIds = defaultAccount;
      changeAccountOptions(record.gameId, record.subGameId);
      //赋值
      Object.assign(formData, record);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    // 更新时判断
    if (isUpdate.value) {
      if (model.dealArgs == undefined || model.dealArgs == '') {
        createMessage.warn('监测链接不能为空');
        return;
      }
      if (model.downloadUrl == undefined || model.downloadUrl == '') {
        createMessage.warn('下载链接不能为空');
        return;
      }
    }
    // 预算判断
    let budget = model.budget;
    let budgetMode = model.budgetMode;
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
    confirmLoading.value = true;
    //循环数据
    for (let data in model) {
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }
    }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          responseData.value = res.result;
          if (isUpdate.value) {
            createMessage.success(res.message);
          }
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
        if (!isUpdate.value) {
          messageRegisterModal.value.init(responseData.value);
        }
        selectForm.value.reload();
      });
  }

  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .file-select {
    width: 100%;
    display: flex;
    .ant-select {
      width: 33.3%;
    }
    .ant-select:not(:first-child) {
      margin-left: 10px;
    }
  }
  .div-style {
    margin-top: 16px;
  }
  .radio-button {
    width: 208px;
    height: 70px;
    margin-left: 5px;
  }
</style>
