<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="所属资产">
            <a-input v-model:value="assetObj2.assetName" disabled="false"/>
	          <!-- <j-dict-select-tag v-model:value="formData.assetsId" dictCode="open_gateway.op_jrtt_assets,asset_name,id" placeholder="请选择所属资产" disabled/> -->
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="事件ID" v-bind="validateInfos.eventId">
            <j-dict-select-tag placeholder="请选择事件" v-model:value="formData.eventId" :disabled="disabled" dictCode="event-IOS"/>
	          <!-- <j-dict-select-tag v-model:value="formData.eventId" dictCode="" placeholder="请选择事件ID" :disabled="disabled"/> -->
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="回传方式" v-bind="validateInfos.trackType">
	          <j-dict-select-tag v-model:value="formData.trackType" dictCode="track_type" placeholder="请选择回传方式" :disabled="disabled"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, watch, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpJrttEvents.api';
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
    assetsId: '',   
    eventId: undefined,
    trackType: '',   
  });
  let assetObj2 = reactive<Record<string, any>>({
    id: '',
    assetId: '',   
    gameType: undefined,
    assetName: '',   
  });

  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    eventId: [{ required: true, message: '请输入事件ID!'},],
    trackType: [{ required: true, message: '请输入回传方式!'},],
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
   * 起始
   */
  function begin(record2,assetObj,type) {
    Object.assign(assetObj2, assetObj);
    if(type === 0) {
      add();
    }else {
      edit(record2,assetObj);
    }
  }

  
  /**
   * 新增
   */
  function add() {
    edit({},null);
  }

  /**
   * 编辑
   */
  function edit(record,assetObj) {
    if(JSON.stringify(record) === '{}') {
      if(assetObj2.gameType === 0) {
        record.trackType = '0';
      }else {
        record.trackType = '1';
      }
    }
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
    formData.assetsId = assetObj2.id;
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
    begin,
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
