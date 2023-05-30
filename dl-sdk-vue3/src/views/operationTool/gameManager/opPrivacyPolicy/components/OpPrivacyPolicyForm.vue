<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="名称" v-bind="validateInfos.name" >
	          <a-input v-model:value="formData.name" maxLength="30" placeholder="长度限制至30位"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
          <!-- <a-form-item label="游戏" v-bind="validateInfos.pkgIds" >
            <a-tree-select
            v-model:value="formData.pkgIds"
            style="width: 100%"
            :tree-data="treeData"
            tree-checkable
            allow-clear
            :show-checked-strategy="SHOW_PARENT"
            search-placeholder="Please select"
          />
          </a-form-item> -->
        </a-col>
        <!-- <a-col :span="24">
          <a-form-item label="文件" v-bind="validateInfos.file"  >
            <a-upload-dragger
            v-model:fileList="fileList"
            name="import"
            accept=".html"
            :max-count="1"
            :beforeUpload="handleUploadBefore"
            @remove="handleFileRemove"
          >
            <p class="ant-upload-drag-icon">
              <LoadingOutlined v-if="uploading"/>
              <UploadOutlined v-else/>
            </p>
            <p class="ant-upload-text">点击进行文件上传</p>
            <p class="ant-upload-hint">
              仅支持单文件上传，格式：.html
            </p>
          </a-upload-dragger>
          </a-form-item>
        <a-button  type="primary" preIcon="ant-design:download-outlined" size="small" @click="handlePreview">预览</a-button>
        </a-col> -->
        <a-col :span="24">
          <a-form-item label="内容" v-bind="validateInfos.content" >
              <a-textarea v-model:value="formData.content" rows="8" style="width:800px;height:400px" placeholder="请输入内容" :disabled="disabled"/>
          </a-form-item>
        </a-col>
        <a-col :span="24" v-if="isEdit">
          <a-form-item label="地址" v-bind="validateInfos.url" >
	          <a-input v-model:value="formData.url"  style="width: 100%" :disabled="true"/>
          </a-form-item>
        </a-col>
        <!-- <a-col :span="24" >
          <a-form-item label="备注" v-bind="validateInfos.descs" >
	          <a-input v-model:value="formData.descs"  style="width: 100%" :disabled="disabled"/>
          </a-form-item>
        </a-col> -->
      </a-row>
    </a-form>
  </a-spin>
</template>

<script lang="ts" setup>
  import { watch, ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import { useListPage } from '/@/hooks/system/useListPage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate, getHtmlText } from '../OpPrivacyPolicy.api';
  // import { getOptionList } from '/@/views/operationTool/gameManager/opPkg/OpPkg.api';
  import { Form, TreeSelect  } from 'ant-design-vue';
  import func from '../../../../../../vue-temp/vue-editor-bridge';
 
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true },
  });
  let gameList = ref([]);
  const formRef = ref();
  let isEdit = ref(false);
  // const uploadInput = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    name: '',
    // pkgIds: [],
    content: '',
    // descs: '',
    url: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  const uploading = ref<boolean>(false);
  // let uploadData = new FormData();
  
  //表单验证
  const validatorRules = {
    content: [{ required: true, message: '内容不能为空!'},],
    name: [{ required: true, message: '名称不能为空!'},],
    // pkgIds: [{ required: true, message: '请选择游戏!'},],
    // file: [{ required: true, message: '请选择文件!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });
  const fileList = ref<UploadProps['fileList']>([])
  const handleUploadBefore = () => {
    return false;
  }
  watch(fileList, (newName, oldName) => {
    let fileValue = new FormData();
    for (let index = 0; index < fileList.value.length; index++) {
      const file = fileList.value[index];
      fileValue.append('file', file.originFileObj);
      getHtmlText(fileValue).then((res: any)=>{
        formData.content = res
      })
    }
    
  },{deep:false});
  // const SHOW_PARENT = TreeSelect.SHOW_PARENT;
  // const treeData = ref([]);
  
// watch(formData, () => {
//       console.log(formData.pkgIds);
//     });

// const handleSave = () => {
//     uploading.value = true;
//     fileList.value?.forEach((file: any) => {
// 	// 注意这里一定要使用 originFileObj，否则会报错：Required request part 'file' is not present，意思是后端找不到 file 对应的文件对象
//       console.log('ilr-->'+file.originFileObj);
//       formData.append('file', file.originFileObj);
//     });
//     // importApi(form).then(() => {
//     //   console.log('文件上传成功')
//     //   message.success('文件上传成功')
//     // }).finally(() => uploading.value = false)
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

  // function getList() {
  //   getOptionList({type:1}).then((res: any)=>{
  //       treeData.value = res
  //     })
  // }

  /**
   * 新增
   */
  function add(list, val) {
    gameList.value = list;
    edit({},gameList.value,val);
  }

  /**
   * 编辑
   */
  function edit(record,list,val) {
  //  getList();
   isEdit.value = val;
   gameList.value = list;
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
  //   uploading.value = true;
  //   uploadData.append('id',formData.id);
  //   uploadData.append('gameId',formData.gameId);
  //   fileList.value?.forEach((file: any) => {
  //     console.log('file-->'+file);
	// // 注意这里一定要使用 originFileObj，否则会报错：Required request part 'file' is not present，意思是后端找不到 file 对应的文件对象
  //     console.log('ilr-->'+file.originFileObj);
  //     // formData.file = file.originFileObj
  //     uploadData.append('file', file.originFileObj);
  //   });
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    // let model = uploadData;
    // console.log('model-->'+model);
    // console.log('id-->',model.get('id'));
    // if (model.get('id')) {
    //   isUpdate.value = true;
    // }
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    // for (let data in model) {
    //   //如果该数据是数组并且是字符串类型
    //   if (model[data] instanceof Array) {
    //     let valueType = getValueType(formRef.value.getProps, data);
    //     //如果是字符串类型的需要变成以逗号分割的字符串
    //     // if (valueType === 'string') {
    //     //   model[data] = model[data].join(',');
    //     // }
    //   }
    // }
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
        uploading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
    // treeData,
    // SHOW_PARENT,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    min-height: 500px !important;
    overflow-y: auto;
    padding: 24px 24px 24px 24px;
  }
  .excel-upload-input {
  display: none;
  z-index: -9999;
}
</style>
