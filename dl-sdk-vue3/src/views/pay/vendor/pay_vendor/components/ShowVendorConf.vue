<template>
  <div>
    <a-modal v-model:visible="visible" title="支付参数详情" @ok="handleOk" width="1000px" style="top: 50px">
      <!-- 支付宝 -->
      <a-descriptions title="支付宝参数" bordered layout="vertical" :column="24" v-if="payType == 1" style="margin: 20px">
        <a-descriptions-item label="应用ID" :span="8">{{ confData.app_id }}</a-descriptions-item>
        <a-descriptions-item label="合作者ID" :span="8">{{ confData.seller_id }}</a-descriptions-item>
        <a-descriptions-item label="合作者账号" :span="8">{{ confData.seller_email }}</a-descriptions-item>
        <a-descriptions-item label="合作者公钥" :span="24">{{ confData.seller_pubkey }}</a-descriptions-item>
        <a-descriptions-item label="合作者私钥" :span="24">{{ confData.seller_prikey }}</a-descriptions-item>
        <a-descriptions-item label="合作伙伴支付宝公钥" :span="24">{{ confData.alipay_pubkey }}</a-descriptions-item>
        <a-descriptions-item label="开放平台支付宝公钥" :span="24">{{ confData.alipay_pubkey2 }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 汇付宝 -->
      <a-descriptions title="汇付宝参数" bordered layout="vertical" :column="24" v-if="payType == 2" style="margin: 20px">
        <a-descriptions-item label="商户内码" :span="24">{{ confData.seller_id }}</a-descriptions-item>
        <a-descriptions-item label="商户密钥" :span="24">{{ confData.seller_key }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 易宝 -->
      <a-descriptions title="易宝参数" bordered layout="vertical" :column="24" v-if="payType == 3" style="margin: 20px">
        <a-descriptions-item label="商户编号" :span="24">{{ confData.seller_id }}</a-descriptions-item>
        <a-descriptions-item label="商户RSA公钥" :span="24">{{ confData.seller_pubkey }}</a-descriptions-item>
        <a-descriptions-item label="商户RSA私钥" :span="24">{{ confData.seller_prikey }}</a-descriptions-item>
        <a-descriptions-item label="易宝RSA公钥" :span="24">{{ confData.yeepay_pubkey }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 爱贝 -->
      <a-descriptions title="爱贝参数" bordered layout="vertical" :column="24" v-if="payType == 4" style="margin: 20px">
        <a-descriptions-item label="应用ID" :span="24">{{ confData.app_id }}</a-descriptions-item>
        <a-descriptions-item label="应用私钥" :span="24">{{ confData.seller_prikey }}</a-descriptions-item>
        <a-descriptions-item label="爱贝公钥" :span="24">{{ confData.iapppay_pubkey }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 现在支付 -->
      <a-descriptions title="现在支付参数" bordered layout="vertical" :column="24" v-if="payType == 5" style="margin: 20px">
        <a-descriptions-item label="商户ID" :span="24">{{ confData.app_id }}</a-descriptions-item>
        <a-descriptions-item label="商户密钥" :span="24">{{ confData.app_key }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 现在支付app -->
      <a-descriptions title="现在支付app参数" bordered layout="vertical" :column="24" v-if="payType == 6" style="margin: 20px">
        <a-descriptions-item label="商户ID" :span="24">{{ confData.app_id }}</a-descriptions-item>
        <a-descriptions-item label="商户密钥" :span="24">{{ confData.app_key }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 微信 -->
      <a-descriptions title="微信支付参数" bordered layout="vertical" :column="24" v-if="payType == 7" style="margin: 20px">
        <a-descriptions-item label="应用ID" :span="12">{{ confData.app_id }}</a-descriptions-item>
        <a-descriptions-item label="商户号" :span="12">{{ confData.mch_id }}</a-descriptions-item>
        <a-descriptions-item label="API V3密钥" :span="12">{{ confData.api_key }}</a-descriptions-item>
        <a-descriptions-item label="商户API证书序列号" :span="12">{{ confData.serial_no }}</a-descriptions-item>
        <a-descriptions-item label="商户私钥" :span="24">{{ confData.private_key }}</a-descriptions-item>
        <a-descriptions-item label="回调url">{{ confData.notify_url }}</a-descriptions-item>
      </a-descriptions>
      <!-- 自定义页脚 -->
      <template #footer>
        <a-button type="primary" @click="handleOk">关闭</a-button>
      </template>
    </a-modal>
  </div>
</template>
<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
  setup() {
    const visible = ref<boolean>(false);
    const payType = ref();
    const confData = ref();
    const showModal = () => {
      visible.value = true;
    };

    const handleOk = (e: MouseEvent) => {
      visible.value = false;
    };
    return {
      visible,
      showModal,
      handleOk,
      payType,
      confData
    };
  },
});
</script>

