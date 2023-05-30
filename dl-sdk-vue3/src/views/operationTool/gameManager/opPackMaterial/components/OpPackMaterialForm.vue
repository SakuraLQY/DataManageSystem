<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="素材名" v-bind="validateInfos.materialName">
            <a-input v-model:value="formData.materialName" maxLength="64" placeholder="长度限制至64位" style="width:500px"  :disabled="disabled"></a-input>
            <span style="color:red"> 选填，如果不填就采用文件名</span>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <div class="file-select">
            <span style="color:red;margin-right:6px;margin-left:75px;">*</span>
            <span style="white-space: normal;margin-right:10px;width:66.3px">素材类型 : </span>
            <a-form-item  v-bind="validateInfos.type" style="width:30%">
              <j-dict-select-tag v-model:value="formData.type"   dictCode="material_type" placeholder="请选择素材类型" :disabled="disabled"/>
            </a-form-item>
            <span style="float:left;color:red">{{msg}}</span>
          </div>
        </a-col>
        <a-col :span="24">
          <div class="file-select" > 
          <GameOptionModal ref="selectForm" @handlerModal="getGameVal"></GameOptionModal>
          </div>
        </a-col>
        <a-col :span="24">
          <a-form-item label="图片" :labelCol="labelCol"  :wrapperCol="wrapperCol" v-bind="validateInfos.file">
            <div :key="ImgKey">
            <JImageUpload :fileMax="1" :bizPath="bizPath" v-model:value="fileList"></JImageUpload>
            </div>
          </a-form-item>
        </a-col>
        <a-col :span="24">
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
  import { saveOrUpdate } from '../OpPackMaterial.api';
  import { getObjByGameId } from '/@/views/operationTool/gameManager/opPkgParent/OpPackConfig.api.ts';
  import { optionList } from '../../opSubGame/OpSubGame.api';
  import { Form } from 'ant-design-vue';
  import JImageUpload from '/@/components/Form/src/jeecg/components/JImageUpload.vue';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue'
  import func from '../../../../../../vue-temp/vue-editor-bridge';
  import { message } from 'ant-design-vue';

  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  let msg = ref('游戏icon,尺寸为512*512像素的PNG，大小2M以下');
  let faList = ref([]);
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    gameId: undefined,
    subGameId: undefined,
    materialName: '',   
    type: '1',
    file: '',   
    materialDesc: '',   
    createBy: '',
    createTime: '',
  });
  const selectForm= ref();
  let getGameVal = (e:any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
  }
  const bizPath = "packMaterial";
  let ImgKey = ref('');
  const fileList = ref('');
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    // gameId: [{ required: true, message: '请选择游戏!'},],
    // subGameId: [{ required: true, message: '请选择子游戏!'},],
    // materialName: [{ required: true, message: '请输入素材名!'},],
    type: [{ required: true, message: '请选择素材类型!'},],
    // file: [{ required: true, message: '请选择素材文件!'},],
    // size: [{ required: true, message: '请输入素材大小!'},],
    // specs: [{ required: true, message: '请输入规格!'},],
    // path: [{ required: true, message: '请输入文件目录!'},],
    // showUrl: [{ required: true, message: '请输入素材链接!'},],
    // fileMd5: [{ required: true, message: '请输入素材md5!'},],
    // materialDesc: [{ required: true, message: '请输入素材备注!'},],
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

  getOptionList();
  // 游戏、子游戏下拉框取值
  function getOptionList() {
    optionList({status:0}).then((res: any)=>{
      faList.value = res
    })
  };
  //监听游戏下拉框变化
  watch(
      () => formData.gameId,
      val => {
        if(val === undefined || val === '') {
          formData.subGameId = undefined;
          if(formData.type === '1') {
          msg.value = "游戏icon,尺寸为512*512像素的PNG，大小2M以下";
          }else {
            msg.value = "由于尺寸不同，游戏限制不同，所以需要先确定游戏获取规格";
          }
        }else {
          formData.subGameId = undefined;
        }
      },
    );
    watch(
      () => formData.subGameId,
      val => {
        if(val === undefined || val === '' || val === 0) {
          msg.value = "由于尺寸不同，游戏限制不同，所以需要先确定游戏获取规格";
        }else {
          if(formData.type === '1') {
          msg.value = "游戏icon,尺寸为512*512像素的PNG，大小2M以下";
          } else {
            getObjByGameIdFun();
          }
        }
      },
    );
  //监听素材类型下拉框变化
  watch(
      () => formData.type,
      val => {
        if(val === '1') {
          msg.value = "游戏icon,尺寸为512*512像素的PNG，大小2M以下";
        }else {
          if(formData.subGameId === '' || formData.subGameId === undefined || formData.subGameId === 0) {
            msg.value = "由于尺寸不同，游戏限制不同，所以需要先确定游戏获取规格";
          }else {
            getObjByGameIdFun();
          }
        }
      },
    );
  
  function getObjByGameIdFun() {
    getObjByGameId({subGameId:formData.subGameId}).then((res: any)=>{
      if(res == null) {
                msg.value = "游戏未配置打包参数";
                return false;
              }
              if(formData.type === '2') {
                if(res.packConfig['screen_type'] === '' || res.packConfig['screen_width'] === '' || res.packConfig['screen_height'] === '' || res.packConfig['screen_path'] === '') {
                  msg.value = "该游戏未配置游戏闪屏打包配置";
                }else {
                  msg.value = '图片格式：' + res.packConfig['screen_type'] +', 宽：' + res.packConfig['screen_width'] + ', 高：' + res.packConfig['screen_height'];
                }
              }else if (formData.type === '3') {
                if(res.packConfig['loading_type'] === '' || res.packConfig['loading_width'] === '' || res.packConfig['loading_height'] === '' || res.packConfig['loading_path'] === '') {
                  msg.value = "该游戏未配置游戏加载图打包配置";
                }else {
                  msg.value = '图片格式：' + res.packConfig['loading_type'] +', 宽：' + res.packConfig['loading_width'] + ', 高：' + res.packConfig['loading_height'];
                }
              }else if (formData.type === '4') {
                if(res.packConfig['login_type'] === '' || res.packConfig['login_width'] === '' || res.packConfig['login_height'] === '' || res.packConfig['login_path'] === '') {
                  msg.value = "该游戏未配置游戏登陆图打包配置";
                }else {
                  msg.value = '图片格式：' + res.packConfig['login_type'] +', 宽：' + res.packConfig['login_width'] + ', 高：' + res.packConfig['login_height'];
                }
              }else if (formData.type === '5') {
                if(res.packConfig['logo_type'] === '' || res.packConfig['logo_width'] === '' || res.packConfig['logo_height'] === '' || res.packConfig['logo_path'] === '') {
                  msg.value = "该游戏未配置游戏logo打包配置";
                }else {
                  msg.value = '图片格式：' + res.packConfig['logo_type'] +', 宽：' + res.packConfig['logo_width'] + ', 高：' + res.packConfig['logo_height'];
                }
              }
            })
  }
  /**
   * 新增
   */
  function add() {
    ImgKey.value = ref(Math.random());
    edit(formData);
  }

  function gameReload() {
    selectForm.value.reload();
    fileList.value = '';
  }

  function editbegin(record) {
    fileList.value = record.showUrl;
    selectForm.value.edit(record);
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
    if(fileList.value === '') {
      message.warning('请选择图片');
      return false;
    }
    if(formData.type !== '1') {
      if(formData.subGameId === undefined || formData.subGameId === '' || formData.subGameId === 0) {
        message.warning('除游戏icon外，其余素材都需要选择子游戏');
        return false;
      }
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    formData.file = fileList.value;
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
          selectForm.value.reload();
          fileList.value = '';
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
    msg,
    gameReload,
    getObjByGameIdFun,
    editbegin
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
