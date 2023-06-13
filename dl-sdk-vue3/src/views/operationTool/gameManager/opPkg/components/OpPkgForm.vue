<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <GameOptionModal ref="selectForm" v-model:value="formData" @handlerModal="getGameVal"></GameOptionModal>
          <a-col :span="10">
            <a-col :span="24">
              <a-form-item label="厂商" v-bind="validateInfos.vendorId">
                <j-search-select v-model:value="formData.vendorId" dict="open_gateway.op_vendor,vendor_name,id" placeholder="请选择厂商"  :disabled="disabled" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="应用名" v-bind="validateInfos.nickName">
                <a-input v-model:value="formData.nickName" placeholder="请输入应用名" maxLength="50" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="应用包名" v-bind="validateInfos.packageName">
                <a-input v-model:value="formData.packageName" placeholder="请输入游戏包名" maxLength="50" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏版本" v-bind="validateInfos.versionName">
                <a-input v-model:value="formData.versionName" placeholder="请输入游戏版本" maxLength="20" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏构建版本" v-bind="validateInfos.versionCode">
                <a-input v-model:value="formData.versionCode" placeholder="请输入游戏构建版本" maxLength="20" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="下载链接" v-bind="validateInfos.downloadUrl">
                <a-input v-model:value="formData.downloadUrl" placeholder="请输入下载链接" maxLength="200" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="备注" v-bind="validateInfos.descs">
                <a-input v-model:value="formData.descs" placeholder="请输入备注" maxLength="255" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏icon" :labelCol="labelCol"  :wrapperCol="wrapperCol">
                <OpPkgUploadMaterial :subGameId="formData.subGameId" :materialType="1" v-model:value="formData.iconMaterialPackPath"></OpPkgUploadMaterial>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏闪屏" :labelCol="labelCol"  :wrapperCol="wrapperCol" >
                <OpPkgUploadMaterial :subGameId="formData.subGameId" :materialType="2" v-model:value="formData.screenMaterialPackPath"></OpPkgUploadMaterial>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏加载图" :labelCol="labelCol"  :wrapperCol="wrapperCol">
                <OpPkgUploadMaterial :subGameId="formData.subGameId" :materialType="3" v-model:value="formData.loadingMaterialPackPath"></OpPkgUploadMaterial>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏登陆图" :labelCol="labelCol"  :wrapperCol="wrapperCol">
                <OpPkgUploadMaterial :subGameId="formData.subGameId" :materialType="4" v-model:value="formData.loginMaterialPackPath"></OpPkgUploadMaterial>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="游戏logo" :labelCol="labelCol"  :wrapperCol="wrapperCol">
                <OpPkgUploadMaterial :subGameId="formData.subGameId" :materialType="5" v-model:value="formData.logoMaterialPackPath"></OpPkgUploadMaterial>
              </a-form-item>
            </a-col>
          </a-col>
          <a-col :span="14">
            <a-col :span="24">
              <a-form-item label="是否开启平台币" v-bind="validateInfos.platformCurrencySwitch" >
                <j-search-select v-model:value="formData.platformCurrencySwitch" dict="game_is_open" placeholder="请选择是否开启平台币" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="折扣配置" v-bind="validateInfos.platformCurrencyDiscount" v-show="platformCurrencyIfShow">
                <a-input-number v-model:value="formData.platformCurrencyDiscount" min="0" max="100" placeholder="请输入折扣配置" style="width: 100%" :disabled="disabled"/>
                <h1 style="color:red">折扣按百分比换算，比如60表示可以用平台币抵60%，支持0到100</h1>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="包id配置" v-bind="validateInfos.platformCurrencyPkgConfig" v-show="platformCurrencyIfShow">
                <a-input v-model:value="formData.platformCurrencyPkgConfig" placeholder="请输入包id配置" :disabled="disabled"></a-input>
                <h1 style="color:red">多包ID请用;进行分割，例子： 1;2;3,*表示</h1>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="绑定手机开关" v-bind="validateInfos.phoneSwitch">
                <j-search-select v-model:value="formData.phoneSwitch" dict="game_is_open" placeholder="请选择绑定手机开关" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24" v-show="false">
              <a-form-item label="隐私政策开关" v-bind="validateInfos.privacySwitch">
                <j-search-select v-model:value="formData.privacySwitch" dict="game_is_open" placeholder="请选择隐私政策开关" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="实名验证窗口" v-bind="validateInfos.idAuthSwitch" >
                <j-search-select v-model:value="formData.idAuthSwitch" dict="game_is_open" placeholder="请选择实名验证窗口" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="是否调用接口" v-bind="validateInfos.idAuthApi" v-show="idAuthSwitchIfShow">
                <j-search-select v-model:value="formData.idAuthApi" dict="game_is_open" placeholder="请选择是否调用接口" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="接入中宣部" v-bind="validateInfos.officialAntiIndulgeSwitch" v-show="idAuthSwitchIfShow">
                <j-search-select v-model:value="formData.officialAntiIndulgeSwitch" dict="game_is_open" placeholder="请选择是否接入中宣部" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="备案识别码" v-bind="validateInfos.officialBizId" v-if="idAuthSwitchIfShow && officialAntiIndulgeSwitchIfShow">
                <a-input v-model:value="formData.officialBizId" maxLength="11" placeholder="长度限制至11位" style="width: 100%" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="防沉迷开关" v-bind="validateInfos.antiIndulgeSwitch">
                <j-search-select v-model:value="formData.antiIndulgeSwitch" dict="game_is_open" placeholder="请选择防沉迷开关" :disabled="disabled"/>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="隐私政策地址" >
                <a-input v-model:value="formData.privacyPolicyUrl" placeholder="请输入隐私政策地址" maxLength="200" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="用户协议地址" >
                <a-input v-model:value="formData.userAgreementUrl" placeholder="请输入用户协议地址" maxLength="200" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item label="客服地址" >
                <a-input v-model:value="formData.customerServiceUrl" placeholder="请输入客服地址" maxLength="200" :disabled="disabled"></a-input>
              </a-form-item>
            </a-col>
          </a-col>
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
  import OpPkgUploadMaterial from './OpPkgUploadMaterial.vue';
  import GameOptionModal from '/@/views/common/gameOptionModal.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';

  let getGameVal = (e:any) => {
    formData.gameId = e.gameId;
    formData.subGameId = e.subGameId;
  }
  const selectForm= ref();
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: ()=>{} },
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  let platformCurrencyIfShow = ref<boolean>(true)
  let idAuthSwitchIfShow = ref<boolean>(true)
  let officialAntiIndulgeSwitchIfShow = ref<boolean>(true)
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    gameId: undefined,
    vendorId: undefined,
    subGameId: undefined,
    packageName: undefined,
    versionName: undefined,
    versionCode: undefined,
    nickName: '',
    iconMaterialPackPath: undefined,
    screenMaterialPackPath: undefined,
    loadingMaterialPackPath: undefined,
    loginMaterialPackPath: undefined,
    logoMaterialPackPath: undefined,
    downloadUrl:'',   
    descs: '',   
    privacyPolicyUrl: undefined,
    userAgreementUrl: undefined,
    customerServiceUrl: undefined,
    platformCurrencySwitch: '0',
    platformCurrencyDiscount: undefined,
    platformCurrencyPkgConfig: '',   
    phoneSwitch: '0',
    privacySwitch: '0',
    idAuthSwitch: '0',
    idAuthApi: '0',
    officialAntiIndulgeSwitch: '0',
    officialBizId: undefined,
    antiIndulgeSwitch: '0',
  });
  //监听下拉框变化
  watch(
    () => formData.gameId,
    (newValue, oldValue) => {
      if(oldValue){
        formData.subGameId = '';
      }
    },
  );
  watch(formData, (newName, oldName) => {
    if(formData.platformCurrencySwitch !== '1' && formData.platformCurrencySwitch !== 1) {
      platformCurrencyIfShow.value = false
    }else{
      platformCurrencyIfShow.value = true
    }
    if(formData.idAuthSwitch !== '1' && formData.idAuthSwitch !== 1) {
      idAuthSwitchIfShow.value = false
    }else{
      idAuthSwitchIfShow.value = true
    }
    if(formData.officialAntiIndulgeSwitch !== '1' && formData.officialAntiIndulgeSwitch !== 1) {
      officialAntiIndulgeSwitchIfShow.value = false
    }else{
      officialAntiIndulgeSwitchIfShow.value = true
    }
  },{deep:false});
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    gameId: [{ required: true, message: '请输入游戏id!'},],
    vendorId: [{ required: true, message: '请选择厂商!'},],
    nickName: [{ required: true, message: '请输入游戏名!'},],
    packageName: [{ required: true, message: '请输入游戏应用包名!'},],
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
      selectForm.value.edit({gameId:record.gameId, subGameId: record.subGameId});
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
