<template>
  <a-spin :spinning="confirmLoading">
    <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol">
      <a-row>
        <a-col :span="24">
          <a-form-item label="支付类型" v-bind="validateInfos.payType">
            <j-dict-select-tag v-model:value="formData.payType" :options="options" placeholder="请选择支付类型" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="支付渠道名称" v-bind="validateInfos.payVendorName">
            <a-input v-model:value="formData.payVendorName" placeholder="请输入支付渠道名称" showCount :maxlength="64" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 支付宝 -->
        <!-- appid -->
        <a-col :span="24">
          <a-form-item label="应用ID" v-bind="validateInfos.app_id" v-if="formData.payType == 1">
            <a-input v-model:value="formData.app_id" placeholder="请输入支付宝appId" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 合作者ID -->
        <a-col :span="24">
          <a-form-item label="合作者ID" v-bind="validateInfos.seller_id" v-if="formData.payType == 1">
            <a-input v-model:value="formData.seller_id" placeholder="请输入支付宝合作者ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 合作者账号 -->
        <a-col :span="24">
          <a-form-item label="合作者账号" v-bind="validateInfos.seller_email" v-if="formData.payType == 1">
            <a-input v-model:value="formData.seller_email" placeholder="请输入支付宝合作者账号" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 合作者公钥 -->
        <a-col :span="24">
          <a-form-item label="合作者公钥" v-bind="validateInfos.seller_pubkey" v-if="formData.payType == 1">
            <a-textarea v-model:value="formData.seller_pubkey" :auto-size="{ minRows: 5 }" placeholder="请输入合作者公钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 合作者私钥 -->
        <a-col :span="24">
          <a-form-item label="合作者私钥" v-bind="validateInfos.seller_prikey" v-if="formData.payType == 1">
            <a-textarea v-model:value="formData.seller_prikey" :auto-size="{ minRows: 5 }" placeholder="请输入合作者私钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 合作伙伴支付宝公钥 -->
        <a-col :span="24">
          <a-form-item label="合作伙伴支付宝公钥" v-bind="validateInfos.alipay_pubkey" v-if="formData.payType == 1">
            <a-textarea
              v-model:value="formData.alipay_pubkey"
              :auto-size="{ minRows: 5 }"
              placeholder="请输入合作伙伴支付宝公钥"
              :disabled="disabled"
            />
          </a-form-item>
        </a-col>
        <!-- 开放平台支付宝公钥 -->
        <a-col :span="24">
          <a-form-item label="开放平台支付宝公钥" v-bind="validateInfos.alipay_pubkey2" v-if="formData.payType == 1">
            <a-textarea
              v-model:value="formData.alipay_pubkey2"
              :auto-size="{ minRows: 5 }"
              placeholder="请输入开放平台支付宝公钥"
              :disabled="disabled"
            />
          </a-form-item>
        </a-col>
        <!-- 汇付宝 -->
        <!-- 商户内码 -->
        <a-col :span="24">
          <a-form-item label="商户内码" v-bind="validateInfos.seller_id" v-if="formData.payType == 2">
            <a-input v-model:value="formData.seller_id" placeholder="请输入商户内码" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户密钥 -->
        <a-col :span="24">
          <a-form-item label="商户密钥" v-bind="validateInfos.seller_key" v-if="formData.payType == 2">
            <a-textarea v-model:value="formData.seller_key" :auto-size="{ minRows: 5 }" placeholder="请输入商户密钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 易宝 -->
        <!-- 商户编号 -->
        <a-col :span="24">
          <a-form-item label="商户编号" v-bind="validateInfos.seller_id" v-if="formData.payType == 3">
            <a-input v-model:value="formData.seller_id" placeholder="请输入商户编号" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户RSA公钥 -->
        <a-col :span="24">
          <a-form-item label="商户RSA公钥" v-bind="validateInfos.seller_pubkey" v-if="formData.payType == 3">
            <a-textarea v-model:value="formData.seller_pubkey" :auto-size="{ minRows: 5 }" placeholder="请输入商户RSA公钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 商户RSA私钥 -->
        <a-col :span="24">
          <a-form-item label="商户RSA私钥" v-bind="validateInfos.seller_prikey" v-if="formData.payType == 3">
            <a-textarea v-model:value="formData.seller_prikey" :auto-size="{ minRows: 5 }" placeholder="请输入商户RSA私钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 易宝RSA公钥 -->
        <a-col :span="24">
          <a-form-item label="易宝RSA公钥" v-bind="validateInfos.yeepay_pubkey" v-if="formData.payType == 3">
            <a-textarea v-model:value="formData.yeepay_pubkey" :auto-size="{ minRows: 5 }" placeholder="请输入易宝RSA公钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 爱贝 -->
        <!-- 应用ID -->
        <a-col :span="24">
          <a-form-item label="应用ID" v-bind="validateInfos.app_id" v-if="formData.payType == 4">
            <a-input v-model:value="formData.app_id" placeholder="请输入应用ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 应用私钥 -->
        <a-col :span="24">
          <a-form-item label="应用私钥" v-bind="validateInfos.seller_prikey" v-if="formData.payType == 4">
            <a-textarea v-model:value="formData.seller_prikey" :auto-size="{ minRows: 5 }" placeholder="请输入应用私钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 爱贝公钥 -->
        <a-col :span="24">
          <a-form-item label="爱贝公钥" v-bind="validateInfos.iapppay_pubkey" v-if="formData.payType == 4">
            <a-textarea v-model:value="formData.iapppay_pubkey" :auto-size="{ minRows: 5 }" placeholder="请输入爱贝公钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 现在支付 / 现在支付app-->
        <!-- 商户ID -->
        <a-col :span="24">
          <a-form-item label="商户ID" v-bind="validateInfos.app_id" v-if="formData.payType == 5 || formData.payType == 6">
            <a-input v-model:value="formData.app_id" placeholder="请输入商户ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户密钥 -->
        <a-col :span="24">
          <a-form-item label="商户密钥" v-bind="validateInfos.app_key" v-if="formData.payType == 5 || formData.payType == 6">
            <a-textarea v-model:value="formData.app_key" :auto-size="{ minRows: 5 }" placeholder="请输入商户密钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 微信 -->
        <!-- 应用ID -->
        <a-col :span="24">
          <a-form-item label="应用ID" v-bind="validateInfos.app_id" v-if="formData.payType == 7">
            <a-input v-model:value="formData.app_id" placeholder="请输入应用ID" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户号 -->
        <a-col :span="24">
          <a-form-item label="商户号" v-bind="validateInfos.mch_id" v-if="formData.payType == 7">
            <a-input v-model:value="formData.mch_id" placeholder="请输入商户号" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- API V3密钥 -->
        <a-col :span="24">
          <a-form-item label="API V3密钥" v-bind="validateInfos.api_key" v-if="formData.payType == 7">
            <a-input v-model:value="formData.api_key" placeholder="请输入API V3密钥" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户API证书序列号 -->
        <a-col :span="24">
          <a-form-item label="商户API证书序列号" v-bind="validateInfos.serial_no" v-if="formData.payType == 7">
            <a-input v-model:value="formData.serial_no" placeholder="请输入商户API证书序列号" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <!-- 商户私钥 -->
        <a-col :span="24">
          <a-form-item label="商户私钥" v-bind="validateInfos.private_key" v-if="formData.payType == 7">
            <a-textarea v-model:value="formData.private_key" :auto-size="{ minRows: 5 }" placeholder="请输入商户私钥" :disabled="disabled" />
          </a-form-item>
        </a-col>
        <!-- 回调url -->
        <a-col :span="24">
          <a-form-item label="回调url" v-bind="validateInfos.notify_url" v-if="formData.payType != null">
            <a-input v-model:value="formData.notify_url" placeholder="请输入回调url" :disabled="disabled"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label="备注" v-bind="validateInfos.note">
            <a-input v-model:value="formData.note" placeholder="请输入备注" :disabled="disabled"></a-input>
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
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../OpPayVendor.api';
  import { options } from '../OpPayVendor.data';
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
    payType: undefined,
    payVendorName: '',   
    payVendorConf: '',   
    note: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = {
    payType: [{ required: true, message: '请输入支付类型!'},],
    payVendorName: [{ required: true, message: '请输入支付渠道名称!'},],
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
      // 表单部分属性重置
      formData.app_id = "";
      formData.seller_id = "";
      formData.seller_email = "";
      formData.seller_pubkey = "";
      formData.seller_prikey = "";
      formData.alipay_pubkey = "";
      formData.alipay_pubkey2 = "";
      formData.seller_key = "";
      formData.yeepay_pubkey = "";
      formData.iapppay_pubkey = "";
      formData.app_key = "";
      formData.notify_url = "";
      //赋值
      let vendorData = record;
      if(vendorData.payVendorConf != ""){
        let payConf = JSON.parse(vendorData.payVendorConf);
        //表单赋值
        if(vendorData.payType == 1){
            // 支付宝
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.app_id = payConf.app_id;
            formData.seller_id = payConf.seller_id;
            formData.seller_email = payConf.seller_email;
            formData.seller_pubkey = payConf.seller_pubkey;
            formData.seller_prikey = payConf.seller_prikey;
            formData.alipay_pubkey = payConf.alipay_pubkey;
            formData.alipay_pubkey2 = payConf.alipay_pubkey2;
        }else if(vendorData.payType == 2) {
            // 汇付宝
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.seller_id = payConf.seller_id;
            formData.seller_key = payConf.seller_key;
        } else if(vendorData.payType == 3) {
            // 易宝
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.seller_id = payConf.seller_id;
            formData.seller_pubkey = payConf.seller_pubkey;
            formData.seller_prikey = payConf.seller_prikey;
            formData.yeepay_pubkey = payConf.yeepay_pubkey;
        } else if(vendorData.payType == 4) {
            // 爱贝
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.app_id = payConf.app_id;
            formData.seller_prikey = payConf.seller_prikey;
            formData.iapppay_pubkey = payConf.iapppay_pubkey;
        } else if(vendorData.payType == 5 || vendorData.payType == 6) {
            // 现在支付
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.app_id = payConf.app_id;
            formData.app_key = payConf.app_key;
        } else if(vendorData.payType == 7) {
            // 微信
            formData.id = vendorData.id;
            formData.payType = vendorData.payType;
            formData.payVendorName = vendorData.payVendorName;
            formData.note = vendorData.note;
            formData.app_id = payConf.app_id;
            formData.mch_id = payConf.mch_id;
            formData.api_key = payConf.api_key;
            formData.serial_no = payConf.serial_no;
            formData.private_key = payConf.private_key;
        }
        formData.notify_url = payConf.notify_url;
      }
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
    // 支付参数配置
    let payVendorConf = {};
    // 将部分数据转为json
    if(model.payType == 1) {
        // 支付宝
        payVendorConf.app_id = model.app_id;
        payVendorConf.seller_id = model.seller_id;
        payVendorConf.seller_email = model.seller_email;
        payVendorConf.seller_pubkey = model.seller_pubkey;
        payVendorConf.seller_prikey = model.seller_prikey;
        payVendorConf.alipay_pubkey = model.alipay_pubkey;
        payVendorConf.alipay_pubkey2 = model.alipay_pubkey2;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 2) {
        // 汇付宝
        payVendorConf.seller_id = model.seller_id;
        payVendorConf.seller_key = model.seller_key;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 3) {
        // 易宝
        payVendorConf.seller_id = model.seller_id;
        payVendorConf.seller_pubkey = model.seller_pubkey;
        payVendorConf.seller_prikey = model.seller_prikey;
        payVendorConf.yeepay_pubkey = model.yeepay_pubkey;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 4) {
        // 爱贝
        payVendorConf.app_id = model.app_id;
        payVendorConf.seller_prikey = model.seller_prikey;
        payVendorConf.iapppay_pubkey = model.iapppay_pubkey;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 5) {
        // 现在支付
        payVendorConf.app_id = model.app_id;
        payVendorConf.app_key = model.app_key;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 6) {
        // 现在支付app
        payVendorConf.app_id = model.app_id;
        payVendorConf.app_key = model.app_key;
        payVendorConf.notify_url = model.notify_url;
    } else if(model.payType == 7) {
        // 微信
        payVendorConf.app_id = model.app_id;
        payVendorConf.mch_id = model.mch_id;
        payVendorConf.api_key = model.api_key;
        payVendorConf.serial_no = model.serial_no;
        payVendorConf.private_key = model.private_key;
        payVendorConf.notify_url = model.notify_url;
    }
    // 处理最终的数据
    let data = {
        id: model.id,
        payType: model.payType,
        payVendorName: model.payVendorName,
        payVendorConf: JSON.stringify(payVendorConf),
        note: model.note
    };
    await saveOrUpdate(data, isUpdate.value)
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
