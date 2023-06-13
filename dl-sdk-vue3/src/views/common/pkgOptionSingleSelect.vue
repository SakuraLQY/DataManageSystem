<template>
    <a-col :span="24">
        <a-form-item label="渠道游戏包" v-bind="validateInfos.pkgIdList">
            <a-cascader
            v-model:value="formData.pkgIdList"
            :options="treeData"
            show-all-levels="false"
            placeholder="Please select"
            :disabled="disabled"
        />
        </a-form-item>
    </a-col>
</template>

<script lang="ts"  setup>
  import { ref, nextTick, reactive, watch, defineExpose} from 'vue';
  import { Form } from 'ant-design-vue';
  import { getOptionList } from '/@/views/operationTool/gameManager/opPkg/OpPkg.api';
import func from '../../../vue-temp/vue-editor-bridge';

  const formData = reactive<Record<string, any>>({
         pkgIdList: undefined,
     })
  const treeData = ref([]);
  let emitFn = defineEmits(['handlerModal']);
  const useForm = Form.useForm;
  const validatorRules = {
    pkgIdList: [{ required: true, message: '请选择渠道游戏包!'},],
  };
  const props = defineProps({
    // v-model:value
    initData: {
        type: Array,
        default: () => []
      }
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });
  getList();
  function getList() {
    getOptionList({type:0, channelId:props.initData}).then((res: any)=>{
        treeData.value = res
      })
  }


  // 监听数据变化 传值给父组件
  watch(formData, () => {
        emitFn('handlerModal', formData);
        }
    ) 

  function edit(record) {
    nextTick(() => {
      formData.pkgIdList = record.pkgIdList;
    });
  }
  
  function reload() {
    formData.pkgIdList = undefined;
  }
  defineExpose({
    reload,
    edit,
  });
</script>