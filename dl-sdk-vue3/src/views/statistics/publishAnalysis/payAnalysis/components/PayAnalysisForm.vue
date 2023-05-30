<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
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
          <a-form-item label="新增付费人数" v-bind="validateInfos.firstPayUser">
            <a-input v-model:value="formData.firstPayUser" placeholder="请输入新增付费人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="老用户付费人数" v-bind="validateInfos.oldPayUser">
            <a-input v-model:value="formData.oldPayUser" placeholder="请输入老用户付费人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="总付费人数" v-bind="validateInfos.totalPayUser">
            <a-input v-model:value="formData.totalPayUser" placeholder="请输入总付费人数" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="新增付费金额" v-bind="validateInfos.firstPayMoney">
            <a-input v-model:value="formData.firstPayMoney" placeholder="请输入新增付费金额" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="老用户付费金额" v-bind="validateInfos.oldPayMoney">
            <a-input v-model:value="formData.oldPayMoney" placeholder="请输入老用户付费金额" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="总付费金额" v-bind="validateInfos.totalPayMoney">
            <a-input v-model:value="formData.totalPayMoney" placeholder="请输入总付费金额" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="累计付费金额" v-bind="validateInfos.aliveTotalMoney">
            <a-input v-model:value="formData.aliveTotalMoney" placeholder="请输入累计付费金额" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="老用户ARPU" v-bind="validateInfos.oldArpu">
            <a-input v-model:value="formData.oldArpu" placeholder="请输入老用户ARPU" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="老用户ARPPU" v-bind="validateInfos.oldArppu">
            <a-input v-model:value="formData.oldArppu" placeholder="请输入老用户ARPPU" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="ARPU" v-bind="validateInfos.arpu">
            <a-input v-model:value="formData.arpu" placeholder="请输入ARPU" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="ARPPU" v-bind="validateInfos.arppu">
            <a-input v-model:value="formData.arppu" placeholder="请输入ARPPU" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="新增付费率" v-bind="validateInfos.firstPayRate">
            <a-input v-model:value="formData.firstPayRate" placeholder="请输入新增付费率" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="总付费率" v-bind="validateInfos.totalRate">
            <a-input v-model:value="formData.totalRate" placeholder="请输入总付费率" :disabled="disabled"></a-input>
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
  import { saveOrUpdate } from '../PayAnalysis.api';
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
    startTime: '',   
    endTime: '',   
    type: '',   
    typeId: '',   
    name: '',   
    firstPayUser: '',   
    oldPayUser: '',   
    totalPayUser: '',   
    firstPayMoney: '',   
    oldPayMoney: '',   
    totalPayMoney: '',   
    aliveTotalMoney: '',   
    oldArpu: '',   
    oldArppu: '',   
    arpu: '',   
    arppu: '',   
    firstPayRate: '',   
    totalRate: '',   
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
