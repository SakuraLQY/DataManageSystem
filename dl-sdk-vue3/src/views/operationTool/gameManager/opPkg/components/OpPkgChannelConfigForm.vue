<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="渠道" v-bind="validateInfos.channelId">
              <j-search-select placeholder="请选择渠道" v-model:value="formData.channelId" dict="open_gateway.op_channel,channel_name,id" allowClear/>
          </a-form-item>
        </a-col>
        <!-- 今日头条 -->
        <template v-if="formData.channelId == 4 || formData.channelId == 5">
          <a-col :span="24">
            <a-form-item label="appId" >
              <a-input v-model:value="formData.channelConf.appId" placeholder="请输入appId" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="appKey" >
              <a-input v-model:value="formData.channelConf.appKey" placeholder="请输入appKey" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="packageId" >
              <a-input v-model:value="formData.channelConf.packageId" placeholder="请输入应用包id" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="packageName" >
              <a-input v-model:value="formData.channelConf.packageName" placeholder="请输入包名" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建者账号id">
              <j-search-select placeholder="请选择头条账号" v-model:value="formData.channelConf.createAccountId" dict="open_gateway.op_put_account,nick_name,id,channel_id=4 and state=1"/>
            </a-form-item>
          </a-col>
        </template>
         <!-- 快手 -->
         <template v-if="formData.channelId == 9">
          <a-col :span="24">
            <a-form-item label="appId" >
              <a-input v-model:value="formData.channelConf.appId" placeholder="请输入appId" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="appName" >
              <a-input v-model:value="formData.channelConf.appName" placeholder="请输入appName" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="appChannel" >
              <a-input v-model:value="formData.channelConf.appChannel" placeholder="请输入appChannel" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建者账号id">
              <j-search-select placeholder="请选择快手账号" v-model:value="formData.channelConf.createAccountId" dict="open_gateway.op_put_account,nick_name,id,channel_id=9 and state=1"/>
            </a-form-item>
          </a-col>
        </template>
         <!-- 广点通 -->
         <template v-if="formData.channelId == 3">
          <a-col :span="24">
            <a-form-item label="appId" >
              <a-input v-model:value="formData.channelConf.appId" placeholder="请输入appId" :disabled="disabled"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建者账号id">
              <j-search-select placeholder="请选择广点通账号" v-model:value="formData.channelConf.createAccountId" dict="open_gateway.op_put_account,nick_name,id,channel_id=3 and state=1"/>
            </a-form-item>
          </a-col>
        </template>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted, watch } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpPkg.api';
  import { Form } from 'ant-design-vue';
  import { optionList } from '../../opSubGame/OpSubGame.api';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    channelId: '',  //今日头条
    channelConf:{}
  });
  // 监听下拉框变化
  watch(
    () => formData.channelId,
    (newValue, oldValue) => {
      if(oldValue){
        // 初次加载不改变值
        formData.channelConf = {};
      }
      
    },
  );
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    channelId: [{ required: true, message: '请选择渠道!'},],
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
      // resetFields();
      //赋值
      formData.id = record.id;
      formData.channelId = record.channelId;
      if(record.channelConf){
        formData.channelConf = JSON.parse(record.channelConf);
        // 数字类型下拉框无法回显
        formData.channelConf.createAccountId = formData.channelConf.createAccountId + '';
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
    console.log("submit")
    let model = formData;
    if(model.channelConf){    
      model.channelConf = JSON.stringify(formData.channelConf);
    }
    if(model.channelConf.createAccountId){
      model.channelConf.createAccountId = Number(model.channelConf.createAccountId);
    }
    await saveOrUpdate(model, true)
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
