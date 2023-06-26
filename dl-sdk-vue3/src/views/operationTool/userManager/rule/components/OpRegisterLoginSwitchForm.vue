<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="30">
          <p style="backgroundColor:cadetblue;margin-left:13%;height:30px;width:80%" ><i>规则类型</i></p>
        </a-col>
        <a-col :span="24">
          <a-form-item label="类型" v-bind="validateInfos.ruleType">
	          <j-search-select v-model:value="formData.ruleType" dict="rule_type" placeholder="请选择类型" :disabled="isEdit"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="类型参数" v-bind="validateInfos.ruleId" v-if="formData.ruleType === undefined || formData.ruleType === null || formData.ruleType === ''">
            <j-search-select v-model:value="formData.ruleId" placeholder="请先选择类型" :disabled="isEdit" />
          </a-form-item>
          <a-form-item label="游戏" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '1'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_game,game_name,id"  placeholder="请选择游戏" :disabled="isEdit" />
          </a-form-item>
          <a-form-item label="子游戏" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '2'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_sub_game where status = 0,game_name,id"  placeholder="请选择子游戏" :disabled="isEdit" />
          </a-form-item>
          <a-form-item label="广告" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '3'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_deal,deal_name,id"  placeholder="请选择广告" :disabled="isEdit" />
          </a-form-item>
          <a-form-item label="渠道游戏包" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '4'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_pkg where status = 0,pkg_name,id"  placeholder="请选择渠道游戏包" :disabled="isEdit" />
          </a-form-item>
        </a-col>
        <a-col :span="30">
          <p style="backgroundColor:cadetblue;margin-left:13%;height:30px;width:80%" ><i>注册控制</i></p>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册规则类型"  v-bind="validateInfos.registerLimitSwitch">
	          <j-search-select v-model:value="formData.registerLimitSwitch" dict="register_rule_type" placeholder="请选择注册规则类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册关闭类型" v-bind="validateInfos.registerCloseType" >
	          <j-search-select v-model:value="formData.registerCloseType" dict="close_type" placeholder="请选择注册关闭类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册限制IP" v-bind="validateInfos.registerLimitIp" v-if="registerIfShow">
	          <a-textarea v-model:value="formData.registerLimitIp" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册限制设备" v-bind="validateInfos.registerLimitDevice" v-if="registerIfShow">
	          <a-textarea v-model:value="formData.registerLimitDevice" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="30">
          <p style="backgroundColor:cadetblue;margin-left:13%;height:30px;width:80%" ><i>登录控制</i></p>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录关闭类型" v-bind="validateInfos.loginCloseType">
	          <j-search-select v-model:value="formData.loginCloseType" dict="close_type" placeholder="请选择登录关闭类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录限制IP" v-bind="validateInfos.loginLimitIp" v-if="loginIfShow">
	          <a-textarea v-model:value="formData.loginLimitIp" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录限制用户ID" v-bind="validateInfos.loginLimitUserId" v-if="loginIfShow">
	          <a-textarea v-model:value="formData.loginLimitUserId" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="登录限制设备" v-bind="validateInfos.loginLimitDevice" v-if="loginIfShow">
	          <a-textarea v-model:value="formData.loginLimitDevice" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="30">
          <p style="backgroundColor:cadetblue;margin-left:13%;height:30px;width:80%" ><i>跨登录控制</i></p>
        </a-col>
        <a-col :span="24">
          <a-form-item label="限制跨登录开关" v-bind="validateInfos.limitCrossLoginSwitch">
	          <j-search-select v-model:value="formData.limitCrossLoginSwitch" dict="is_open" placeholder="请选择限制跨登录开关" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="备注" v-bind="validateInfos.descs">
            <a-textarea v-model:value="formData.descs" showCount maxLength="255" rows="4" placeholder="长度限制至255位" :disabled="disabled"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { watch, ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpRegisterLoginSwitch.api';
  import { Form } from 'ant-design-vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  let loginIfShow = ref<boolean>(true)
  let registerIfShow = ref<boolean>(true)
  // let selectType = ref<number>(1)
  const formRef = ref();
  const useForm = Form.useForm;
  let isEdit = ref(false);
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    ruleType: '1',
    ruleId: undefined,
    registerCloseType: '2',
    registerLimitSwitch: '1',
    loginCloseType: '2',
    loginLimitIp: '',   
    loginLimitUserId: '',   
    loginLimitDevice: '', 
    registerLimitIp: '',   
    registerLimitDevice: '',   
    limitCrossLoginSwitch: '1',
    descs: '',   
  });
  watch(
      () => formData.ruleType,
      val => {
        if(formData.id === '') {
          formData.ruleId = undefined; 
        }
      })
  watch(formData, (newName, oldName) => {
    // if(formData.ruleType === '1') {
    //   selectType.value = 1
    // }else if(formData.ruleType === '2'){
    //   selectType.value = 2
    // }else{
    //   selectType.value = 3
    // }
    if(formData.registerCloseType !== '2') {
      registerIfShow.value = false
    }else{
      registerIfShow.value = true
    }
    if(formData.loginCloseType !== '2') {
      loginIfShow.value = false
    }else{
      loginIfShow.value = true
    }
  },{deep:false});

  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    ruleType: [{ required: true, message: '请输入类型!'},],
    ruleId: [{ required: true, message: '请选择!'},],
    registerCloseType: [{ required: true, message: '请输入注册关闭类型!'},],
    registerLimitSwitch: [{ required: true, message: '请输入注册规则类型!'},],
    loginCloseType: [{ required: true, message: '请输入登录关闭类型!'},],
    limitCrossLoginSwitch: [{ required: true, message: '请输入限制跨登录开关!'},],
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
    edit(formData);
  }

  /**
   * 编辑
   */
  function edit(record) {
    // record.ruleType = record.ruleType + '',
    // record.registerCloseType = record.registerCloseType + '',
    // record.registerLimitSwitch = record.registerLimitSwitch + '',
    // record.loginCloseType = record.loginCloseType + '',
    // record.limitCrossLoginSwitch = record.limitCrossLoginSwitch + '',
    nextTick(() => {
      resetFields();
      //赋值
      Object.assign(formData, record);
      if(formData.id !== '') {
        isEdit.value = true
      }else {
        isEdit.value = false
      }
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
    loginIfShow,
    registerIfShow,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
    // color: cadetblue;
  }
</style>
