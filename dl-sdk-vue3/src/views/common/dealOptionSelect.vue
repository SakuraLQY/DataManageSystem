<template>
    <a-col :lg="8">
        <a-form-item label="广告">
          <a-select v-model:value="queryParam.dealId"  placeholder="请选择广告" mode="multiple" optionFilterProp="label"  allowClear>
            <a-select-option v-for="(dealItem,index) in paginatedData" :key="index" :value="dealItem.id" :label="dealItem.dealName">{{ dealItem.dealName }}</a-select-option>
            <a-select-option value="" disabled>
              <div style="bottom: -10px">
                <a-pagination
                  v-model:current="pageNo"
                  :total="total"
                  :page-size="pageSize"
                  prev-text="Prev"
                  next-text="Next"
                  show-prev-next-jumpers="false"
                  @change="pageChange"
                >
            </a-pagination>
              </div>
            </a-select-option>
        </a-select>
        </a-form-item>
    </a-col>
</template>

<script lang="ts"  setup>
    import { ref, computed, onMounted, reactive, watch, defineExpose} from 'vue';
    import { optionList, selectVagueList } from '/@/views/advertManager/toutiaoManager/jrttdeal/JrttDeal.api';
    import { Pagination } from 'ant-design-vue';
    // import { debounce } from 'lodash';

     const queryParam = ref<any>({});
     const APagination = Pagination;
     let emitFn = defineEmits(['handler'])
     let paginatedData = ref([]);
     let pageSize = ref(10)
     let pageNo = ref(1)
     let total = ref(0);
     onMounted(() => {
      pageChange(pageNo.value, pageSize.value)
    })

    function pageChange(page, pageSize) {
      optionList({pageNo:page, pageSize:pageSize}).then((res: any)=>{
            paginatedData.value = res.list.records
            total.value = res.total
            pageNo.value = page
        })
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
    queryParam.value.dealId = [];
  }
defineExpose({
    reload
})
</script>
