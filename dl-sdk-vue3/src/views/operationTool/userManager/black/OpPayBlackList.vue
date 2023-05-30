<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="规则类型">
              <j-dict-select-tag placeholder="请选择规则类型" v-model:value="queryParam.ruleType" dictCode="pay_rule_type" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
           <a-form-item label="游戏名">
             <j-search-select v-if="queryParam.ruleType === undefined || queryParam.ruleType === null || queryParam.ruleType === ''" v-model:value="queryParam.ruleId" placeholder="请先选择规则类型"   :disabled="disabled" allowClear/>
              <j-search-select v-if="queryParam.ruleType === '0'" v-model:value="queryParam.ruleId" dict="open_gateway.op_game,game_name,id"  placeholder="请选择游戏" :disabled="disabled" allowClear/>
              <j-search-select v-if="queryParam.ruleType === '1'" v-model:value="queryParam.ruleId" dict="open_gateway.op_sub_game,game_name,id"  placeholder="请选择子游戏" :disabled="disabled" allowClear/>
            <!-- <a-select v-model:value="queryParam.ruleId" placeholder="请选择游戏" >
                <a-select-option
                  v-for="(optionItem,index) in gameList"
                  :key="index"
                  :value="optionItem.ruleId"
                  :disabled="disabled"
                >
                {{optionItem.gameName}}
                </a-select-option>
              </a-select> -->
            <!-- <a-form-item label="规则ID">
              <a-input placeholder="请输入规则ID" v-model:value="queryParam.ruleId"></a-input> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px" v-show="false">
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
      <template #ruleNameAndId="{ record }">
        <Tag color="green" v-if="record.gameName === null">
          {{  record.subGameName + '('+record.ruleId +')' }}
        </Tag>
        <Tag color="green" v-if="record.subGameName === null">
          {{  record.gameName + '('+record.ruleId +')' }}
        </Tag>
      </template>
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls" v-show="false"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls" v-show="false">导入</j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-show="false">批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
      </template>
      <!--操作栏-->
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
    <OpPayBlackModal ref="registerModal" @success="handleSuccess"></OpPayBlackModal>
  </div>
</template>

<script lang="ts" name="users-opPayBlack" setup>
  import { watch, ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpPayBlack.data';
  import { list, optionList, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpPayBlack.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpPayBlackModal from './components/OpPayBlackModal.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const queryParam = ref<any>({});
  // let gameList = ref([]);
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  // watch(queryParam.value, (newName, oldName) => {
  //   optionList({ruleType:queryParam.value.ruleType}).then((res: any)=>{
  //     gameList.value = res
  //   })
  // },{deep:false});
  watch(
      () => queryParam.value.ruleType,
      val => {
         queryParam.value.ruleId = undefined; 
      })
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_pay_black',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "op_pay_black",
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
   
  /**
   * 下拉操作栏
   */
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
    // queryParam.ruleId = '';
    // queryParam.ruleType = undefined;
    queryParam.value = {};
    // gameList.value = [];
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
