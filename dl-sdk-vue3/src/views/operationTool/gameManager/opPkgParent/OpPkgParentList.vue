<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container" >
      <a-form @keyup.enter.native="searchQuery" :model="queryParam"  :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24" >
          <a-col :lg="8">
            <a-form-item label="游戏名">
              <a-select v-model:value="queryParam.gameId"   placeholder="请选择游戏名" allowClear>
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="index"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="子游戏名">
              <a-select v-model:value="queryParam.subGameId" allowClear placeholder="请选择子游戏名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined">
                <a-select-option
                  v-for="(gameItem,index) in faList[queryParam.gameId].list"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName}}
                </a-select-option>
              </a-select>
              <a-select v-model:value="queryParam.subGameId" placeholder="请选择子游戏名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined">
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
      <!-- <template #game="{record}">
        {{getGameName(record.gameId)}}
      </template>
      <template #subGame="{record}">
        {{gerSubGameName(record.gameId,record.subGameId)}}
      </template> -->
      <template #state="{record}">
        <BigFileupload :gameId="record.gameId" :subGameId="record.subGameId" :label="record.state?'更新母包':'上传母包'" accept=".apk" :onSuccess="callback"></BigFileupload>
          
        </template>
      <template #htmlSlot="{text}">
        <div v-html="text"></div>
      </template>

      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        <!-- {{ getAreaTextByCode(text) }} -->
      </template>
      <template #fileSlot="{text}">
        <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(text)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpPkgParentModal ref="registerModal" @success="handleSuccess"></OpPkgParentModal>
    <OpPackConfigModal ref="opPackConfigModal" ></OpPackConfigModal>
  </div>
</template>

<script lang="ts" name="opPkgParent-opPkgParent" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpPkgParent.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, } from './OpPkgParent.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpPkgParentModal from './components/OpPkgParentModal.vue';
  import BigFileupload from './components/UploadUtil.vue';
  import {optionList} from '../opSubGame/OpSubGame.api'
  import OpPackConfigModal from './components/OpPackConfigModal.vue'
  import { getObjByGameId } from './OpPackConfig.api';
 
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const opPackConfigModal = ref();

  let faList = ref([]);
  getOptionList();
  // 下拉框取值
  function getOptionList() {
    optionList({status:0}).then((res: any)=>{
      faList.value = res;
    })
  };

  function callback(){
    reload();
  }
  function getGameName(gameId){
    let games =faList.value;
    return games[gameId].gameName
  }
  function gerSubGameName(gameId, subGameId){
    let games =faList.value;
    for(let subgame of games[gameId].list){
      if(subgame.id == subGameId){
        return subgame.gameName
      }
    }
  }

  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_pkg_parent',
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
      name: "op_pkg_parent",
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
   * 游戏打包配置
   */
  function handlePackConf(record) {
    getObjByGameId({subGameId:record.subGameId}).then((res: any)=>{
      opPackConfigModal.value.edit(res, record.subGameId_dictText);
    })
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
      },{
        label: '游戏打包配置',
        onClick: handlePackConf.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        }
      },
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