<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <GameThirdSingleOptionModal ref="selectGameModal" @handler="getGameVal"></GameThirdSingleOptionModal>
        <ChannelThirdOptionModal ref="selectChannelModal" @handler="getChannelVal"></ChannelThirdOptionModal>
        <a-col :span="24">
          <a-form-item label="广告" v-bind="validateInfos.dealId">
            <j-search-select placeholder="请选择广告" v-model:value="formData.dealId"  dict="open_gateway.op_deal,deal_name,id" allowClear />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道广告id" v-bind="validateInfos.channelDealId">
	          <a-input-number v-model:value="formData.channelDealId" placeholder="请输入渠道广告id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="成本日期 " v-bind="validateInfos.costDay">
		        <a-date-picker placeholder="请选择成本日期 " v-model:value="formData.costDay" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="成本金额" v-bind="validateInfos.costMoney">
	          <a-input-number v-model:value="formData.costMoney" placeholder="请输入成本金额" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="下载" v-bind="validateInfos.download">
	          <a-input-number v-model:value="formData.download" placeholder="请输入下载" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="点击" v-bind="validateInfos.click">
	          <a-input-number v-model:value="formData.click" placeholder="请输入点击" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="展示" v-bind="validateInfos.exhibition">
	          <a-input-number v-model:value="formData.exhibition" placeholder="请输入展示" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="投放账号" v-bind="validateInfos.accountId">
            <j-search-select placeholder="请选择投放账号" v-model:value="formData.accountId" dict="open_gateway.op_jrtt_put_account opa left join open_gateway.op_put_account ojpa on opa.account_id = ojpa.id,ojpa.nick_name,ojpa.id" allowClear/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="成本平台" v-bind="validateInfos.platform">
            <j-dict-select-tag v-model:value="formData.platform" dictCode="cost_platform_item" placeholder="请选择成本平台" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="备注" v-bind="validateInfos.costDesc">
            <a-input v-model:value="formData.costDesc" placeholder="请输入备注" :disabled="disabled"></a-input>
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
  import { saveOrUpdate } from '../OpCost.api';
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
  const selectGameModal= ref();
  const selectChannelModal= ref();
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
    channelTypeId: undefined,
    channelId: undefined,
    channelSubAccountId: undefined,
    dealId: undefined,
    channelDealId: undefined,
    costDay: '',   
    costMoney: undefined,
    download: undefined,
    click: undefined,
    exhibition: undefined,
    accountId: undefined,
    platform: undefined,
    costDesc: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请输入游戏id!'},],
    subGameId: [{ required: true, message: '请输入子游戏id!'},],
    pkgId: [{ required: true, message: '请输入渠道游戏包id!'},],
    channelTypeId: [{ required: true, message: '请输入渠道类型id!'},],
    channelId: [{ required: true, message: '请输入渠道id!'},],
    channelSubAccountId: [{ required: true, message: '请输入渠道子账号id!'},],
    dealId: [{ required: true, message: '请输入广告id!'},],
    channelDealId: [{ required: true, message: '请输入渠道广告id!'},],
    costDay: [{ required: true, message: '请输入成本日期 !'},],
    costMoney: [{ required: true, message: '请输入成本金额!'},],
    download: [{ required: true, message: '请输入下载!'},],
    click: [{ required: true, message: '请输入点击!'},],
    exhibition: [{ required: true, message: '请输入展示!'},],
    accountId: [{ required: true, message: '请输入投放账号id!'},],
    platform: [{ required: true, message: '请输入成本平台，0表示全部平台，1为安卓，2为ios!'},],
    costDesc: [{ required: true, message: '请输入成本-描述!'},],
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
      selectGameModal.value.edit({"gameId":record.gameId,"subGameId":record.subGameId,"pkgId":record.pkgId});
      selectChannelModal.value.edit({"channelTypeId":record.channelTypeId,"channelId":record.channelId,"channelSubAccountId":record.channelSubAccountId});
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
    selectGameModal.value.check();
    selectChannelModal.value.check();
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
