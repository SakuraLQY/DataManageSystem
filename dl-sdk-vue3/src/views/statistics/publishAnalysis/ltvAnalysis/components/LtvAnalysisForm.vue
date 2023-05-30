<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.gameId">
            <a-input v-model:value="formData.gameId" placeholder="请输入游戏" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="开始时间" v-bind="validateInfos.startTime">
		        <a-date-picker placeholder="请选择开始时间" v-model:value="formData.startTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="结束时间" v-bind="validateInfos.endTime">
		        <a-date-picker placeholder="请选择结束时间" v-model:value="formData.endTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="归类方式" v-bind="validateInfos.type">
            <a-input v-model:value="formData.type" placeholder="请输入归类方式" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="ID" v-bind="validateInfos.typeId">
            <a-input v-model:value="formData.typeId" placeholder="请输入ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="名称" v-bind="validateInfos.name">
            <a-input v-model:value="formData.name" placeholder="请输入名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV1" v-bind="validateInfos.ltv1">
            <a-input v-model:value="formData.ltv1" placeholder="请输入LTV1" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV2" v-bind="validateInfos.ltv2">
            <a-input v-model:value="formData.ltv2" placeholder="请输入LTV2" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV3" v-bind="validateInfos.ltv3">
            <a-input v-model:value="formData.ltv3" placeholder="请输入LTV3" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV4" v-bind="validateInfos.ltv4">
            <a-input v-model:value="formData.ltv4" placeholder="请输入LTV4" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV5" v-bind="validateInfos.ltv5">
            <a-input v-model:value="formData.ltv5" placeholder="请输入LTV5" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV6" v-bind="validateInfos.ltv6">
            <a-input v-model:value="formData.ltv6" placeholder="请输入LTV6" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV7" v-bind="validateInfos.ltv7">
            <a-input v-model:value="formData.ltv7" placeholder="请输入LTV7" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV15" v-bind="validateInfos.ltv15">
            <a-input v-model:value="formData.ltv15" placeholder="请输入LTV15" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV30" v-bind="validateInfos.ltv30">
            <a-input v-model:value="formData.ltv30" placeholder="请输入LTV30" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV45" v-bind="validateInfos.ltv45">
            <a-input v-model:value="formData.ltv45" placeholder="请输入LTV45" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV60" v-bind="validateInfos.ltv60">
            <a-input v-model:value="formData.ltv60" placeholder="请输入LTV60" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV90" v-bind="validateInfos.ltv90">
            <a-input v-model:value="formData.ltv90" placeholder="请输入LTV90" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV120" v-bind="validateInfos.ltv120">
            <a-input v-model:value="formData.ltv120" placeholder="请输入LTV120" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="LTV150" v-bind="validateInfos.ltv150">
            <a-input v-model:value="formData.ltv150" placeholder="请输入LTV150" :disabled="disabled"></a-input>
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
  import { saveOrUpdate } from '../LtvAnalysis.api';
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
    gameId: '',   
    startTime: '',   
    endTime: '',   
    type: '',   
    typeId: '',   
    name: '',   
    ltv1: '',   
    ltv2: '',   
    ltv3: '',   
    ltv4: '',   
    ltv5: '',   
    ltv6: '',   
    ltv7: '',   
    ltv15: '',   
    ltv30: '',   
    ltv45: '',   
    ltv60: '',   
    ltv90: '',   
    ltv120: '',   
    ltv150: '',   
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
