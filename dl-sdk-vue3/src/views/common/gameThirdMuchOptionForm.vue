<template>
    <a-col :lg="8">
        <a-form-item label="游戏名">
          <a-select v-model:value="queryParam.gameId" optionFilterProp="label" mode="multiple" show-search  placeholder="请选择游戏名" allowClear>
            <a-select-option
                v-for="(gameItem,index) in resList[1]"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.gameName + '(' + gameItem.id + ')'"
            >
            {{gameItem.gameName + '(' + gameItem.id + ')'}}
            </a-select-option>
          </a-select>
        </a-form-item>
        </a-col>
        <a-col :lg="8">
        <a-form-item label="子游戏名">
            <a-select v-model:value="queryParam.subGameId" optionFilterProp="label" mode="multiple" show-search allowClear placeholder="请选择子游戏名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined">
            <a-select-option
                v-for="(gameItem,index) in subGameList"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.gameName + '(' + gameItem.id + ')'"
            >
            {{gameItem.gameName + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.subGameId" placeholder="请选择子游戏名" v-if="queryParam.gameId === [] || queryParam.gameId ===undefined">
            <a-select-option
                v-for="(gameItem,index) in []"
                :key="index"
                :value="gameItem.id"
            >
            {{gameItem.gameName}}
            </a-select-option>
            </a-select>
        </a-form-item>
    </a-col>
    <a-col :lg="8">
        <a-form-item label="渠道游戏包名">
            <a-select v-model:value="queryParam.pkgId" optionFilterProp="label" mode="multiple" show-search allowClear placeholder="请选择渠道游戏包名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined && queryParam.subGameId !=='' && queryParam.subGameId !==undefined">
            <a-select-option
                v-for="(gameItem,index) in pkgList"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.gameName + '(' + gameItem.id + ')'"
            >
            {{gameItem.gameName + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.pkgId" placeholder="请选择渠道游戏包名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined || queryParam.subGameId ==='' || queryParam.subGameId ===undefined">
            <a-select-option
                v-for="(gameItem,index) in []"
                :key="index"
                :value="gameItem.id"
            >
            {{gameItem.gameName}}
            </a-select-option>
            </a-select>
        </a-form-item>
    </a-col>
</template>

<script lang="ts"  setup>
    import { ref, reactive, watch, defineExpose} from 'vue';
    import { gameAndSubGameAndPkgList } from '/@/views/operationTool/gameManager/opSubGame/OpSubGame.api';
    import { forEach } from '../../utils/helper/treeHelper';

     const queryParam = ref<any>({});

     let emitFn = defineEmits(['handler'])
     let resList = ref([]);
     let subGameList = ref([]);
     let pkgList = ref([]);

     getOptionList();
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        gameAndSubGameAndPkgList().then((res: any)=>{
            resList.value = res
        })
    };

  //监听下拉框变化
  watch(
      () => queryParam.value.gameId,
      val => {
        subGameList.value = [];
        if(val.length === 0) {
        queryParam.value.subGameId = [];
        queryParam.value.pkgId = [];
        }else {
        val.forEach(element => {
            if(resList.value[1][element].list !== null) {
                resList.value[1][element].list.forEach(element2 => {
                    subGameList.value.push(element2)
                });
            }
        });
        }
      },
    );

    //监听下拉框变化
  watch(
      () => queryParam.value.subGameId,
      val => {
          pkgList.value = [];
          if(val.length === 0) {
              queryParam.value.pkgId = [];
          }else {
            val.forEach(element => {
                if(resList.value[2][element].list !== null) {
                    resList.value[2][element].list.forEach(element2 => {
                        pkgList.value.push(element2)
                    });
                }
            });
          }
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
    queryParam.value.gameId = [];
    queryParam.value.subGameId = [];
    queryParam.value.pkgId = [];
  }
defineExpose({
    reload
})
</script>
