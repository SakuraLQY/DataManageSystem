<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.gameId">
            <a-select v-model:value="formData.gameId"  placeholder="请选择游戏名" :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="24">
            <a-form-item label="子游戏名" v-bind="validateInfos.subGameId">
              <a-select v-model:value="formData.subGameId" allowClear placeholder="请选择子游戏名" v-if="formData.gameId !=='' && formData.gameId !==undefined" :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in faList[formData.gameId].list"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
              <a-select v-model:value="formData.subGameId" placeholder="请选择子游戏名" v-if="formData.gameId ==='' || formData.gameId ===undefined" :disabled="disabled">
                <a-select-option
                  v-for="(gameItem,index) in []"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
            </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="母包备注" v-bind="validateInfos.parentDesc">
            <a-textarea v-model:value="formData.parentDesc" placeholder="请输入母包备注" :disabled="disabled" :maxlength="150"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpPkgParent.api';
  import { Form } from 'ant-design-vue';
  import {optionList} from '../../opSubGame/OpSubGame.api'
  
  
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
    apkName: '',   
    gameName: '',   
    packageName: '',   
    version: '',   
    versionCode: '',   
    parentDesc: '',   
    creatUser: '',   
    pkgUpdateTime: '',
    state:''   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  let faList = ref([]);
  getOptionList();
  // 下拉框取值
  function getOptionList() {
    optionList({status:0}).then((res: any)=>{
      faList.value = res;
    })
  };;

  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请输入游戏id!'},],
    subGameId: [{ required: true, message: '请输入子游戏id!'},],
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
