<template>
  <a-spin :spinning="confirmLoading">
    <a-row>
      <a-col :span="12">
        <PageWrapper title="广告计划参数">
          <a-card :bodyStyle="{ padding: '0' }">
            <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-row>
                <a-col :span="24" v-if="formData.id == ''">
                  <a-form-item label="生成广告计划条数">
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
                  <a-form-item label="渠道子账号ID" v-bind="validateInfos.channelSubAccountId">
                    <j-search-select
                      v-model:value="formData.channelSubAccountId"
                      dict="open_gateway.op_channel_sub_account where channel_id = 9,sub_account_name,id"
                      :disabled="disabled"
                      placeholder="请选择渠道子账号ID"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="广告计划名称" v-bind="validateInfos.dealName">
                    <a-input v-model:value="formData.dealName" placeholder="默认填充游戏名" showCount :maxlength="100" :disabled="disabled"></a-input>
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="监测链接">
                    <a-textarea
                      v-model:value="formData.dealArgs"
                      placeholder="不填将自动生成"
                      showCount
                      :maxlength="512"
                      auto-size
                      :disabled="disabled"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="下载链接">
                    <a-textarea
                      v-model:value="formData.pkgUrl"
                      placeholder="不填将自动生成"
                      showCount
                      :maxlength="128"
                      auto-size
                      :disabled="disabled"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="广告组描述">
                    <a-textarea
                      v-model:value="formData.dealDesc"
                      placeholder="请输入广告描述"
                      showCount
                      :maxlength="128"
                      auto-size
                      :disabled="disabled"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-form>
          </a-card>
        </PageWrapper>
      </a-col>
      <a-col :span="12">
        <PageWrapper title="快手计划参数">
          <a-card>
            <!-- 推广目的 -->
            <CollapseContainer title="推广目的">
              <div class="div-style">
                <a-radio-group v-model:value="formData.type" :disabled="disabled">
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
            <CollapseContainer title="投放方式">
              <div class="div-style">
                <a-radio-group v-model:value="formData.bidType" :disabled="disabled">
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
                <a-radio-group v-model:value="formData.budgetMode" button-style="solid" :disabled="disabled">
                  <a-radio-button :value="CampaignEnum.BUDGET_MODE.INFINITE" :disabled="formData.bidType == CampaignEnum.BID_TYPE.MAX ? true : false"
                    >不限</a-radio-button
                  >
                  <a-radio-button :value="CampaignEnum.BUDGET_MODE.DAY">统一预算</a-radio-button>
                  <a-radio-button :value="CampaignEnum.BUDGET_MODE.WEEK">分日预算</a-radio-button>
                </a-radio-group>
              </div>
              <div class="div-style">
                <!-- 统一预算 -->
                <div v-if="formData.budgetMode == CampaignEnum.BUDGET_MODE.DAY">
                  <a-input-number v-model:value="formData.budget" :min="0" :max="100000000" :precision="0" style="width: 80%" :disabled="disabled" />
                  <div style="color: crimson"> 不小于 500 元，不超过 100000000 元，0表示预算不限，仅支持输入整数 </div>
                </div>
                <!-- 分日预算 -->
                <div v-if="formData.budgetMode == CampaignEnum.BUDGET_MODE.WEEK">
                  <WeekBudgetTable
                    ref="weekRegisterModal"
                    :data="formData.budgetWeek"
                    @syncWeekData="syncWeekData"
                    :canEdit="!disabled"
                  ></WeekBudgetTable>
                </div>
              </div>
            </CollapseContainer>
          </a-card>
        </PageWrapper>
      </a-col>
    </a-row>
    <MessageModal ref="messageRegisterModal"></MessageModal>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed } from 'vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JCheckbox from '/@/components/Form/src/jeecg/components/JCheckbox.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate, pkgInfoList } from '../dealPlan.api';
  import { Form } from 'ant-design-vue';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { list } from '/@/views/advertManager/kuaishouManager/opKsPutAccount/OpKsPutAccount.api';
  import MessageModal from '/@/views/common/messageModal.vue';
  import { PageWrapper } from '/@/components/Page';
  import * as CampaignEnum from '../CampaignEnum';
  import { CollapseContainer } from '/@/components/Container/index';
  import WeekBudgetTable from './WeekBudgetTable.vue';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => {} },
    formBpm: { type: Boolean, default: true },
  });
  const formRef = ref();
  const messageRegisterModal = ref();
  const weekRegisterModal = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    dealName: '',
    gameId: undefined,
    subGameId: undefined,
    accountIds: 46,
    pkgId: undefined,
    channelSubAccountId: undefined,
    dealArgs: '',
    dealDesc: '',
    pkgUrl: '',
    dealNumbers: 1,
    budget: 0,
    budgetWeek: '0,0,0,0,0,0,0',
    type: CampaignEnum.TYPE.APP,
    bidType: CampaignEnum.BID_TYPE.DEFAULT,
    budgetMode: CampaignEnum.BUDGET_MODE.INFINITE,
  });

  const selectForm = ref();
  const accountList = ref();
  let weekData = [0, 0, 0, 0, 0, 0, 0];

  let syncWeekData = (e: any) => {
    weekData = e;
  };

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
  let defaultpkg = undefined;
  let defaultAccount = undefined;
  let priceTip = ref<string>('');
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);

  //表单验证
  const validatorRules = {
    dealName: [{ required: true, message: '请输入广告组名称!' }],
    gameId: [{ required: true, message: '请输入游戏ID!' }],
    subGameId: [{ required: true, message: '请输入子游戏ID!' }],
    accountIds: [{ required: true, message: '请选择投放账号!' }],
    pkgId: [{ required: true, message: '请选择游戏包!' }],
    channelSubAccountId: [{ required: true, message: '请选择渠道子账号!' }],
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
    pkgQueryParam.value.channelId = 9;
    pkgInfoList(pkgQueryParam.value).then((res: any) => {
      pkgList.value = res;
    });
  }

  /**
   * 游戏/子游戏变化
   */
  function selectPkg(data, option) {
    formData.dealName = option.key;
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
      if (record.budgetMode == CampaignEnum.BUDGET_MODE.WEEK) {
        formData.budgetWeek = record.budget;
        formData.budget = 0;
      } else {
        formData.budgetWeek = null;
      }
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
    // 预算判断
    if (model.budgetMode == CampaignEnum.BUDGET_MODE.WEEK) {
      for (let i in weekData) {
        if (weekData[i] != 0) {
          if (weekData[i] < 500 || weekData[i] > 100000000) {
            createMessage.warning('分日预算有误');
            return;
          }
        } else {
          if (model.bidType == CampaignEnum.BID_TYPE.MAX) {
            createMessage.warning('最大转化必须设置预算');
            return;
          }
        }
      }
    } else if (model.budgetMode == CampaignEnum.BUDGET_MODE.DAY) {
      if (model.budget != 0) {
        if (model.budget < 500 || model.budget > 100000000) {
          createMessage.warning('日预算有误');
          return;
        }
      }
    } else {
      model.budget = 0;
    }
    model.budgetWeek = weekData;
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
    width: 270px;
    height: 70px;
    margin-left: 10px;
  }
</style>
