<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="用户名" v-bind="validateInfos.userName">
            <a-input v-model:value="formData.userName" placeholder="请输入用户名" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- <a-col :span="24">
          <a-form-item label="用户密码" v-bind="validateInfos.userPassword">
            <a-input v-model:value="formData.userPassword" placeholder="请输入用户密码" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col> -->
        <!-- <a-col :span="24">
          <a-form-item label="用户类型" v-bind="validateInfos.userType">
	          <a-input-number v-model:value="formData.userType" placeholder="请输入用户类型" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col> -->
        <a-col :span="24">
          <a-form-item label="用户信息-手机" v-bind="validateInfos.userPhone">
            <a-input v-model:value="formData.userPhone" placeholder="请输入用户信息-手机" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="实名信息" v-bind="validateInfos.userTagDeal">
	          <a-input-number v-model:value="formData.realNameInfo" placeholder="请输入实名信息" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <!-- <a-col :span="24">
          <a-form-item label="用户信息-标签-广告" v-bind="validateInfos.userTagDeal">
	          <a-input-number v-model:value="formData.userTagDeal" placeholder="请输入用户信息-标签-广告" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="用户信息-标签-子游戏" v-bind="validateInfos.userTagSubGame">
	          <a-input-number v-model:value="formData.userTagSubGame" placeholder="请输入用户信息-标签-子游戏" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col> -->
        <a-col :span="24">
          <a-form-item label="注册-时间" v-bind="validateInfos.signupTime">
		        <a-date-picker placeholder="请选择注册-时间" v-model:value="formData.signupTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册-IP" v-bind="validateInfos.signupIp">
            <a-input v-model:value="formData.signupIp" placeholder="请输入注册-IP" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册-设备" v-bind="validateInfos.signupDevice">
            <a-input v-model:value="formData.signupDevice" placeholder="请输入注册-设备" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册-来源" v-bind="validateInfos.signupSource">
            <a-input v-model:value="formData.signupSource" placeholder="请输入注册-来源" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录-时间" v-bind="validateInfos.signinTime">
		        <a-date-picker placeholder="请选择登录-时间" v-model:value="formData.signinTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录-IP" v-bind="validateInfos.signinIp">
            <a-input v-model:value="formData.signinIp" placeholder="请输入登录-IP" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录-设备" v-bind="validateInfos.signinDevice">
            <a-input v-model:value="formData.signinDevice" placeholder="请输入登录-设备" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录-来源" v-bind="validateInfos.signinSource">
            <a-input v-model:value="formData.signinSource" placeholder="请输入登录-来源" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="充值-时间" v-bind="validateInfos.chargeTime">
		        <a-date-picker placeholder="请选择充值-时间" v-model:value="formData.chargeTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="平台币" v-bind="validateInfos.platformCurrency">
	          <a-input-number v-model:value="formData.platformCurrency" placeholder="请输入平台币,1:100" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="微信用户ID" v-bind="validateInfos.extendedField">
            <a-input v-model:value="formData.extendedField" placeholder="请输入微信用户ID" :disabled="disabled"></a-input>
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

  import { Form } from 'ant-design-vue';
  import { duplicateValidate } from '/@/utils/helper/validator'
  
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
    userName: '',   
    userPassword: '',   
    userType: undefined,
    userPhone: '',   
    userTagDeal: undefined,
    userTagSubGame: undefined,
    signupTime: '',   
    signupIp: '',   
    signupDevice: '',   
    signupSource: '',   
    signinTime: '',   
    signinIp: '',   
    signinDevice: '',   
    signinSource: '',   
    chargeTime: '',   
    platformCurrency: undefined,
    extendedField: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    userName: [{ required: true, message: '请输入用户名!'}, { pattern: /^.{6,18}$/, message: '请输入6到18位任意字符!'},],
    userPassword: [{ required: true, message: '请输入用户密码!'}, { pattern: /^.{6,18}$/, message: '请输入6到18位任意字符!'},],
    userType: [{ required: true, message: '请输入用户类型!'}, { validator: userTypeDuplicatevalidate }],
    userPhone: [{ required: false}, { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},],
    userTagDeal: [{ required: false}, { pattern: /^[A-Z|a-z]+$/, message: '请输入字母!'},],
    userTagSubGame: [{ required: true, message: '请输入用户信息-标签-子游戏!'}, { pattern: /^[0-9]\d{5}$/, message: '请输入正确的邮政编码!'},],
    signupTime: [{ required: true, message: '请输入注册-时间!'}, { pattern: /^([\w]+\.*)([\w]+)@[\w]+\.\w{3}(\.\w{2}|)$/, message: '请输入正确的电子邮件!'},],
    signupIp: [{ required: true, message: '请输入注册-IP!'}, { pattern: /^-?\d+$/, message: '请输入整数!'},],
    signupDevice: [{ required: true, message: '请输入注册-设备!'}, { pattern: /^(([1-9][0-9]*)|([0]\.\d{0,2}|[1-9][0-9]*\.\d{0,2}))$/, message: '请输入正确的金额!'},],
    signupSource: [{ required: true, message: '请输入注册-来源!'}, { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  // 表单禁用
  const disabled = computed(()=>{
    return true;
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
  
  }


  async function userTypeDuplicatevalidate(_r, value) {
    return duplicateValidate('op_user', 'user_type', value, formData.id || '')
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
