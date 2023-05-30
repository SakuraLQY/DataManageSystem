<template>
    <a-col :lg="8">
      <a-form-item label="渠道游戏包">
        <a-tree-select
          v-model:value="queryParam.pkgIdList"
          style="width: 100%"
          :tree-data="treeData"
          tree-checkable
          allow-clear
          :show-checked-strategy="SHOW_PARENT"
          search-placeholder="Please select"
        />
      </a-form-item>
    </a-col>
</template>

<script lang="ts"  setup>
  import { ref, nextTick, reactive, watch, defineExpose} from 'vue';
  import { Form, TreeSelect } from 'ant-design-vue';
  import { getOptionList } from '/@/views/operationTool/gameManager/opPkg/OpPkg.api';
import func from '../../../vue-temp/vue-editor-bridge';

  const queryParam = reactive<Record<string, any>>({
         pkgIdList: undefined,
     })
  const treeData = ref([]);
  const SHOW_PARENT = TreeSelect.SHOW_PARENT;
  let emitFn = defineEmits(['handler']);
 
  getList();
  function getList() {
    getOptionList({type:1}).then((res: any)=>{
        treeData.value = res
      })
  }

  // 监听数据变化 传值给父组件
  watch(queryParam, () => {
        emitFn('handler', queryParam);
        }
    ) 

  function reload() {
    queryParam.pkgIdList = undefined;
  }
  defineExpose({
    reload,
  });
</script>