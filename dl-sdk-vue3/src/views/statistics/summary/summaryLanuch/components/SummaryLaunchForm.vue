<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏选择" v-bind="validateInfos.gameId">
	          <j-dict-select-tag v-model:value="formData.gameId" dictCode="" placeholder="请选择游戏选择" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="子游戏" v-bind="validateInfos.subGameId">
	          <j-dict-select-tag v-model:value="formData.subGameId" dictCode="" placeholder="请选择子游戏" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏包" v-bind="validateInfos.pkgId">
	          <j-dict-select-tag v-model:value="formData.pkgId" dictCode="" placeholder="请选择游戏包" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道类型" v-bind="validateInfos.channelTypeId">
	          <j-dict-select-tag v-model:value="formData.channelTypeId" dictCode="" placeholder="请选择渠道类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道" v-bind="validateInfos.channelId">
	          <j-dict-select-tag v-model:value="formData.channelId" dictCode="" placeholder="请选择渠道" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道子账号" v-bind="validateInfos.channelSubAccountId">
	          <j-dict-select-tag v-model:value="formData.channelSubAccountId" dictCode="" placeholder="请选择渠道子账号" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告编号" v-bind="validateInfos.dealId">
	          <j-checkbox type="checkbox" v-model:value="formData.dealId" dictCode="" placeholder="请选择广告编号" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="投放账号" v-bind="validateInfos.account">
	          <j-checkbox type="checkbox" v-model:value="formData.account" dictCode="" placeholder="请选择投放账号" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册时间" v-bind="validateInfos.registryTime">
		        <a-date-picker placeholder="请选择注册时间" v-model:value="formData.registryTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="付费时间" v-bind="validateInfos.payTime">
		        <a-date-picker placeholder="请选择付费时间" v-model:value="formData.payTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="归类方式" v-bind="validateInfos.type">
            <a-input v-model:value="formData.type" placeholder="请输入归类方式" :disabled="disabled"></a-input>
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
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JCheckbox from "/@/components/Form/src/jeecg/components/JCheckbox.vue";
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../SummaryLaunch.api';
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
    channelTypeId: undefined,
    channelId: undefined,
    channelSubAccountId: undefined,
    dealId: undefined,
    account: undefined,
    registryTime: '',   
    payTime: '',   
    type: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    registryTime: [{ required: true, message: '请输入注册时间!'},],
    payTime: [{ required: true, message: '请输入付费时间!'},],
    type: [{ required: true, message: '请输入归类方式!'},],
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
