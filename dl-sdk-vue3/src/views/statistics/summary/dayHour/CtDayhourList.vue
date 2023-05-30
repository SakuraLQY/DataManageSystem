<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="广告列表">
              <j-search-select placeholder="请选择广告列表" v-model:value="queryParam.dealId" dict="open_gateway.op_deal,deal_name,id" allowClear/>
            </a-form-item>
          </a-col>
          <GameThirdSingleOptionForm ref="selectGameForm" @handler="getGameVal"></GameThirdSingleOptionForm>
          <template v-if="toggleSearchStatus">
            <ChannelThirdOptionForm ref="selectChannelForm" @handler="getChannelVal"></ChannelThirdOptionForm>
            <a-col :lg="8">
              <a-form-item label="起始日期">
                <a-date-picker  valueFormat="YYYY-MM-DD"  placeholder="请选择起始日期" v-model:value="queryParam.startTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="结束日期">
                <a-date-picker  valueFormat="YYYY-MM-DD"  placeholder="请选择结束日期" v-model:value="queryParam.endTime" />
              </a-form-item>
            </a-col>
            <a-col :lg="8">
              <a-form-item label="对比维度">
                <j-dict-select-tag   placeholder="请输入对比维度"  v-model:value="queryParam.level" :options =" compareLevelOption">
              
                </j-dict-select-tag>
                  <!-- <a-select-option @click="toclick(item.id)" v-for="(item,index) in mylist" :value="index">{{item.type}}</a-select-option>
             </a-select> -->
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <!-- <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button> -->
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a>
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <!-- <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button> -->
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button>批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!-- 操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
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
    <CtDayhourModal ref="registerModal" @success="handleSuccess"></CtDayhourModal>
  </div>
</template>

<script lang="ts" name="count-ctDayhour" setup>
  import { ref, reactive } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './CtDayhour.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CtDayhour.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import CtDayhourModal from './components/CtDayhourModal.vue'
  import {compareLevelOption} from './CtDayhour.data';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import {formatToDate } from '/@/utils/dateUtil';
  import GameThirdSingleOptionForm from '/@/views/common/gameThirdSingleOptionForm.vue';
  import ChannelThirdOptionForm from '/@/views/common/channelThirdOptionForm.vue';
  const queryParam = ref<any>({level:'count_active',startTime:formatToDate(new Date()),endTime:formatToDate(new Date())});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();

  // //获取游戏项目
  // function getGameName(gameId){
  //   let games = faList.value;
  //   return games[gameId].gameName;
  // }

  // //获取子游戏名
  // function getSubGameName(gameId,subGameId){
  //   let games = faList.value;
  //   for(let subgame of games[gameId].list){
  //     if(subgame.subId==subGameId){
  //       return subgame.subName;
  //     }
  //   }
  // }
  // //获取广告列表
  // function getDealId(dealId){
  //   let deals = faList.value;
  //   return deals[dealId].dealName;
  // }
  // //
  //注册table数据
  let getGameVal = (e: any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
    queryParam.value.pkgId = e.pkgId;
  };
  let getChannelVal = (e: any) => {
    queryParam.value.channelTypeId = e.channelTypeId;
    queryParam.value.channelId = e.channelId;
    queryParam.value.channelSubAccountId = e.channelSubAccountId;
  };
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'ct_dayHour',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      showActionColumn:false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "ct_dayHour",
      url: getExportUrl,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
  const labelCol = reactive({
    xs: { span: 24 },
    sm: { span: 7 },
  });
  const wrapperCol = reactive({
    xs: { span: 24 },
    sm: { span: 16 },
  });

  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
   
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
      },
    ];
  }
   
  // /**
  //  * 下拉操作栏
  //  */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }
  



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
