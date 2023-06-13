<template>
    <a-col :lg="8">
        <a-form-item label="渠道类型">
          <a-select v-model:value="queryParam.channelTypeId" optionFilterProp="label" mode="multiple" show-search  placeholder="请选择渠道类型" allowClear>
            <a-select-option
                v-for="(gameItem,index) in resList[1]"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
          </a-select>
        </a-form-item>
        </a-col>
        <a-col :lg="8">
        <a-form-item label="渠道">
            <a-select v-model:value="queryParam.channelId" optionFilterProp="label" mode="multiple" show-search allowClear placeholder="请选择渠道" v-if="queryParam.channelTypeId !=='' && queryParam.channelTypeId !==undefined">
            <a-select-option
                v-for="(gameItem,index) in channelList"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.channelId"  placeholder="请选择渠道" v-if="queryParam.channelTypeId ==='' || queryParam.channelTypeId ===undefined">
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
    <a-col :lg="8">
        <a-form-item label="渠道子账号">
            <a-select v-model:value="queryParam.channelSubAccountId" optionFilterProp="label" mode="multiple" show-search allowClear placeholder="请选择渠道子账号" v-if="queryParam.channelTypeId !=='' && queryParam.channelTypeId !==undefined && queryParam.channelId !=='' && queryParam.channelId !==undefined">
            <a-select-option
                v-for="(gameItem,index) in channelSubAccountList"
                :key="index"
                :value="gameItem.id"
                :label="gameItem.name + '(' + gameItem.id + ')'"
            >
            {{gameItem.name + '(' + gameItem.id + ')'}}
            </a-select-option>
            </a-select>
            <a-select v-model:value="queryParam.channelSubAccountId" placeholder="请选择渠道子账号" v-if="queryParam.channelTypeId ==='' || queryParam.channelTypeId ===undefined || queryParam.channelId ==='' || queryParam.channelId ===undefined">
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
    import { ref, reactive, watch, defineExpose} from 'vue';
    import { thirdMuchList } from '/@/views/advertManager/channelManager/opChannelSubAccount/OpChannelSubAccount.api';

     const queryParam = ref<any>({});
     let emitFn = defineEmits(['handler'])
     let resList = ref([]);
     let channelList = ref([]);
     let channelSubAccountList = ref([]);

     getOptionList();
    // 游戏、子游戏下拉框取值
    function getOptionList() {
        thirdMuchList().then((res: any)=>{
        resList.value = res
        })
    };

  //监听下拉框变化
  watch(
      () => queryParam.value.channelTypeId,
      val => {
        channelList.value = [];
        if(val.length === 0) {
        queryParam.value.channelId = [];
        queryParam.value.channelSubAccountId = [];
        }else {
        val.forEach(element => {
            if(resList.value[1][element].list !== null) {
                resList.value[1][element].list.forEach(element2 => {
                    channelList.value.push(element2)
                });
            }
        });
        }
      },
    );

    //监听下拉框变化
  watch(
      () => queryParam.value.channelId,
      val => {
          channelSubAccountList.value = [];
          if(val.length === 0) {
              queryParam.value.channelSubAccountId = [];
          }else {
            val.forEach(element => {
                if(resList.value[2][element].list !== null) {
                    resList.value[2][element].list.forEach(element2 => {
                        channelSubAccountList.value.push(element2)
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
    queryParam.value.channelTypeId = [];
    queryParam.value.channelId = [];
    queryParam.value.channelSubAccountId = [];
  }
defineExpose({
    reload
})
</script>
