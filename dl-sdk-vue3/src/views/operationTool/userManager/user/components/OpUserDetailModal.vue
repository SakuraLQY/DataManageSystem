<template>
    <BasicModal :title="title" :width="width" :visible="visible" :body-style="bodystyle" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': true } }" @cancel="handleCancel" cancelText="关闭">
      <OpUserDetailForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></OpUserDetailForm>
    </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import { BasicModal } from '/@/components/Modal';
  import OpUserDetailForm from './OpUserDetailForm.vue'
  
  const title = ref<string>('详情');
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success']);
  const bodystyle = {
    height: '700px',
    overflow: 'hidden',
    overflowY: 'scroll',
  }
  /**
   * 编辑
   * @param record
   */
  function edit(record) {
    visible.value = true;
    record.realNameInfo = record.realName + '(' + record.realNumber + ')';
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
    edit,
    disableSubmit,
  });
</script>

<style>
  /**隐藏样式-modal确定按钮 */
  .jee-hidden {
    display: none !important;
  }
</style>
