<template>
  <div>
    <!-- 信息框 -->
    <a-modal v-model:visible="visible" title="创建结果" :maskClosable="false" :destroyOnClose="true">
      <div style="padding: 20px; font-size: 16px">
        <a-row :gutter="16">
          <a-col :span="8">
            <span>总共</span>
            <span class="span-font-normal">&nbsp;{{ timelineDataList.length }}</span>
          </a-col>
          <a-col :span="8">
            <span>成功</span>
            <span class="span-font-success">&nbsp;{{ createSuccess }}</span>
          </a-col>
          <a-col :span="8">
            <span>失败</span>
            <span class="span-font-error">&nbsp;{{ createFail }}</span>
          </a-col>
        </a-row>
        <!-- 时间线 -->
        <div class="time-line">
          <Timeline>
            <TimelineItem :color="time.status == 200 ? 'green' : 'red'" v-for="time in timelineDataList">
              ID&nbsp;{{ time.id }} -- {{ time.status == 200 ? '创建成功' : '创建失败, ' + time.message }}
            </TimelineItem>
          </Timeline>
        </div>
      </div>
      <template #footer>
        <a-button key="back" @click="handleCreateCancel">关闭</a-button>
      </template>
    </a-modal>
  </div>
</template>

<script lang="ts" setup>
  import { ref, nextTick, defineExpose, reactive, defineProps } from 'vue';
  import { message } from 'ant-design-vue';
  import Timeline from 'ant-design-vue/lib/timeline'; // 加载 JS
  import TimelineItem from 'ant-design-vue/lib/timeline/TimelineItem'; // 加载 JS
  import 'ant-design-vue/lib/timeline/style/css'; // 加载 CSS

  const visible = ref<boolean>(false);
  const disableSubmit = ref<boolean>(false);
  // 成功
  const createSuccess = ref<number>(0);
  // 失败
  const createFail = ref<number>(0);
  // 时间线
  const timelineDataList = ref<Array>([]);
  
  /**
   * 数据初始化
   */
  function init(data){
    visible.value = true;
    let arr = data;
    // 记录成功失败数量
    for(let i in arr) {
      if(arr[i].status == 200) {
        createSuccess.value++;
      } else{
        createFail.value++;
      }
    }
    // 赋值数组
    timelineDataList.value = arr;
  }

  /**
   * 关闭并清空Modal
   */
  function handleCreateCancel() {
    createFail.value = 0;
    createSuccess.value = 0;
    timelineDataList.value = [];
    visible.value = false;
  }

  defineExpose({
    disableSubmit,
    visible,
    init
  });
</script>

<style>
/**隐藏样式-modal确定按钮 */
.jee-hidden {
  display: none !important;
}
.span-font-normal {
  color: #108ee9;
  font-weight: bold;
}
.span-font-error {
  color: #ff4d4f;
  font-weight: bold;
}
.span-font-success {
  color: #87d068;
  font-weight: bold;
}
.time-line {
  padding: 20px 0px 20px 0px;
  height: 300px;
  overflow-y: auto;
}
</style>
