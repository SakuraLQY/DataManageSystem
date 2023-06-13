<template>
    <a-modal :title="title" width="90%" :visible="showall" @cancel="handleCancel">
      <BasicTable 
      titleHelpMessage="温馨提醒" :columns ="columms" :dataSource="billDetail">
    </BasicTable>
  </a-modal>
  </template>
  <script lang="ts" setup>
    import {defineProps,ref,defineExpose} from 'vue';
    import { BasicTable, useTable, TableAction } from '/@/components/Table';
    //用来接收对应的数据
    const billDetail = ref([]);
    //弹窗的展示关闭|开启
    const showall = ref(false);
    const title = ref([]);
    //定义列
    const columms:any =ref([
  {
    title: '日期',
    align: "center",
    dataIndex: 'dateTime'
  },
  {
    title: '类型',
    align: "center",
    dataIndex: 'type'
  },
  {
    title: '金额',
    align: "center",
    dataIndex: 'cost'
  },{
    title: '账号余额',
    align: "center",
    dataIndex: 'surplusAmount'
  },
  {
    title: '其他数据',
    align: "center",
    width:300,
    dataIndex: 'otherData'
  },
  {
    title: '创建人',
    align: "center",
    dataIndex: 'createUser'
  }
  ,
  {
    title: '备注',
    align: "center",
    dataIndex: 'desc'
  }
]) 

  const showModal = (res)=>{
    billDetail.value = res;
    showall.value = true;
    title.value = res[0].nickName+"("+res[0].account+")的账单";
    
  }
  //点击取消改变弹窗属性
  const handleCancel = ()=>{
    showall.value = false;
  }
  defineExpose({
    showModal,
  });
  </script>
  <style scoped>
    .ant-modal-content{

  }
</style>