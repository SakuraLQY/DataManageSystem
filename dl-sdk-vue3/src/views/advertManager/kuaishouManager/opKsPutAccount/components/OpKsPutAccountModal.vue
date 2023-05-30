<template>
  <a-modal :title="title" :width="width" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
    <OpKsPutAccountForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></OpKsPutAccountForm>
  </a-modal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import OpKsPutAccountForm from './OpKsPutAccountForm.vue';
  
  const title = ref<string>('');
  const width = ref<number>(1000);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success']);

  
  function fillOptions(record){
    nextTick(() =>{
      registerForm.value.fillOptions(record);
    })
  }
   

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
    registerForm.value.gameReload();
  }

  defineExpose({
    add,
    edit,
    disableSubmit,
    fillOptions
  });
</script>

<style>
  /**隐藏样式-modal确定按钮 */
  .jee-hidden {
    display: none !important;
  }
</style>
