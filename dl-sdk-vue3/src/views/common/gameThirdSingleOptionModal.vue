<template>
    <a-col>
        <a-form-item label="游戏名" :help="gameIdHelp">
          <a-select v-model:value="queryParam.gameId" optionFilterProp="label" show-search placeholder="请选择游戏名" @blur="checkGameId" @change="changeGame" allowClear>
            <a-select-option
                v-for="(gameItem,index) in faList"
                :key="index"
                :value="index"
                :label="gameItem.gameName + '(' + index + ')'"
            >
            {{gameItem.gameName + '(' + index + ')'}}
            </a-select-option>
          </a-select>
        </a-form-item>
        </a-col>
        <a-col>
        <a-form-item label="子游戏名" :help="subGameIdHelp">
            <a-select v-model:value="queryParam.subGameId" optionFilterProp="label"  show-search allowClear placeholder="请选择子游戏名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined" @blur="checkSubGameId" @change="changeSubGame">
            <a-select-option
                v-for="(gameItem,index) in faList[queryParam.gameId]?.map"
                :key="index"
                :value="index"
                :label="gameItem.gameName + '(' + index + ')'"
            >
            {{gameItem.gameName + '(' + index + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.subGameId" optionFilterProp="label" placeholder="请选择子游戏名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined" @blur="checkSubGameId" @change="changeSubGame">
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
    <a-col>
        <a-form-item label="渠道游戏包名" :help="pkgIdHelp">
            <a-select v-model:value="queryParam.pkgId" optionFilterProp="label" show-search allowClear placeholder="请选择渠道游戏包名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined && queryParam.subGameId !=='' && queryParam.subGameId !==undefined" @blur="checkPkgId">
            <a-select-option
                v-for="(gameItem,index) in faList[queryParam.gameId]?.map[queryParam.subGameId].list"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.gameName + '(' + gameItem.id + ')'"
            >
            {{gameItem.gameName + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.pkgId" placeholder="请选择渠道游戏包名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined || queryParam.subGameId ==='' || queryParam.subGameId ===undefined"  @blur="checkPkgId">
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
    import { ref, reactive, watch, defineExpose, nextTick} from 'vue';
    import { queryList } from '/@/views/operationTool/gameManager/opPkg/OpPkg.api';
    import { Form } from 'ant-design-vue';
     const queryParam = ref<any>({});

     let emitFn = defineEmits(['handler'])
     let faList = ref([]);

     getOptionList();

     function changeGame(){
        queryParam.value.subGameId = undefined;
        queryParam.value.pkgId = undefined;
     }
     function changeSubGame(){
        queryParam.value.pkgId = undefined;
     }
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        queryList().then((res: any)=>{
        faList.value = res
        })
    };
    // 提示
    const gameIdHelp = ref("");
    const subGameIdHelp = ref("");
    const pkgIdHelp = ref("");

    function checkGameId(){
        if(!queryParam.value.gameId){
            gameIdHelp.value = "请选择游戏";
        }else{
            gameIdHelp.value = "";
        }
    }

    function checkSubGameId(){
        if(!queryParam.value.subGameId){
            subGameIdHelp.value = "请选择子游戏";
        }else{
            subGameIdHelp.value = "";
        }
    }

    function checkPkgId(){
        if(!queryParam.value.subGameId){
            pkgIdHelp.value = "请选择一级游戏包";
        }else{
            pkgIdHelp.value = "";
        }
    }

    function edit(record) {
        nextTick(() => {
            if(record.gameId) {
                queryParam.value.gameId = record.gameId + "";
            }else{
                queryParam.value.gameId = undefined;
            }
            if(record.subGameId) {
                queryParam.value.subGameId = record.subGameId + "";
            }else{
                queryParam.value.subGameId = undefined;
            }
            if(record.pkgId) {
                queryParam.value.pkgId = record.pkgId;
            }else{
                queryParam.value.pkgId = undefined;
            }
            debugger
        });
  }

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
    queryParam.value.pkgId = undefined;
  }
    /**
     * 展示提示的方法 解决父组件调用validate无法显示红色提示的问题
     */
    function check(){
        if(!queryParam.value.gameId){
            gameIdHelp.value = "请选择游戏";
        }else{
            gameIdHelp.value = "";
        }
        if(!queryParam.value.subGameId){
            subGameIdHelp.value = "请选择子游戏";
        }else{
            subGameIdHelp.value = "";
        }
        if(!queryParam.value.subGameId){
            pkgIdHelp.value = "请选择一级游戏包";
        }else{
            pkgIdHelp.value = "";
        }
    }
    defineExpose({
        reload,
        check,
        edit
    })
</script>
<style>
.ant-form-item-explain {
    color: red;
  }
</style>
