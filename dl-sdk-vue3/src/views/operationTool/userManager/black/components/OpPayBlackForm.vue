<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="规则类型" v-bind="validateInfos.ruleType">
	          <j-search-select v-model:value="formData.ruleType" dict="pay_rule_type" placeholder="请选择规则类型" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '0'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_game,game_name,id"  placeholder="请选择游戏" :disabled="disabled" />
          </a-form-item>
          <a-form-item label="子游戏" v-bind="validateInfos.ruleId" v-if="formData.ruleType === '1'">
            <j-search-select v-model:value="formData.ruleId" dict="open_gateway.op_sub_game,game_name,id"  placeholder="请选择子游戏" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="支付限制用户ID" v-bind="validateInfos.payLimitUserId">
	          <a-textarea v-model:value="formData.payLimitUserId" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="支付限制IP" v-bind="validateInfos.payLimitIp">
	          <a-textarea v-model:value="formData.payLimitIp" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="支付限制设备" v-bind="validateInfos.payLimitDevice">
	          <a-textarea v-model:value="formData.payLimitDevice" rows="4" placeholder="一行一个
（例：192.168.X.X
          192.168.X.X）" :disabled="disabled"/>
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
  import { saveOrUpdate } from '../OpPayBlack.api';
  import { gameAndSubGameList } from '/@/views/operationTool/gameManager/opGame/OpGame.api';
  import { Form } from 'ant-design-vue';
  import { message } from 'ant-design-vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  // let selectType = ref<number>(0)
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  // let gameList = ref([]);
  // let subGameList = ref([]);
  const formData = reactive<Record<string, any>>({
    id: '',
    ruleType: '0',
    ruleId: undefined,
    payLimitUserId: '',   
    payLimitIp: '',   
    payLimitDevice: '',   
    descs: '',   
  });
  // getGameAndSubGameList();
  // // 下拉框取值
  // function getGameAndSubGameList() {
  //   gameAndSubGameList({}).then((res: any)=>{
  //     gameList.value = res['gameList']
  //     subGameList.value = res['subGameList']
  //   })
  // };
  watch(
      () => formData.ruleType,
      val => {
         formData.ruleId = undefined; 
      })
  // watch(formData, (newName, oldName) => {
  //   if(formData.ruleType === '0') {
  //     selectType.value = 0
  //   }else{
  //     selectType.value = 1
  //   }
  // },{deep:false});
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    ruleType: [{ required: true, message: '请输入规则类型!'},],
    ruleId: [{ required: true, message: '请选择!'},],
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
    nextTick(() => {
      resetFields();
      //赋值
      Object.assign(formData, record);
    });
  }

  /**
   * 监控限制的那三个条件
   */
  function checkRule() {
    if(formData.payLimitUserId === '' && formData.payLimitIp === '' && formData.payLimitDevice === '' ) {
      message.warning('限制条件必填一个');
    }
  }


  /**
   * 提交数据
   */
  async function submitForm() {
    // 触发表单验证
    await validate();
    if(formData.payLimitUserId === '' && formData.payLimitIp === '' && formData.payLimitDevice === '' ) {
      message.warning('限制条件必填一个');
    }else{
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
