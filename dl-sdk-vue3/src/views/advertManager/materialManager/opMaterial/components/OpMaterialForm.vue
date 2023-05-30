<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="15">
          <a-form-item label="素材名" v-bind="validateInfos.materialName">
            <a-input v-model:value="formData.materialName" style="width:500px" maxLength="64" placeholder="长度限制至64位"  :disabled="disabled"></a-input>
            <span style="color:red"> 选填，如果不填就采用文件名</span>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
            <!-- <span style="color:red;margin-right:6px;margin-left:50px;">*</span> -->
            <span style="white-space: normal;margin-left:55px;">头条上传账号 : </span>
            <a-form-item  v-bind="validateInfos.inUploadJrtt" style="margin-left:8px;width:200px" >
              <j-search-select v-model:value="formData.inUploadJrtt"  dict="select_upload" placeholder="请选择是否上传" :disabled="disabled"/>
            </a-form-item>
            <a-form-item  v-bind="validateInfos.jrttAccountId" style="width:400px" >
              <j-search-select placeholder="请选择头条账号" v-model:value="formData.jrttAccountId" dict="open_gateway.op_put_account ,nick_name,id,channel_id=4 and state=1 and level_id = 2" allowClear/>
            </a-form-item>
            <a-form-item style="width:350px">
              <span style="color:red"> 如需上传到渠道，请选择类型为上传并选择账号</span>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
            <!-- <span style="color:red;margin-right:6px;margin-left:50px;">*</span> -->
            <span style="white-space: normal;margin-left:40px;">广点通上传账号 : </span>
            <a-form-item  v-bind="validateInfos.inUploadGdt" style="margin-left:8px;width:200px" >
              <j-search-select v-model:value="formData.inUploadGdt" style="margin-left:200px" dict="select_upload" placeholder="请选择是否上传" :disabled="disabled"/>
            </a-form-item>
            <a-form-item  v-bind="validateInfos.gdtAccountId" style="width:400px" >
              <j-search-select placeholder="请选择广点通账号" v-model:value="formData.gdtAccountId" dict="open_gateway.op_put_account,nick_name,id,channel_id=3 and state=1" allowClear/>
            </a-form-item>
            <a-form-item style="width:350px">
              <span style="color:red"> 如需上传到渠道，请选择类型为上传并选择账号</span>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
            <!-- <span style="color:red;margin-right:6px;margin-left:50px;">*</span> -->
            <span style="white-space: normal;;margin-left:55px;">快手上传账号 : </span>
            <a-form-item  v-bind="validateInfos.inUploadKs" style="margin-left:8px;width:200px" >
              <j-search-select v-model:value="formData.inUploadKs" style="margin-left:200px" dict="select_upload" placeholder="请选择是否上传" :disabled="disabled"/>
            </a-form-item>
            <a-form-item  v-bind="validateInfos.ksAccountId" style="width:400px" >
              <j-search-select placeholder="请选择快手账号" v-model:value="formData.ksAccountId" dict="open_gateway.op_put_account,nick_name,id,channel_id=9 and state=1 and level_id = 2" allowClear/>
            </a-form-item>
            <a-form-item style="width:350px">
              <span style="color:red"> 如需上传到渠道，请选择类型为上传并选择账号</span>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
          <GameOptionModal ref="selectForm" @handlerModal="getGameVal"></GameOptionModal>
            <a-form-item>
              <span style="color:red">可以不选择子游戏，如若不选则代表全部</span>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
            <span style="color:red;margin-right:6px;margin-left:70px;">*</span>
            <span style="white-space: normal;width:100px">素材类型 : </span>
            <a-form-item  v-bind="validateInfos.type1" style="width:200px" >
              <j-search-select v-model:value="formData.type1"  dict="material_manager_type" placeholder="请选择素材类型" :disabled="disabled"/>
            </a-form-item>
            <a-form-item>
              <span style="margin-right:70px">/</span>
            </a-form-item>
            <a-form-item  v-bind="validateInfos.type2" style="width:250px" >
	            <j-search-select v-if="formData.type1 === '1'"  v-model:value="formData.type2"   dict="material_img_type" placeholder="请选择素材归类" :disabled="disabled"/>
	            <j-search-select v-if="formData.type1 === '2'" v-model:value="formData.type2"   dict="material_video_type" placeholder="请选择素材归类" :disabled="disabled"/>
            </a-form-item>
            <a-form-item>
              <span style="color:red;width:300px">{{msg}}</span>
            </a-form-item>
          </div>
        </a-col>
        <a-col :span="15">
          <a-form-item label="素材文件" :labelCol="labelCol" :wrapperCol="wrapperCol" v-bind="validateInfos.file">
            <a-upload
              list-type="picture"
              action="/jeecgboot/advert/opMaterial/upload"
              v-model:file-list="fileList"
              @change="handleChange"
            >
              <a-button type="primary" v-if="fileList.length < 1">
                Upload
              </a-button>
            </a-upload>
           </a-form-item>
        </a-col>
        <a-col :span="15">
          <a-form-item label="素材备注" v-bind="validateInfos.materialDesc">
            <a-textarea v-model:value="formData.materialDesc" showCount maxLength="255" rows="4" placeholder="长度限制至255位" :disabled="disabled"></a-textarea>
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
  import { saveOrUpdate } from '../OpMaterial.api';
  import { Form } from 'ant-design-vue';
  import { message } from 'ant-design-vue';
  import { JVxeTypes } from '/@/components/jeecg/JVxeTable/types';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue'
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
    subGameId: '',
    gameId: undefined,
    materialName: '',   
    type1: '1',
    type2: '101',
    inUploadJrtt: '1',   
    jrttAccountId: undefined,   
    inUploadGdt: '1',   
    gdtAccountId: undefined,   
    inUploadKs: '1',   
    ksAccountId: undefined,   
    materialDesc: '',
  });
  const selectForm= ref();
  let getGameVal = (e:any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
  }
  let newFormData = new FormData();
  const fileList = ref<UploadProps['fileList']>([])
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  let msg = ref('落地页,尺寸不限，大小2M以下，由于图片会出现主体限制，不同主体需要从新上传');
  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请选择游戏ID!'},],
    // materialName: [{ required: true, message: '请输入素材名!'},],
    type1: [{ required: true, message: '请选择素材类型!'},],
    type2: [{ required: true, message: '请选择素材归类!'},],
    // file: [{ required: true, message: '请选择素材文件!'},],
    // jrttCreateAccountId: [{ required: true, message: '请输入头条素材账号!'},],
    // jrttMaterialId: [{ required: true, message: '请输入头条素材ID!'},],
    // gdtCreateAccountId: [{ required: true, message: '请输入广点通素材账号!'},],
    // gdtMaterialId: [{ required: true, message: '请输入广点通素材ID!'},],
    // kuaishouCreateAccountId: [{ required: true, message: '请输入快手素材账号!'},],
    // kuaishouMaterialId: [{ required: true, message: '请输入快手素材ID!'},],
    // materialDesc: [{ required: true, message: '请输入素材备注!'},],
  };
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  const handleChange = ({ file, fileList }: FileInfo) => {
      if (file.status !== 'uploading') {
        console.log(file, fileList);
        newFormData.set('file', file.response['result']);
      }
      
    };

    //监听下拉框变化
  watch(
      () => formData.type1,
      val => {
        if(val === '1') {
          formData.type2 = '101';
        }else {
          formData.type2 = '201';
        }
      },
    );
    //监听下拉框变化
  watch(
      () => formData.type2,
      val => {
        if(val === '101') {
          msg.value = '落地页,尺寸不限，大小2M以下，由于图片会出现主体限制，不同主体需要从新上传';
        }else if(val === '102') {
          msg.value = 'ICON图片，横版大图宽高比1，大小500K以下,尺寸：450 & 450';
        }else if(val === '201') {
          msg.value = '横版视频，封面图宽高比1.78（下限：1280 & 720，上限：2560 & 1440）,视频资源支持mp4、mpeg、3gp、avi文件格式，宽高比16:9，大小不超过100M';
        }else if(val === '202') {
          msg.value = '竖版视频，封面图宽高比0.56（9:16），下限：720 & 1280，上限：1440 & 2560，视频资源支持mp4、mpeg、3gp、avi文件格式，大小不超过100M';
        }
      },
    );
  const previewFile = async file => {
      console.log('Your upload file:', file); // Your process logic. Here we just mock to the same file

      const res = await fetch('https://next.json-generator.com/api/json/get/4ytyBoLK8', {
        method: 'POST',
        body: file,
      });
      const { thumbnail } = await res.json();
      return thumbnail;
  }

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
    fileList.value = [];
    edit(formData);
  }

  function gameOptionModalReload() {
    selectForm.value.reload();
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
    if(formData.gameId === undefined || formData.gameId === '') {
      return false;
    }
    if(fileList.value.length < 1) {
      message.warning('请选择素材文件');
      return false;
    }
    if(formData.subGameId === undefined) {
      formData.subGameId = '';
    }
    if(formData.jrttAccountId != undefined) {
      newFormData.set('jrttAccountId', formData.jrttAccountId);
    }
    if(formData.gdtAccountId != undefined) {
      newFormData.set('gdtAccountId', formData.gdtAccountId);
    }
    if(formData.ksAccountId != undefined) {
      newFormData.set('ksAccountId', formData.ksAccountId);
    }
    // fileList.value?.forEach((file: any) => {
	// 注意这里一定要使用 originFileObj，否则会报错：Required request part 'file' is not present，意思是后端找不到 file 对应的文件对象
      // newFormData.set('file', formData.file);
      newFormData.set('materialName', formData.materialName);
      newFormData.set('inUploadJrtt', formData.inUploadJrtt);
      newFormData.set('inUploadGdt', formData.inUploadGdt);
      newFormData.set('inUploadKs', formData.inUploadKs);
      newFormData.set('gameId', formData.gameId);
      newFormData.set('subGameId', formData.subGameId);
      newFormData.set('type1', formData.type1);
      newFormData.set('type2', formData.type2);
      newFormData.set('materialDesc', formData.materialDesc);
    // });
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = newFormData;
    if (model.get('id')) {
      isUpdate.value = true;
    }
    //循环数据
    // for (let data in model) {
    //   //如果该数据是数组并且是字符串类型
    //   if (model[data] instanceof Array) {
    //     let valueType = getValueType(formRef.value.getProps, data);
    //     //如果是字符串类型的需要变成以逗号分割的字符串
    //     if (valueType === 'string') {
    //       model[data] = model[data].join(',');
    //     }
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
        selectForm.value.reload();
        newFormData = new FormData();
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
    previewFile,
    gameOptionModalReload
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
    .ant-select {
      width: 33.3%;
    }
    .ant-select:not(:first-child) {
      margin-left: 10px;
    }
  }
</style>
