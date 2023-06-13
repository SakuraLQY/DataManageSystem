<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="12">
          <a-form-item label="厂商名" v-bind="validateInfos.vendorName">
            <a-input v-model:value="formData.vendorName" placeholder="请输入厂商名" showCount :maxlength="64" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="支付宝" v-bind="validateInfos.aliPayVendor">
            <j-search-select
              v-model:value="formData.aliPayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 1,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择支付宝渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="汇付宝" v-bind="validateInfos.heePayVendor">
            <j-search-select
              v-model:value="formData.heePayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 2,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择汇付宝渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="易宝" v-bind="validateInfos.yeePayVendor">
            <j-search-select
              v-model:value="formData.yeePayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 3,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择易宝渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="爱贝" v-bind="validateInfos.iappPayVendor">
            <j-search-select
              v-model:value="formData.iappPayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 4,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择爱贝渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="现在支付" v-bind="validateInfos.ipaynowPayVendor">
            <j-search-select
              v-model:value="formData.ipaynowPayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 5,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择现在支付渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="现在支付app" v-bind="validateInfos.ipaynowappPayVendor">
            <j-search-select
              v-model:value="formData.ipaynowappPayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 6,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择现在支付app渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="微信支付" v-bind="validateInfos.wxPayVendor">
            <j-search-select
              v-model:value="formData.wxPayVendor"
              dict="open_gateway.op_pay_vendor where pay_type = 7,pay_vendor_name,id"
              :disabled="disabled"
              placeholder="请选择微信渠道"
              allowClear
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="备注" v-bind="validateInfos.note">
            <a-input v-model:value="formData.note" placeholder="请输入备注" :disabled="disabled" showCount :maxlength="255"></a-input>
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
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpVendor.api';
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
    vendorName: '',   
    aliPayVendor: undefined,
    heePayVendor: undefined,
    yeePayVendor: undefined,
    iappPayVendor: undefined,
    ipaynowPayVendor: undefined,
    ipaynowappPayVendor: undefined,
    wxPayVendor: undefined,
    note: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 6 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    vendorName: [{ required: true, message: '请输入厂商名!'},],
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
