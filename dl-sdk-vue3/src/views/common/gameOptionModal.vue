<template>
    <span style="color:red;margin-right:6px;margin-left:50px;">*</span>
            <span style="white-space: normal">游戏/子游戏 : </span>
            <a-form-item style="margin-left:10px" v-bind="validateInfos.gameId">
              <a-select v-model:value="formData.gameId" optionFilterProp="label" show-search style="width:190px;"   placeholder="请选择游戏名" @change="changeSubGame" allowClear>
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="gameItem.id"
                  :label="gameItem.gameName + '(' + gameItem.id + ')'"
                >
                {{gameItem.gameName + '(' + gameItem.id + ')'}}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item>
              <span style="margin-left:10px;margin-right:10px">/</span>
            </a-form-item>
            <a-form-item  style="width:40%">
	            <a-select v-model:value="formData.subGameId" optionFilterProp="label" show-search style="width:100%"  allowClear placeholder="请选择子游戏名" v-if="formData.gameId !=='' && formData.gameId !==undefined">
                <a-select-option
                  v-for="(gameItem,index) in faList[formData.gameId]?.list"
                  :key="index"
                  :value="gameItem.id"
                  :label="gameItem.gameName + '(' + gameItem.id + ')'"
                >
                {{gameItem.gameName + '(' + gameItem.id + ')'}}
                </a-select-option>
              </a-select>
              <a-select v-model:value="formData.subGameId" style="width:100%"  placeholder="请选择子游戏名" v-if="formData.gameId ==='' || formData.gameId ===undefined">
                <a-select-option
                  v-for="(gameItem,index) in []"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
            </a-form-item>
</template>

<script lang="ts"  setup>
    import { ref, nextTick, reactive, defineExpose, watch } from 'vue';
    import { optionList } from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';
    import { Form } from 'ant-design-vue';

     const formData = reactive<Record<string, any>>({
         subGameId: '',
         gameId: '',
     })
     let emitFn = defineEmits(['handlerModal']);
     let faList = ref([]);
    const useForm = Form.useForm;
     getOptionList();
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        optionList({status:0}).then((res: any)=>{
        faList.value = res
        })
    };

    function changeSubGame(){
      formData.subGameId = "";
    }

  const validatorRules = {
      gameId: [{ required: true, message: '请选择游戏ID!'},],
  }
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: true });

  function edit(record) {
    nextTick(() => {
      formData.gameId = record.gameId;
      formData.subGameId = record.subGameId;
      if(record.subGameId === 0) {
        formData.subGameId = undefined;
      }
    });
  }
   // 监听数据变化 传值给父组件
  watch(formData, () => {
        emitFn('handlerModal', formData);
        }
    )

//   const fn = () => {
//   // 第一个参数是 自定义事件名称  第二个参数是 传过去的值
//   emitFn('handlerModal', formData);
// }

function reload() {
    formData.gameId = undefined;
    formData.subGameId = undefined;
}
defineExpose({
    reload,
    edit
  });
</script>
