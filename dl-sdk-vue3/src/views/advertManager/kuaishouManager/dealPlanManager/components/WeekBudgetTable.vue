<template>
  <a-table bordered :data-source="dataSource" :columns="weekColumns" :pagination="false" size="small">
    <!-- 周一 -->
    <template #mon="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.mon || editableData.mon == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.mon" @pressEnter="saveWeek('mon')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('mon')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('mon')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周二 -->
    <template #tues="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.tues || editableData.tues == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.tues" @pressEnter="saveWeek('tues')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('tues')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('tues')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周三 -->
    <template #wed="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.wed || editableData.wed == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.wed" @pressEnter="saveWeek('wed')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('wed')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('wed')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周四 -->
    <template #thur="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.thur || editableData.thur == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.thur" @pressEnter="saveWeek('thur')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('thur')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('thur')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周五 -->
    <template #fri="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.fri || editableData.fri == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.fri" @pressEnter="saveWeek('fri')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('fri')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('fri')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周六 -->
    <template #sat="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.sat || editableData.sat == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.sat" @pressEnter="saveWeek('sat')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('sat')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('sat')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
    <!-- 周日 -->
    <template #sun="{ text, record }">
      <div class="editable-cell">
        <div v-if="editableData.sun || editableData.sun == 0" class="editable-cell-input-wrapper">
          <a-input-number v-model:value="editableData.sun" @pressEnter="saveWeek('sun')" :min="0" :max="100000000" :precision="0" />
          <check-outlined class="editable-cell-icon-check" @click="saveWeek('sun')" />
        </div>
        <div v-else class="editable-cell-text-wrapper">
          {{ text == '0' ? '不限' : text }}
          <edit-outlined class="editable-cell-icon" @click="editWeek('sun')" v-if="props.canEdit" />
        </div>
      </div>
    </template>
  </a-table>
  <div style="color: crimson" v-if="props.canEdit"> 每天不小于 500 元，不超过 100000000 元，0表示预算不限，仅支持输入整数 </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, watch } from 'vue';
  import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
  import { weekColumns } from '../dealPlan.data';

  const props = defineProps({
    data: { type: String, default: false },
    canEdit: { type: Boolean, default: false },
  });

  watch(
    () => props.data,
    () => {
      init();
    }
  );
  // 变化的数据
  const editableData: Ref<string> = reactive({});
  // 数据源
  const dataSource: Ref<DataItem[]> = ref([
    {
      mon: 0,
      tues: 0,
      wed: 0,
      thur: 0,
      fri: 0,
      sat: 0,
      sun: 0,
    },
  ]);

  init();
  /**
   * 数据初始化
   */
  function init() {
    let weekArr;
    if (props.data == null || typeof props.data != 'string') {
      weekArr = ['0', '0', '0', '0', '0', '0', '0'];
    } else {
      weekArr = props.data.split(',');
    }
    dataSource.value[0].mon = weekArr[0];
    dataSource.value[0].tues = weekArr[1];
    dataSource.value[0].wed = weekArr[2];
    dataSource.value[0].thur = weekArr[3];
    dataSource.value[0].fri = weekArr[4];
    dataSource.value[0].sat = weekArr[5];
    dataSource.value[0].sun = weekArr[6];
  }

  /**
   * 修改列
   */
  const editWeek = (key: string) => {
    // 其他编辑先保存
    for (let item in editableData) {
      saveWeek(item);
    }
    // 赋值
    switch (key) {
      case 'mon':
        editableData[key] = dataSource.value[0].mon;
        break;
      case 'tues':
        editableData[key] = dataSource.value[0].tues;
        break;
      case 'wed':
        editableData[key] = dataSource.value[0].wed;
        break;
      case 'thur':
        editableData[key] = dataSource.value[0].thur;
        break;
      case 'fri':
        editableData[key] = dataSource.value[0].fri;
        break;
      case 'sat':
        editableData[key] = dataSource.value[0].sat;
        break;
      case 'sun':
        editableData[key] = dataSource.value[0].sun;
    }
  };

  /**
   * 保存列
   */
  const saveWeek = (key: string) => {
    switch (key) {
      case 'mon':
        let mon = editableData.mon;
        if (mon == null) {
          mon = 0;
        }
        dataSource.value[0].mon = mon;
        delete editableData.mon;
        break;
      case 'tues':
        let tues = editableData.tues;
        if (tues == null) {
          tues = 0;
        }
        dataSource.value[0].tues = tues;
        delete editableData.tues;
        break;
      case 'wed':
        let wed = editableData.wed;
        if (wed == null) {
          wed = 0;
        }
        dataSource.value[0].wed = wed;
        delete editableData.wed;
        break;
      case 'thur':
        let thur = editableData.thur;
        if (thur == null) {
          thur = 0;
        }
        dataSource.value[0].thur = thur;
        delete editableData.thur;
        break;
      case 'fri':
        let fri = editableData.fri;
        if (fri == null) {
          fri = 0;
        }
        dataSource.value[0].fri = fri;
        delete editableData.fri;
        break;
      case 'sat':
        let sat = editableData.sat;
        if (sat == null) {
          sat = 0;
        }
        dataSource.value[0].sat = sat;
        delete editableData.sat;
        break;
      case 'sun':
        let sun = editableData.sun;
        if (sun == null) {
          sun = 0;
        }
        dataSource.value[0].sun = sun;
        delete editableData.sun;
    }
  };

  let emitFn = defineEmits(['syncWeekData']);

  // 监听数据变化 传值给父组件
  watch(dataSource.value, () => {
    // 将数据转为数组
    let arr = [
      dataSource.value[0].mon,
      dataSource.value[0].tues,
      dataSource.value[0].wed,
      dataSource.value[0].thur,
      dataSource.value[0].fri,
      dataSource.value[0].sat,
      dataSource.value[0].sun,
    ];
    emitFn('syncWeekData', arr);
  });
</script>
<style lang="less" scoped>
  .editable-cell {
    position: relative;
    .editable-cell-input-wrapper,
    .editable-cell-text-wrapper {
      padding-right: 24px;
    }

    .editable-cell-text-wrapper {
      padding: 5px 24px 5px 5px;
    }

    .editable-cell-icon,
    .editable-cell-icon-check {
      position: absolute;
      right: 0;
      width: 20px;
      cursor: pointer;
    }

    .editable-cell-icon {
      margin-top: 4px;
      display: none;
    }

    .editable-cell-icon-check {
      line-height: 28px;
    }

    .editable-cell-icon:hover,
    .editable-cell-icon-check:hover {
      color: #108ee9;
    }

    .editable-add-btn {
      margin-bottom: 8px;
    }
  }
  .editable-cell:hover .editable-cell-icon {
    display: inline-block;
  }
</style>
