<template>
  <a-modal wrapClassName="full-modal" :title="title" width="100%" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
    <OpJrttSiteForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></OpJrttSiteForm>
  </a-modal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import OpJrttSiteForm from './OpJrttSiteForm.vue'
  
  const title = ref<string>('');
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success']);

  /**
   * 新增
   */
  function add() {
    title.value = '新增';
    visible.value = true;
    nextTick(() => {
      registerForm.value.add();
    });
  }
  
  /**
   * 编辑
   * @param record
   */
  function edit(record) {
    title.value = disableSubmit.value ? '详情' : '编辑';
    visible.value = true;
    nextTick(() => {
      registerForm.value.edit(record);
    });
  }
  
  /**
   * 确定按钮点击事件
   */
  function handleOk() {
    registerForm.value.submitForm();
  }

  /**
   * form保存回调事件
   */
  function submitCallback() {
    handleCancel();
    emit('success');
  }

  /**
   * 取消按钮回调事件
   */
  function handleCancel() {
    visible.value = false;
  }

  defineExpose({
    add,
    edit,
    disableSubmit,
  });
</script>

<style lang="less">
  /**隐藏样式-modal确定按钮 */
  .jee-hidden {
    display: none !important;
  }
  .full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }
  }
</style>
