<template>
  <BasicModal :title="title" :maskClosable="false" :width="width" :body-style="bodystyle" :visible="visible" @ok="handleOk" :okButtonProps="{ class: { 'jee-hidden': disableSubmit } }" @cancel="handleCancel" cancelText="关闭">
    <OpSubGameForm ref="registerForm" @ok="submitCallback" :formDisabled="disableSubmit" :formBpm="false"></OpSubGameForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose } from 'vue';
  import { BasicModal } from '/@/components/Modal';
  import OpSubGameForm from './OpSubGameForm.vue';
  import { list } from '/@/views/operationTool/gameManager/opGame/OpGame.api';
  
  const title = ref<string>('');
  const width = ref<number>(800);
  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  const registerForm = ref();
  const emit = defineEmits(['register', 'success']);
  let gameNameList = ref([])
  const bodystyle = {
    height: '600px',
    overflow: 'hidden',
    overflowY: 'scroll',
  }
  getList() 
  function getList() {
    list({id:''}).then((res: any)=>{
      gameNameList.value = res['records']
    })
  }

  /**
   * 新增
   */
  function add() {
    getList();
    title.value = '新增';
    visible.value = true;
    nextTick(() => {
      registerForm.value.add(gameNameList.value);
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
      registerForm.value.edit(record,gameNameList.value);
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
