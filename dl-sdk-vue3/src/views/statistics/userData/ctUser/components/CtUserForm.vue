<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.gameId">
	          <a-input-number v-model:value="formData.gameId" placeholder="请输入游戏" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="子游戏" v-bind="validateInfos.subGameId">
	          <a-input-number v-model:value="formData.subGameId" placeholder="请输入子游戏" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道游戏包" v-bind="validateInfos.pkgId">
	          <a-input-number v-model:value="formData.pkgId" placeholder="请输入渠道游戏包" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道ID" v-bind="validateInfos.channelId">
	          <a-input-number v-model:value="formData.channelId" placeholder="请输入渠道ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告ID" v-bind="validateInfos.dealId">
	          <a-input-number v-model:value="formData.dealId" placeholder="请输入广告ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="设备ID" v-bind="validateInfos.uniqueId">
            <a-input v-model:value="formData.uniqueId" placeholder="请输入设备ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="用户ID" v-bind="validateInfos.userId">
	          <a-input-number v-model:value="formData.userId" placeholder="请输入用户ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="首服ID" v-bind="validateInfos.serverInit">
	          <a-input-number v-model:value="formData.serverInit" placeholder="请输入首服ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="新服ID" v-bind="validateInfos.serverLast">
	          <a-input-number v-model:value="formData.serverLast" placeholder="请输入新服ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="在线时长" v-bind="validateInfos.onlineTime">
	          <a-input-number v-model:value="formData.onlineTime" placeholder="请输入在线时长" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册时间" v-bind="validateInfos.registerTime">
		        <a-date-picker placeholder="请选择注册时间" v-model:value="formData.registerTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="最近登录时间" v-bind="validateInfos.loginTime">
		        <a-date-picker placeholder="请选择最近登录时间" v-model:value="formData.loginTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="最新登录IP" v-bind="validateInfos.clientIp">
            <a-input v-model:value="formData.clientIp" placeholder="请输入最新登录IP" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道类型id" v-bind="validateInfos.channelTypeId">
	          <a-input-number v-model:value="formData.channelTypeId" placeholder="请输入渠道类型id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道子账号id" v-bind="validateInfos.channelSubAccountId">
	          <a-input-number v-model:value="formData.channelSubAccountId" placeholder="请输入渠道子账号id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="当天在线时长" v-bind="validateInfos.dayOnlineTime">
	          <a-input-number v-model:value="formData.dayOnlineTime" placeholder="请输入当天在线时长" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登陆标记" v-bind="validateInfos.loginMask">
	          <a-input-number v-model:value="formData.loginMask" placeholder="请输入登陆标记" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="充值时间" v-bind="validateInfos.payTime">
		        <a-date-picker placeholder="请选择充值时间" v-model:value="formData.payTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="上报时间" v-bind="validateInfos.aliveTime">
		        <a-date-picker placeholder="请选择上报时间" v-model:value="formData.aliveTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="首次支付时间" v-bind="validateInfos.timeFirstPay">
		        <a-date-picker placeholder="请选择首次支付时间" v-model:value="formData.timeFirstPay" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="用户支付留存" v-bind="validateInfos.payUserLoginMask">
	          <a-input-number v-model:value="formData.payUserLoginMask" placeholder="请输入用户支付留存" style="width: 100%" :disabled="disabled"/>
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
  import { saveOrUpdate } from '../CtUser.api';
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
    dealId: undefined,
    uniqueId: '',
    userId: undefined,
    serverInit: undefined,
    serverLast: undefined,
    onlineTime: undefined,
    registerTime: '',
    loginTime: '',
    clientIp: '',
    channelTypeId: undefined,
    channelSubAccountId: undefined,
    dayOnlineTime: undefined,
    loginMask: undefined,
    payTime: '',
    aliveTime: '',
    timeFirstPay: '',
    payUserLoginMask: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请输入游戏!'},],
    subGameId: [{ required: true, message: '请输入子游戏!'},],
    pkgId: [{ required: true, message: '请输入渠道游戏包!'},],
    channelId: [{ required: true, message: '请输入渠道ID!'},],
    dealId: [{ required: true, message: '请输入广告ID!'},],
    uniqueId: [{ required: true, message: '请输入设备ID!'},],
    userId: [{ required: true, message: '请输入用户ID!'},],
    serverInit: [{ required: true, message: '请输入首服ID!'},],
    serverLast: [{ required: true, message: '请输入新服ID!'},],
    onlineTime: [{ required: true, message: '请输入在线时长!'},],
    registerTime: [{ required: true, message: '请输入注册时间!'},],
    loginTime: [{ required: true, message: '请输入最近登录时间!'},],
    clientIp: [{ required: true, message: '请输入最新登录IP!'},],
    channelTypeId: [{ required: true, message: '请输入渠道类型id!'},],
    channelSubAccountId: [{ required: true, message: '请输入渠道子账号id!'},],
    dayOnlineTime: [{ required: true, message: '请输入当天在线时长!'},],
    loginMask: [{ required: true, message: '请输入登陆标记!'},],
    payTime: [{ required: true, message: '请输入充值时间!'},],
    aliveTime: [{ required: true, message: '请输入上报时间!'},],
    timeFirstPay: [{ required: true, message: '请输入首次支付时间!'},],
    payUserLoginMask: [{ required: true, message: '请输入用户支付留存!'},],
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
