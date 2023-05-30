<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏id" v-bind="validateInfos.gameId">
	          <a-input-number v-model:value="formData.gameId" placeholder="请输入游戏id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="子游戏id" v-bind="validateInfos.subGameId">
	          <a-input-number v-model:value="formData.subGameId" placeholder="请输入子游戏id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道游戏包id" v-bind="validateInfos.pkgId">
	          <a-input-number v-model:value="formData.pkgId" placeholder="请输入渠道游戏包id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道id" v-bind="validateInfos.channelId">
	          <a-input-number v-model:value="formData.channelId" placeholder="请输入渠道id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道子账号id" v-bind="validateInfos.channelSubAccountId">
	          <a-input-number v-model:value="formData.channelSubAccountId" placeholder="请输入渠道子账号id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回调渠道" v-bind="validateInfos.callbackChannel">
            <a-input v-model:value="formData.callbackChannel" placeholder="请输入回调渠道" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告-id" v-bind="validateInfos.dealId">
	          <a-input-number v-model:value="formData.dealId" placeholder="请输入广告-id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回调事件类型" v-bind="validateInfos.eventType">
	          <a-input-number v-model:value="formData.eventType" placeholder="请输入回调事件类型" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回调数据" v-bind="validateInfos.callbackData">
	          <a-textarea v-model:value="formData.callbackData" rows="4" placeholder="请输入回调数据" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回调次数" v-bind="validateInfos.callbackNum">
	          <a-input-number v-model:value="formData.callbackNum" placeholder="请输入回调次数" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回调状态：1为待回调，2为回调成功，3为回调失败" v-bind="validateInfos.callbackState">
	          <a-input-number v-model:value="formData.callbackState" placeholder="请输入回调状态：1为待回调，2为回调成功，3为回调失败" style="width: 100%" :disabled="disabled"/>
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
  import { saveOrUpdate } from '../CtCallback.api';
  import { Form } from 'ant-design-vue';
  
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
    channelSubAccountId: undefined,
    callbackChannel: '',   
    dealId: undefined,
    eventType: undefined,
    callbackData: '',   
    callbackNum: undefined,
    callbackState: undefined,
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
    channelId: [{ required: true, message: '请输入渠道id!'},],
    channelSubAccountId: [{ required: true, message: '请输入渠道子账号id!'},],
    callbackChannel: [{ required: true, message: '请输入回调渠道!'},],
    dealId: [{ required: true, message: '请输入广告-id!'},],
    eventType: [{ required: true, message: '请输入回调事件类型!'},],
    callbackData: [{ required: true, message: '请输入回调数据!'},],
    callbackNum: [{ required: true, message: '请输入回调次数!'},],
    callbackState: [{ required: true, message: '请输入回调状态：1为待回调，2为回调成功，3为回调失败!'},],
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
