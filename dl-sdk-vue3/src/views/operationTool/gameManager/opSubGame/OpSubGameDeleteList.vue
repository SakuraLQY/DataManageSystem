<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container" >
      <a-form @keyup.enter.native="searchQuery" :model="queryParam"  :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item label="游戏名">
              <a-select v-model:value="queryParam.gameId" show-search placeholder="请选择游戏名" allowClear>
                <a-select-option
                  v-for="(gameItem,index) in faList"
                  :key="index"
                  :value="index"
                >
                {{gameItem.gameName + '(' + index + ')'}}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :lg="8">
            <a-form-item label="子游戏名">
              <a-select v-model:value="queryParam.id" show-search placeholder="请选择子游戏名" v-if="queryParam.gameId !=='' && queryParam.gameId !==undefined" allowClear>
                <a-select-option
                  v-for="(gameItem,index) in faList[queryParam.gameId].list"
                  :key="index"
                  :value="gameItem.id"
                >
                {{gameItem.gameName + '(' + gameItem.id + ')'}}
                </a-select-option>
              </a-select>
              <a-select v-model:value="queryParam.id" placeholder="请选择子游戏名" v-if="queryParam.gameId ==='' || queryParam.gameId ===undefined">
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
                <!-- <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">
                  {{ toggleSearchStatus ? '收起' : '展开' }}
                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />
                </a> -->
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <template #gameId="{ record }">
        <Tag color="green">
          {{ record.faGameName + '(' + record.gameId + ')'}}
        </Tag>
      </template>
      <template #vendorId="{ record }">
        <Tag color="green">
          {{ record.vendorName + '(' + record.vendorId + ')'}}
        </Tag>
      </template>
      <template #id="{ record }">
        <Tag color="green">
          {{ record.gameName + '(' + record.id + ')'}}
        </Tag>
      </template>
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" @click="handleAdd" preIcon="ant-design:plus-outlined" v-show="false"> 新增</a-button>
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
        <TableAction :actions="getTableAction(record)"/>
        <TableAction :actions="getDropDownAction(record)"/>
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
    <OpSubGameModal ref="registerModal" @success="handleSuccess"></OpSubGameModal>
  </div>
</template>

<script lang="ts" name="game-opSubGame" setup>
  import { ref, reactive, watch, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpSubGame.data';
  import { list, optionList, recover } from './OpSubGame.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpSubGameModal from './components/OpSubGameModal.vue'
  import JSwitch from '/@/components/Form/src/jeecg/components/JSwitch.vue';
  
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const registerList = ref();
  let faList = ref([])
  let allList = ref([])
  //监听下拉框变化
  watch(
      () => queryParam.value.gameId,
      val => {
        queryParam.value.id = '';
      },
    );
  getOptionList();
  // 下拉框取值
  function getOptionList() {
    optionList({status:1}).then((res: any)=>{
      faList.value = res
    })
  };
  getList()
  function getList() {
    list({id:'',gameId:'',status:1}).then((res: any)=>{
      allList.value = res['records']
    })
  }


  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_sub_game',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        params.status = 1;
        return Object.assign(params, queryParam.value);
      },
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
   * 恢复
   */
  async function handleRecover(record: Recordable) {
    await recover(record);
    handleSuccess();
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }

   
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
    getList()
  }
   
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '恢复',
        popConfirm: {
          title: '是否确认恢复',
          confirm: handleRecover.bind(null, record),
        }
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
