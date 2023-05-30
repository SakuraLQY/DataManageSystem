<template>
  <BasicModal v-bind="$attrs" @register="registerModal" destroyOnClose :title="title" :width="800" @ok="handleSubmit">
      <BasicForm @register="registerForm"/>
  </BasicModal>
</template>

<script lang="ts" setup>
    import {ref, computed, unref} from 'vue';
    import {BasicModal, useModalInner} from '/@/components/Modal';
    import {BasicForm, useForm} from '/@/components/Form/index';
    import {editPlatformCurrencyFormSchema, editPassFormSchema} from '../OpUser.data';
    import { updateAll} from '../OpUser.api';
    // Emits声明
    const emit = defineEmits(['register','success']);
    const isUpdate = ref(true);
    let idOne;
    //表单配置
    const [registerForm, {setProps,resetFields, setFieldsValue, validate}] = useForm({
        //labelWidth: 150,
        schemas: computed(() => (!unref(isUpdate) ? editPlatformCurrencyFormSchema : editPassFormSchema)),
        showActionButtonGroup: false,
        baseColProps: {span: 24}
    });
    //表单赋值
    const [registerModal, {setModalProps, closeModal}] = useModalInner(async (data) => {
        //重置表单
        await resetFields();
        setModalProps({confirmLoading: false,showCancelBtn:!!data?.showFooter,showOkBtn:!!data?.showFooter});
        isUpdate.value = !!data?.isUpdate;
        idOne = data.record.id
        // if (unref(isUpdate)) {
        //     //表单赋值
        //     await setFieldsValue({
        //         ...data.record,
        //     });
        // }
        // 隐藏底部时禁用整个表单
       setProps({ disabled: !data?.showFooter })
    });
    //设置标题
    const title = computed(() => (!unref(isUpdate) ? '修改余额' : '修改密码'));
    //表单提交事件
    async function handleSubmit(v) {
        try {
            let values = await validate();
            values['id'] = idOne
            setModalProps({confirmLoading: true});
            //提交表单
            await updateAll(values, isUpdate.value);
            //关闭弹窗
            closeModal();
            //刷新列表
            emit('success');
        } finally {
            setModalProps({confirmLoading: false});
        }
    }
</script>

<style lang="less" scoped>
	/** 时间和数字输入框样式 */
  :deep(.ant-input-number){
		width: 100%
	}

	:deep(.ant-calendar-picker){
		width: 100%
	}
</style>