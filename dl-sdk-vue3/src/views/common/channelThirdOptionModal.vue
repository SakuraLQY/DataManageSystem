<template>
    <a-col>
        <a-form-item label="渠道类型" :help="channelTypeIdHelp">
          <a-select v-model:value="queryParam.channelTypeId" optionFilterProp="label"  show-search  placeholder="请选择渠道类型" @blur="checkChannelTypeId" @change="channelTypeChange" allowClear>
            <a-select-option
                v-for="(gameItem,index) in faList"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
          </a-select>
        </a-form-item>
        </a-col>
        <a-col>
        <a-form-item label="渠道" :help="channelIdHelp">
            <a-select v-model:value="queryParam.channelId" optionFilterProp="label" show-search allowClear placeholder="请选择渠道" v-if="queryParam.channelTypeId !=='' && queryParam.channelTypeId !==undefined" @blur="checkChannelId" @change="channelChange">
            <a-select-option
                v-for="(gameItem,index) in faList[queryParam.channelTypeId].map"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.channelId" placeholder="请选择渠道" v-if="queryParam.channelTypeId ==='' || queryParam.channelTypeId ===undefined" @blur="checkChannelId" @change="channelChange">
            <a-select-option
                v-for="(gameItem,index) in []"
                :key="index"
                :value="gameItem.id"
            >
            {{gameItem.name}}
            </a-select-option>
            </a-select>
        </a-form-item>
    </a-col>
    <a-col>
        <a-form-item label="渠道子账号" :help="channelSubAccountIdHelp">
            <a-select v-model:value="queryParam.channelSubAccountId" optionFilterProp="label" show-search allowClear placeholder="请选择渠道子账号" v-if="queryParam.channelTypeId !=='' && queryParam.channelTypeId !==undefined && queryParam.channelId !=='' && queryParam.channelId !==undefined" @blur="checkChannelSubAccountId">
            <a-select-option
                v-for="(gameItem,index) in faList[queryParam.channelTypeId].map[queryParam.channelId].list"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.channelSubAccountId" placeholder="请选择渠道子账号" v-if="queryParam.channelTypeId ==='' || queryParam.channelTypeId ===undefined || queryParam.channelId ==='' || queryParam.channelId ===undefined" @blur="checkChannelSubAccountId">
            <a-select-option
                v-for="(gameItem,index) in []"
                :key="index"
                :value="gameItem.id"
            >
            {{gameItem.name}}
            </a-select-option>
            </a-select>
        </a-form-item>
    </a-col>
</template>

<script lang="ts"  setup>
    import { ref, reactive, watch, defineExpose, nextTick} from 'vue';
    import { queryList } from '/@/views/advertManager/channelManager/opChannelSubAccount/OpChannelSubAccount.api';

     const queryParam = ref<any>({});
     let emitFn = defineEmits(['handler'])
     let faList = ref([]);

     getOptionList();
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        queryList().then((res: any)=>{
        faList.value = res
        })
    };

    // 提示
    const channelTypeIdHelp = ref("");
    const channelIdHelp = ref("");
    const channelSubAccountIdHelp = ref("");

    function checkChannelTypeId(){
        if(!queryParam.value.channelTypeId){
            channelTypeIdHelp.value = "请选择渠道类型";
        }else{
            channelTypeIdHelp.value = "";
        }
    }

    function checkChannelId(){
        if(!queryParam.value.channelId){
            channelIdHelp.value = "请选择渠道";
        }else{
            channelIdHelp.value = "";
        }
    }

    function checkChannelSubAccountId(){
        if(!queryParam.value.channelSubAccountId){
            channelSubAccountIdHelp.value = "请选择子渠道";
        }else{
            channelSubAccountIdHelp.value = "";
        }
    }

    function channelTypeChange(){
        queryParam.value.channelId = undefined;
        queryParam.value.channelSubAccountId = undefined;
    }

    function channelChange(){
        queryParam.value.channelSubAccountId = undefined;
    }

    function edit(record) {
        nextTick(() => {
            if(record.channelTypeId) {
                queryParam.value.channelTypeId = record.channelTypeId + "";
            }else{
                queryParam.value.channelTypeId = undefined;
            }
            if(record.channelId) {
                queryParam.value.channelId = record.channelId + "";
            }else{
                queryParam.value.channelId = undefined;
            }
            if(record.channelSubAccountId) {
                queryParam.value.channelSubAccountId = record.channelSubAccountId;
            }else{
                queryParam.value.channelSubAccountId = undefined;
            }
        });
  }

  //监听下拉框变化
//   watch(
//       () => queryParam.value.channelTypeId,
//       val => {
//           queryParam.value.channelId = undefined;
//           queryParam.value.channelSubAccountId = undefined;
//       },
//     );

//     //监听下拉框变化
//   watch(
//       () => queryParam.value.channelId,
//       val => {
//           queryParam.value.channelSubAccountId = undefined;
//       },
//     );

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
    queryParam.value.channelTypeId = undefined;
    queryParam.value.channelId = undefined;
    queryParam.value.channelSubAccountId = undefined;
  }
    /**
     * 展示提示的方法 解决父组件调用validate无法显示红色提示的问题
     */
    function check(){
        if(!queryParam.value.channelTypeId){
            channelTypeIdHelp.value = "请选择游戏";
        }else{
            channelTypeIdHelp.value = "";
        }
        if(!queryParam.value.channelId){
            channelIdHelp.value = "请选择子游戏";
        }else{
            channelIdHelp.value = "";
        }
        if(!queryParam.value.channelSubAccountId){
            channelSubAccountIdHelp.value = "请选择一级游戏包";
        }else{
            channelSubAccountIdHelp.value = "";
        }
    }
defineExpose({
    reload,
    check,
    edit
})
</script>
