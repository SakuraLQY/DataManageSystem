<template>
  <a-modal :title="title" :width="width" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
    <AddSecondAccountForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></AddSecondAccountForm>
  </a-modal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import AddSecondAccountForm from './AddSecondAccountForm.vue';
  
  const title = ref<string>('添加二级账号');
  const width = ref<number>(1000);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success2']);

  
  function fillOptions(record){
    nextTick(() =>{
      registerForm.value.fillOptions(record);
    })
  }
   

  /**
   * 新增
   */
  function add(obj) {
    visible.value = true;
    nextTick(() => {
      registerForm.value.add(obj);
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
    emit('success2');
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
