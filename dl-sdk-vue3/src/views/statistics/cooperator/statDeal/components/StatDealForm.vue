<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="广告id" v-bind="validateInfos.dealId">
            <a-input v-model:value="formData.dealId" placeholder="请输入广告id" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="开始时间" v-bind="validateInfos.startTime">
            <a-input v-model:value="formData.startTime" placeholder="请输入开始时间" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="结束时间" v-bind="validateInfos.endTime">
            <a-input v-model:value="formData.endTime" placeholder="请输入结束时间" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="归类方式" v-bind="validateInfos.type">
            <a-input v-model:value="formData.type" placeholder="请输入归类方式" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="日期" v-bind="validateInfos.dateTime">
            <a-input v-model:value="formData.dateTime" placeholder="请输入日期" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告名称" v-bind="validateInfos.dealName">
            <a-input v-model:value="formData.dealName" placeholder="请输入广告名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏名称" v-bind="validateInfos.gameName">
            <a-input v-model:value="formData.gameName" placeholder="请输入游戏名称" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="激活数" v-bind="validateInfos.countActive">
            <a-input v-model:value="formData.countActive" placeholder="请输入激活数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册数" v-bind="validateInfos.regCount">
            <a-input v-model:value="formData.regCount" placeholder="请输入注册数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="有效注册数" v-bind="validateInfos.validReg">
            <a-input v-model:value="formData.validReg" placeholder="请输入有效注册数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="新增付费人数" v-bind="validateInfos.firstPayUser">
            <a-input v-model:value="formData.firstPayUser" placeholder="请输入新增付费人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="新增付费金额" v-bind="validateInfos.firstPayMoney">
            <a-input v-model:value="formData.firstPayMoney" placeholder="请输入新增付费金额" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="活跃人数" v-bind="validateInfos.countDau">
            <a-input v-model:value="formData.countDau" placeholder="请输入活跃人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="活跃付费人数" v-bind="validateInfos.alivePayUser">
            <a-input v-model:value="formData.alivePayUser" placeholder="请输入活跃付费人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="付费总额" v-bind="validateInfos.totalMoney">
            <a-input v-model:value="formData.totalMoney" placeholder="请输入付费总额" :disabled="disabled"></a-input>
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
  import { saveOrUpdate } from '../StatDeal.api';
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
    dealId: '',   
    startTime: '',   
    endTime: '',   
    type: '',   
    dateTime: '',   
    dealName: '',   
    gameName: '',   
    countActive: '',   
    regCount: '',   
    validReg: '',   
    firstPayUser: '',   
    firstPayMoney: '',   
    countDau: '',   
    alivePayUser: '',   
    totalMoney: '',   
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
