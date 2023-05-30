<template>
  <div>
    <!--引用表格-->
   <BasicTable @register="registerTable" :rowSelection="rowSelection">
     <template #realNameInfo="{ record }">
        <Tag color="green">
          {{ record.realName + record.realNumber }}
        </Tag>
      </template>
     <!--插槽:table标题-->
      <template #tableTitle>
      </template>
       <!--操作栏-->
      <template #action="{ record }">
        <TableAction  :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
         <div v-html="text"></div>
      </template>
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
         {{ getAreaTextByCode(text) }}
      </template>
      <template #fileSlot="{text}">
         <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
         <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpUserModal @register="registerModal" @success="handleSuccess"></OpUserModal>
    <OpUserDetailModal ref="registerModal2" @success="handleSuccess"></OpUserDetailModal>
  </div>
</template>

<script lang="ts" name="user-opUser" setup>
  import {ref, computed, unref} from 'vue';
  import {BasicTable, useTable, TableAction} from '/@/components/Table';
  import {useModal} from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage'
  import OpUserModal from './components/OpUserModal.vue'
  import OpUserDetailModal from './components/OpUserDetailModal.vue'
  import {columns, searchFormSchema} from './OpUser.data';
  import {list, removePhoneOne} from './OpUser.api';
  import { message } from 'ant-design-vue';


  // import { downloadFile } from '/@/utils/common/renderUtils';
  const checkedKeys = ref<Array<string | number>>([]);
  //注册model
  const [registerModal, {openModal}] = useModal();
  const registerModal2 = ref();
  //注册table数据
  const { prefixCls,tableContext} = useListPage({
      tableProps:{
           title: 'op_user',
           api: list,
           columns,
           canResize:false,
           formConfig: {
              //labelWidth: 120,
              schemas: searchFormSchema,
              autoSubmitOnEnter:true,
              showAdvancedButton:true,
              fieldMapToNumber: [
              ],
              fieldMapToTime: [
              ],
            },
           actionColumn: {
               width: 120,
               fixed:'right'
            },
      },
  })

  const [registerTable, {reload},{ rowSelection, selectedRowKeys }] = tableContext


   /**
    * 详情
   */
  function handleDetail(record: Recordable) {
     registerModal2.value.edit(record);
   }

   /**
    * 修改密码
   */
  function editPass(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: true,
       showFooter: true,
     });
   }

   /**
    * 修改余额
   */
  function editPlatformCurrency(record: Recordable) {
     openModal(true, {
       record,
       isUpdate: false,
       showFooter: true,
     });
   }

   /**
    * 解绑手机号
    */
  function removePhone(record: Recordable) {
    if(record.userPhone !== "") {
      removePhoneOne({id: record.id});
    }else {
      message.warning('该用户手机已解绑，无需再次解绑');
    }
    handleSuccess();
   }
 
   /**
    * 成功回调
    */
  function handleSuccess() {
      (selectedRowKeys.value = []) && reload();
   }

     /**
        * 下拉操作栏
        */
  function getDropDownAction(record){
       return [
         {
           label: '详情',
           onClick: handleDetail.bind(null, record),
         },
         {
           label: '修改密码',
           onClick: editPass.bind(null, record),
         },
         {
           label: '修改余额',
           onClick: editPlatformCurrency.bind(null, record),
         },
         {
           label: '解绑手机号',
           popConfirm: {
             title: '是否确认解绑手机号',
             confirm: removePhone.bind(null, record),
           }
         }
       ]
   }


</script>

<style scoped>

</style>