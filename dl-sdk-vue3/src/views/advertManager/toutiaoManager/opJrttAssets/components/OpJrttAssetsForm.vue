<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <PkgOptionSingleSelect ref="selectForm" @handlerModal="getGameVal" :initData="[4,5]" :disabled="disabled"></PkgOptionSingleSelect>
        <a-col :span="24">
          <a-form-item label="投放账号" v-bind="validateInfos.accountId" >
            <j-search-select placeholder="请选择投放账号" :disabled="disabled" v-model:value="formData.accountId" dict="open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ,nick_name,id"/>
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
  import { saveOrUpdate } from '../OpJrttAssets.api';
  import PkgOptionSingleSelect from '/@/views/common/pkgOptionSingleSelect.vue'
  import { Form, message } from 'ant-design-vue';
  // import { getOptionList } from '/@/views/operationTool/gameManager/opPkg/OpPkg.api';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
import func from '../../../../../../vue-temp/vue-editor-bridge';

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
    pkgIdList: undefined,
    accountId: undefined,
    channelId: 4,
  });
  const selectForm= ref();
  let getGameVal = (e:any) => {
    formData.pkgIdList = e.pkgIdList;
  }
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    // pkgIdList: [{ required: true, message: '请选择渠道游戏包!'},],
    accountId: [{ required: true, message: '请输入投放账号!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });
  // const treeData = ref([]);
  // getList();
  // function getList() {
  //   getOptionList({type:0}).then((res: any)=>{
  //       treeData.value = res
  //     })
  // }

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
   * 重置渠道游戏包
   */
  function pkgOptionSingleSelectReload() {
    selectForm.value.reload();
  }

  /**
   * 编辑
   */
  function edit(record) {
    selectForm.value.edit(record);
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
    if(formData.pkgIdList === undefined) {
      message.warning('请选择渠道游戏包');
      return;
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    // for (let data in model) {
    //   //如果该数据是数组并且是字符串类型
      if (model['createTime'] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, 'createTime');
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model['createTime'] = model['createTime'].join(',');
        }
      }
    // }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
          selectForm.value.reload();
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
    pkgOptionSingleSelectReload,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
</style>
