<template>
  <BasicModal :title="modalTitle" :width="width" :visible="visible"  @cancel="handleCancel" @ok="handleCancel" cancelText="关闭">
    <div>
      <a-descriptions>
      <a-descriptions-item label="渠道游戏包名称">{{ pkgName }}</a-descriptions-item>
      <a-descriptions-item label="渠道子账号名称">{{ subChannelName }}</a-descriptions-item>
      <a-descriptions-item label="投放账号名">{{ accountName }}</a-descriptions-item>
      </a-descriptions>
      <a-table :columns="sevenDayColumn" :data-source="sevenDay" :pagination="false" bordered>
        <template #title >广告最近七天数据</template>
      </a-table>
      <a-table :columns="sameAccountColumn" :data-source="sameAccount" :pagination="false" bordered>
        <template #title>同账号广告数据</template>
      </a-table>
    </div>
  </BasicModal>
</template>

<script lang="ts" name="advert-opJrttPromotion" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { sameAccountColumn,sevenDayColumn } from '../CtSummary.data';
  import { getDealInfoData } from '../CtSummary.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import { Descriptions } from 'ant-design-vue';
  import {BasicModal} from '/@/components/Modal';
  const visible = ref<boolean>(false);
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const width = ref<number>(1800);
  const dealRecord = ref<any>({});
  const modalTitle = ref("");
  const sevenDay = ref([]);
  const sameAccount = ref([]);
  const pkgName = ref("");
  const subChannelName = ref("");
  const accountName = ref("");
  //注册table数据
    const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

   
  // /**
  //  * 成功回调
  //  */
  // function handleSuccess() {
  //   (selectedRowKeys.value = []) && reload();
  // }
   

  function edit(record){
    visible.value = true;
    modalTitle.value = record.name + " 数据";
    // 初始化
    getDealInfoData({
      regStartTime : record.regStartTime,
      regEndTime : record.regEndTime,
      dealId : record.id,
    }).then(res=>{
      sevenDay.value = res.sevenDay;
      sameAccount.value = res.sameAccount;
      pkgName.value = res.pkgName;
      subChannelName.value = res.subChannelName;
      accountName.value = res.accountName;
    });
  }


  function handleCancel() {
    visible.value = false;
  }
  
  defineExpose({
    edit,
  });


</script>

<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      width: calc(50% - 15px);
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
  }
</style>
