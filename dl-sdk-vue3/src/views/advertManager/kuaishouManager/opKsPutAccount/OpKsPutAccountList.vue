<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="16">
          <a-col :lg="8">
              <a-form-item label="账号状态">
                <j-dict-select-tag placeholder="请选择账号状态" v-model:value="queryParam.state" dictCode="account_state"/>
              </a-form-item>
            </a-col>
          <a-col :lg="8">
            <a-form-item label="账号">
              <j-search-select placeholder="请选择账号" v-model:value="queryParam.account" dict="open_gateway.op_put_account where channel_Id = '9' and level_id = '1' ,account,account" allowClear/>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
              <a-form-item label="账号昵称">
                <a-input placeholder="请输入账号昵称" maxlength="20" v-model:value="queryParam.nickName"></a-input>
              </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="游戏名">
              <a-select v-model:value="gameId" show-search  placeholder="请选择游戏名" allowClear @change="handleChange">
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="index"
                >
                {{gameItem.gameName  + '(' + index + ')'}}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="子游戏名">
              <a-select v-model:value="subGameId" show-search allowClear placeholder="请选择子游戏名" v-if="gameId !=='' && gameId !==undefined">
                <a-select-option
                  v-for="(gameItem,index) in faList[gameId].list"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName + '(' + gameItem.id + ')'}}
                </a-select-option>
              </a-select>
              <a-select v-model:value="queryParam.id" placeholder="请选择子游戏名" v-if="gameId ==='' || gameId ===undefined">
                <a-select-option
                  v-for="(gameItem,index) in []"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
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
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button  type="primary" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button  type="primary" preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>
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
      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
      <!--字段回显插槽-->
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>
      <template #state="{text}">
        <span style="color: greenyellow;" v-if="text=='在线'">{{text}}</span>
        <span style="color: red;" v-if="text=='下架'">{{text}}</span>
      </template>
      <template #id="{record}">
        {{record.id + getPid(record)}}
      </template>
      <template #password="{record}">
        {{record.password}}
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
    <OpKsPutAccountModal ref="registerModal" @success="handleSuccess"></OpKsPutAccountModal>
    <AddSecondAccountModal ref="secondAccountModal" @success2="handleSuccess"></AddSecondAccountModal>
  </div>
</template>

<script lang="ts" name="opputaccount-opPutAccount" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpKsPutAccount.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, auth, syncAccount, faList } from './OpKsPutAccount.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpKsPutAccountModal from './components/OpKsPutAccountModal.vue';
  import AddSecondAccountModal from './components/AddSecondAccountModal.vue';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import { message } from 'ant-design-vue';
  
  const queryParam = ref<any>({});
  const registerModal = ref();
  const secondAccountModal = ref();
  const gameId = ref();
  const subGameId = ref();
  function getPid(record) {
    let pid = "("+ record.pid +")";
    return record.levelId == 1 ? "": pid; 
  }

  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_put_account',
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
      name: "op_put_account",
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


  function handleChange(value) {
    if(!value){
      subGameId.value = undefined;
    }
  }
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
    record.appId = record.appId + "";
    record.state = record.state + "";
    registerModal.value.fillOptions(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
    record.appId = record.appId + "";
    record.state = record.state + "";
    registerModal.value.fillOptions(record);
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

  function toAuth(record){
    if(record.levelId != 1){
      message.warning('只有一级账号可以进行授权');
      return;
    }
    auth({accountId : record.id});
  }
  function toSyncAccount(record){
    if(record.levelId != 1){
      message.warning('只有一级账号可以进行进行添加二级账号');
      return;
    }
    secondAccountModal.value.disableSubmit = false;
    secondAccountModal.value.add(record);
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      },
      {
        label:'前往授权',
        onClick: toAuth.bind(null, record),
      },
      {
        label:'添加下级',
        onClick: toSyncAccount.bind(null, record)
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    let game = {
      gameId : gameId.value,
      subGameId : subGameId.value
    };
    queryParam.value.subGameIds = JSON.stringify(game);
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    queryParam.value = {};
    selectedRowKeys.value = [];
    gameId.value = undefined;
    subGameId.value = undefined;
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
