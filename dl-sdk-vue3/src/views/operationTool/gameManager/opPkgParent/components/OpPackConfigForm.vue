<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row >
        <a-col :span="24">
          <a-form-item label="子游戏ID" v-bind="validateInfos.subGameId">
	          <a-input-number v-model:value="formData.subGameId" placeholder="请输入子游戏ID" style="width: 100%" disabled/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="子游戏名称" v-bind="validateInfos.gameName">
	          <a-input v-model:value="formData.gameName" placeholder="请输入子游戏ID" style="width: 100%" disabled/>
          </a-form-item>
        </a-col>
        <a-col :span="24" >
          <div class="cell_dashed">游戏闪屏</div>
          <div class="file-select">
            <a-form-item label="文件格式: " v-bind="validateInfos.screen_type" style="width:42%"  >
              <j-dict-select-tag v-model:value="formData.screen_type" dictCode="img_type"   placeholder="请选择打包配置" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片宽:" v-bind="validateInfos.screen_width" style="width:32%">
              <a-input v-model:value="formData.screen_width"   placeholder="请输入图片宽" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片高:" v-bind="validateInfos.screen_height" style="width:32%">
              <a-input v-model:value="formData.screen_height"   placeholder="请输入图片高" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="项目路径:" v-bind="validateInfos.screen_path" style="width:48%">
              <a-input v-model:value="formData.screen_path"   placeholder="请输入项目路径" :disabled="disabled"/>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24" >
          <div class="cell_dashed">游戏加载图</div>
          <div class="file-select">
            <a-form-item label="文件格式: " v-bind="validateInfos.loading_type" style="width:42%"  >
              <j-dict-select-tag v-model:value="formData.loading_type" dictCode="img_type"   placeholder="请选择打包配置" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片宽:" v-bind="validateInfos.loading_width" style="width:32%">
              <a-input v-model:value="formData.loading_width"   placeholder="请输入图片宽" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片高:" v-bind="validateInfos.loading_height" style="width:32%">
              <a-input v-model:value="formData.loading_height"   placeholder="请输入图片高" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="项目路径:" v-bind="validateInfos.loading_path" style="width:48%">
              <a-input v-model:value="formData.loading_path"   placeholder="请输入项目路径" :disabled="disabled"/>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24" >
          <div class="cell_dashed">游戏登陆图</div>
          <div class="file-select">
            <a-form-item label="文件格式:" v-bind="validateInfos.login_type" style="width:42%"  >
              <j-dict-select-tag v-model:value="formData.login_type" dictCode="img_type"   placeholder="请选择打包配置" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片宽:" v-bind="validateInfos.login_width" style="width:32%">
              <a-input v-model:value="formData.login_width"   placeholder="请输入图片宽" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片高:" v-bind="validateInfos.login_height" style="width:32%">
              <a-input v-model:value="formData.login_height"   placeholder="请输入图片高" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="项目路径:" v-bind="validateInfos.login_path" style="width:48%">
              <a-input v-model:value="formData.login_path"   placeholder="请输入项目路径" :disabled="disabled"/>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24" >
          <div class="cell_dashed">游戏logo</div>
          <div class="file-select">
            <a-form-item label="文件格式: " v-bind="validateInfos.logo_type" style="width:42%"  >
              <j-dict-select-tag v-model:value="formData.logo_type" dictCode="img_type"   placeholder="请选择打包配置" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片宽:" v-bind="validateInfos.logo_width" style="width:32%">
              <a-input v-model:value="formData.logo_width"   placeholder="请输入图片宽" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="图片高:" v-bind="validateInfos.logo_height" style="width:32%">
              <a-input v-model:value="formData.logo_height"   placeholder="请输入图片高" :disabled="disabled"/>
            </a-form-item>
            <a-form-item label="项目路径:" v-bind="validateInfos.logo_path" style="width:48%">
              <a-input v-model:value="formData.logo_path"   placeholder="请输入项目路径" :disabled="disabled"/>
            </a-form-item>
          </div>
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
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpPackConfig.api';
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
    subGameId: undefined,
    gameName: undefined,
    packConfig: undefined,
    screen_type: 'jpg',
    screen_width: '',
    screen_height: '',
    screen_path: '',
    loading_type: 'jpg',
    loading_width: '',
    loading_height: '',
    loading_path: '',
    login_type: 'jpg',
    login_width: '',
    login_height: '',
    login_path: '',   
    logo_type: 'jpg',
    logo_width: '',
    logo_height: '',
    logo_path: '',
    createBy: undefined,
    createTime: undefined,
  });
  
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    subGameId: [{ required: true, message: '请输入子游戏ID!'},],
    // packConfig: [{ required: true, message: '请输入打包配置!'},],
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
    // edit({});
  }

  /**
   * 编辑
   */
  function edit(record, gameName) {
    nextTick(() => {
      resetFields();
      //赋值
      formData.id = record.id;
      formData.subGameId = record.subGameId;
      formData.createBy = record.createBy;
      formData.createTime = record.createTime;
      formData.gameName = gameName;
      formData.packConfig = record.packConfig;
      if(record.packConfig['screen_type'] === '') {
        formData.screen_type =  'jpg';
      }else {
        formData.screen_type =  record.packConfig['screen_type'];
      }
      if(record.packConfig['loading_type'] === '') {
        formData.loading_type =  'jpg';
      }else {
        formData.loading_type =  record.packConfig['loading_type'];
      }
      if(record.packConfig['login_type'] === '') {
        formData.login_type =  'jpg';
      }else {
        formData.login_type =  record.packConfig['login_type'];
      }
      if(record.packConfig['logo_type'] === '') {
        formData.logo_type =  'jpg';
      }else {
        formData.logo_type =  record.packConfig['logo_type'];
      }
      formData.screen_width =  record.packConfig['screen_width'];
      formData.screen_height =  record.packConfig['screen_height'];
      formData.screen_path =  record.packConfig['screen_path'];
      formData.loading_width =  record.packConfig['loading_width'];
      formData.loading_height =  record.packConfig['loading_height'];
      formData.loading_path =  record.packConfig['loading_path'];
      formData.login_width =  record.packConfig['login_width'];
      formData.login_height =  record.packConfig['login_height'];
      formData.login_path =  record.packConfig['login_path'];
      formData.logo_width =  record.packConfig['logo_width'];
      formData.logo_height =  record.packConfig['logo_height'];
      formData.logo_path =  record.packConfig['logo_path'];
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
    formData.packConfig['screen_type'] = formData.screen_type;
    formData.packConfig['loading_type'] = formData.loading_type;
    formData.packConfig['login_type'] = formData.login_type;
    formData.packConfig['logo_type'] = formData.logo_type;
    formData.packConfig['screen_width'] = formData.screen_width;
    formData.packConfig['screen_height'] = formData.screen_height;
    formData.packConfig['screen_path'] = formData.screen_path;
    formData.packConfig['loading_width'] = formData.loading_width;
    formData.packConfig['loading_height'] = formData.loading_height;
    formData.packConfig['loading_path'] = formData.loading_path;
    formData.packConfig['login_width'] = formData.login_width;
    formData.packConfig['login_height'] = formData.login_height;
    formData.packConfig['login_path'] = formData.login_path;
    formData.packConfig['logo_width'] = formData.logo_width;
    formData.packConfig['logo_height'] = formData.logo_height;
    formData.packConfig['logo_path'] = formData.logo_path;
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
    await saveOrUpdate(model)
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
  .file-select {
    width: 100%;
    display: flex;
    margin-left:20px;
    .ant-select {
      width: 33.3%;
    }
    .ant-select:not(:first-child) {
      margin-left: 10px;
    }
  }
  
  .cell_dashed{
    border-bottom: 1px dashed #999999;
    height: 1rem;
    text-align: center;
    margin-bottom: 1rem;
    margin-left: 1rem;
    margin-right: 1rem;
  }
</style>
