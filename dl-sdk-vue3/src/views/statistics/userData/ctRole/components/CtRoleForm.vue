<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="游戏" v-bind="validateInfos.gameId">
	          <a-input-number v-model:value="formData.gameId" placeholder="请输入游戏" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="子游戏" v-bind="validateInfos.subGameId">
	          <a-input-number v-model:value="formData.subGameId" placeholder="请输入子游戏" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道游戏包" v-bind="validateInfos.pkgId">
	          <a-input-number v-model:value="formData.pkgId" placeholder="请输入渠道游戏包" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="所属渠道" v-bind="validateInfos.channelId">
	          <a-input-number v-model:value="formData.channelId" placeholder="请输入所属渠道" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="广告ID" v-bind="validateInfos.dealId">
	          <a-input-number v-model:value="formData.dealId" placeholder="请输入广告ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="用户ID" v-bind="validateInfos.userId">
	          <a-input-number v-model:value="formData.userId" placeholder="请输入用户ID" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="角色ID" v-bind="validateInfos.roleId">
            <a-input v-model:value="formData.roleId" placeholder="请输入角色ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="角色名" v-bind="validateInfos.roleName">
            <a-input v-model:value="formData.roleName" placeholder="请输入角色名" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="角色等级" v-bind="validateInfos.roleLevel">
	          <a-input-number v-model:value="formData.roleLevel" placeholder="请输入角色等级" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="服务器ID" v-bind="validateInfos.serverId">
            <a-input v-model:value="formData.serverId" placeholder="请输入服务器ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="在线时长" v-bind="validateInfos.onlineTime">
	          <a-input-number v-model:value="formData.onlineTime" placeholder="请输入在线时长" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册时间" v-bind="validateInfos.createTime">
		        <a-date-picker placeholder="请选择注册时间" v-model:value="formData.createTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="最新登录时间" v-bind="validateInfos.aliveTime">
		        <a-date-picker placeholder="请选择最新登录时间" v-model:value="formData.aliveTime" value-format="YYYY-MM-DD"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="渠道子账号id" v-bind="validateInfos.channelSubAccountId">
	          <a-input-number v-model:value="formData.channelSubAccountId" placeholder="请输入渠道子账号id" style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="服务器名" v-bind="validateInfos.serverName">
            <a-input v-model:value="formData.serverName" placeholder="请输入服务器名" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="注册-设备" v-bind="validateInfos.createDevice">
            <a-input v-model:value="formData.createDevice" placeholder="请输入注册-设备" :disabled="disabled"></a-input>
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
  import { saveOrUpdate } from '../CtRole.api';
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
    channelId: undefined,
    dealId: undefined,
    userId: undefined,
    roleId: '',   
    roleName: '',   
    roleLevel: undefined,
    serverId: '',   
    onlineTime: undefined,
    createTime: '',   
    aliveTime: '',   
    channelSubAccountId: undefined,
    serverName: '',   
    createDevice: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请输入游戏!'},],
    subGameId: [{ required: true, message: '请输入子游戏!'},],
    pkgId: [{ required: true, message: '请输入渠道游戏包!'},],
    channelId: [{ required: true, message: '请输入所属渠道!'},],
    dealId: [{ required: true, message: '请输入广告ID!'},],
    userId: [{ required: true, message: '请输入用户ID!'},],
    roleId: [{ required: true, message: '请输入角色ID!'},],
    roleName: [{ required: true, message: '请输入角色名!'},],
    roleLevel: [{ required: true, message: '请输入角色等级!'},],
    serverId: [{ required: true, message: '请输入服务器ID!'},],
    onlineTime: [{ required: true, message: '请输入在线时长!'},],
    createTime: [{ required: true, message: '请输入注册时间!'},],
    aliveTime: [{ required: true, message: '请输入最新登录时间!'},],
    channelSubAccountId: [{ required: true, message: '请输入渠道子账号id!'},],
    serverName: [{ required: true, message: '请输入服务器名!'},],
    createDevice: [{ required: true, message: '请输入注册-设备!'},],
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
