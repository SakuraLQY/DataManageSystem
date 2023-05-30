<template>
  <BasicModal :title="title" :maskClosable="false" :width="width" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
    <OpPrivacyPolicyForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></OpPrivacyPolicyForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import { BasicModal } from '/@/components/Modal';
  import OpPrivacyPolicyForm from './OpPrivacyPolicyForm.vue';
  import { queryAll } from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';
  
  const title = ref<string>('');
  const width = ref<number>(800);
  let list = ref([]);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success']);
  getList();
  function getList() {
    queryAll({}).then((res: any)=>{
      list.value = res
    })
  }
  
  /**
   * 新增
   */
  function add() {
    title.value = '新增';
    visible.value = true;
    console.log('2-->'+list.value);
    nextTick(() => {
      registerForm.value.add(list.value,false);
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
      registerForm.value.edit(record,list.value,true);
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

<style>
  /**隐藏样式-modal确定按钮 */
  .jee-hidden {
    display: none !important;
  }
</style>
