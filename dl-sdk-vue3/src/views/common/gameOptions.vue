<template>

    <a-select v-model:value="queryParam.gameId" optionFilterProp="label" show-search  placeholder="请选择游戏名" allowClear>
    <a-select-option
        v-for="(gameItem,index) in faList"
        :key="index"
        :value="gameItem.id"
        :label="gameItem.gameName + '(' + gameItem.id + ')'"
    >
    {{gameItem.gameName  + '(' + gameItem.id + ')'}}
    </a-select-option>
    </a-select>
    /
    <a-select v-model:value="queryParam.subGameId" optionFilterProp="label" show-search allowClear placeholder="请选择子游戏名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined">
    <a-select-option
        v-for="(gameItem,index) in faList[queryParam.gameId].list"
        :key="index"
        :value="gameItem.id"
        :label="gameItem.gameName + '(' + gameItem.id + ')'"
    >
    {{gameItem.gameName  + '(' + gameItem.id + ')'}}
    </a-select-option>
    </a-select>
    <a-select v-model:value="queryParam.subGameId" placeholder="请选择子游戏名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined">
    <a-select-option
        v-for="(gameItem,index) in []"
        :key="index"
        :value="gameItem.id"
    >
    {{gameItem.gameName}}
    </a-select-option>
    </a-select>

</template>

<script lang="ts"  setup>
    import { ref, reactive, watch, defineExpose} from 'vue';
    import { optionList } from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';

     const queryParam = ref<any>({});
     let emitFn = defineEmits(['handler'])
     let faList = ref([]);

     getOptionList();
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        optionList({status:0}).then((res: any)=>{
        faList.value = res
        })
    };

  //监听下拉框变化
  watch(
      () => queryParam.value.gameId,
      val => {
          queryParam.value.subGameId = undefined;
      },
    );

   // 监听数据变化 传值给父组件
  watch(queryParam.value, () => {
        emitFn('handler', queryParam.value);
        }
    )
  const fn = () => {
  // 第一个参数是 自定义事件名称  第二个参数是 传过去的值
  emitFn('handler', queryParam.value);
  }
  function reload() {
    queryParam.value.gameId = undefined;
    queryParam.value.subGameId = undefined;
  }
defineExpose({
    reload
})
</script>
