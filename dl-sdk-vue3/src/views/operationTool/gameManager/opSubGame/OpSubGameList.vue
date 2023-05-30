<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container" >
      <a-form @keyup.enter.native="searchQuery" :model="queryParam"  :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24" >
          <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
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
      <template #gameId="{ record }">
        <Tag color="green">
          {{ record.faGameName + '(' + record.gameId + ')'}}
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
    <OpSubGameModal ref="registerModal" @success="handleSuccess"></OpSubGameModal>
    <OpCommodityModal ref="registerGoodsModal" ></OpCommodityModal>
     <BasicModal title="参数" width="800px" :visible="visible" :okButtonProps="{ class: { 'jee-hidden': 'false' } }"  @cancel="handleCancel" cancelText="关闭">
        <a-form :labelCol="labelCol2" :wrapperCol="wrapperCol2">
          <a-row>
            <a-col :span="24">
              <a-form-item label="参数" >
                <a-textarea rows="8" style="width:800px;height:400px" v-model:value="conf" disabled="false"></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </BasicModal>
  </div>
</template>

<script lang="ts" name="game-opSubGame" setup>
  import { ref, reactive, watch, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpSubGame.data';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import { list, getConf, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpSubGame.api';
  import { goodsList } from '/@/views/operationTool/gameManager/opCommodity/OpCommodity.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpSubGameModal from './components/OpSubGameModal.vue'
  import OpCommodityList from '../opCommodity/OpCommodityList.vue'
  import OpCommodityModal from '/@/views/operationTool/gameManager/opCommodity/components/OpCommodityModal.vue'
  import JSwitch from '/@/components/Form/src/jeecg/components/JSwitch.vue';
  import func from '../../../vue-temp/vue-editor-bridge';
  import GameOptionForm from '/@/views/common/gameOptionForm.vue'

  const queryParam = ref<any>({});
  const visible = ref<boolean>(false);
  const toggleSearchStatus = ref<boolean>(false);
  const labelCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol2 = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const registerModal = ref();
  const registerGoodsModal = ref();
  let toGoodData = ref<any>({});
  // const registerList = ref();
  let conf = ref();
  const selectForm= ref<any>();
  let getGameVal = (e:any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.id = e.subGameId;
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
        params.status = 0;
        return Object.assign(params, queryParam.value);
      },
    },
    exportConfig: {
      name: "op_sub_game",
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
   * 参数
   */
  function handleConf(record) {
    getConf(record).then((res: any)=>{
      conf.value = res.result
    })
    visible.value = true
  }

  function handleCancel() {
    visible.value = false
  }
  
  
  /**
   * 内购商品配置
   */
  function handleShopConf(record) {
    goodsList({subGameId:record.id}).then((res: any)=>{
      if(res === null) {
        toGoodData.value.id = null;
      }else {
        toGoodData.value.id = res.id;
        toGoodData.value.goodsConf = res.goodsConf;
      }
      
    }).finally(() => {
      toGoodData.value.gameId = record.id;
      registerGoodsModal.value.edit(toGoodData.value,1);
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
    // getOptionList();
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
        label: '参数',
        onClick: handleConf.bind(null, record),
      },
      {
        label: '内购商品配置',
        onClick: handleShopConf.bind(null, record),
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
    selectForm.value.reload();
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
