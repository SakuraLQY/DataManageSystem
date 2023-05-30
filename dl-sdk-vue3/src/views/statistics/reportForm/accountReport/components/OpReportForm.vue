<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <GameThirdSingleOptionModal ref="selectGameModal" @handler="getGameVal"></GameThirdSingleOptionModal>
        <ChannelThirdOptionModal ref="selectChannelModal" @handler="getChannelVal"></ChannelThirdOptionModal>
        <a-col :span="24">
          <a-form-item label="渠道游戏包" v-bind="validateInfos.pkgId">
	          <a-input-number v-model:value="formData.pkgId" placeholder="请输入渠道游戏包" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道名称" v-bind="validateInfos.channelId">
	          <a-input-number v-model:value="formData.channelId" placeholder="请输入渠道名称" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道类型" v-bind="validateInfos.channelTypeId">
	          <a-input-number v-model:value="formData.channelTypeId" placeholder="请输入渠道类型" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="二级渠道" v-bind="validateInfos.subChannelAccountId">
	          <a-input-number v-model:value="formData.subChannelAccountId" placeholder="请输入二级渠道" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="投放账号" v-bind="validateInfos.accountId">
            <j-search-select placeholder="请选择投放账号" v-model:value="formData.accountId" dict="open_gateway.op_jrtt_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id" allowClear/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="账号人员" v-bind="validateInfos.createUser">
            <a-input v-model:value="formData.createUser" placeholder="请输入账号人员" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="账号昵称" v-bind="validateInfos.nickName">
            <a-input v-model:value="formData.nickName" placeholder="请输入账号昵称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="账号" v-bind="validateInfos.account">
            <a-input v-model:value="formData.account" placeholder="请输入账号" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.gameName">
            <a-input v-model:value="formData.gameName" placeholder="请输入游戏" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道" v-bind="validateInfos.channelName">
            <a-input v-model:value="formData.channelName" placeholder="请输入渠道" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="充值金额" v-bind="validateInfos.payMoney">
	          <a-input-number v-model:value="formData.payMoney" placeholder="请输入充值金额" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="转出金额" v-bind="validateInfos.outMoney">
	          <a-input-number v-model:value="formData.outMoney" placeholder="请输入转出金额" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="投放消耗" v-bind="validateInfos.outCostMoney">
	          <a-input-number v-model:value="formData.outCostMoney" placeholder="请输入投放消耗" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="账号余额" v-bind="validateInfos.surplusAmount">
	          <a-input-number v-model:value="formData.surplusAmount" placeholder="请输入账号余额" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="曝光" v-bind="validateInfos.exhibition">
	          <a-input-number v-model:value="formData.exhibition" placeholder="请输入曝光" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="下载" v-bind="validateInfos.download">
	          <a-input-number v-model:value="formData.download" placeholder="请输入下载" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="负责人" v-bind="validateInfos.principalUser">
            <a-input v-model:value="formData.principalUser" placeholder="请输入负责人" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="创建时间" v-bind="validateInfos.createTime">
		        <a-date-picker placeholder="请选择创建时间" v-model:value="formData.createTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="更新时间" v-bind="validateInfos.updateTime">
		        <a-date-picker placeholder="请选择更新时间" v-model:value="formData.updateTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="账单" v-bind="validateInfos.bill">
            <a-input v-model:value="formData.bill" placeholder="请输入账单" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpReport.api';
  import { Form } from 'ant-design-vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/jDictSelectTag.vue';
  import GameThirdSingleOptionModal from '/@/views/common/gameThirdSingleOptionModal.vue';
  import ChannelThirdOptionModal from '/@/views/common/channelThirdOptionModal.vue';

  let getGameVal = (e: any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
    formData.pkgId = e.pkgId;
  };
  let getChannelVal = (e: any) => {
    formData.channelTypeId = e.channelTypeId;
    formData.channelId = e.channelId;
    formData.channelSubAccountId = e.channelSubAccountId;
  };
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    gameId: undefined,
    subGameId: undefined,
    pkgId: undefined,
    channelId: undefined,
    channelTypeId: undefined,
    subChannelAccountId: undefined,
    putAccount: undefined,
    createUser: '',   
    nickName: '',   
    account: '',   
    gameName: '',   
    channelName: '',   
    payMoney: undefined,
    outMoney: undefined,
    outCostMoney: undefined,
    surplusAmount: undefined,
    exhibition: undefined,
    download: undefined,
    principalUser: '',   
    createTime: '',   
    updateTime: '',   
    bill: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
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
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
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
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
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
</style>
