<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <GameOptionForm ref="selectForm" @handler="getGameVal"></GameOptionForm>
          <template v-if="toggleSearchStatus">
            <a-col :lg="8">
              <a-form-item label="渠道">
                <j-dict-select-tag placeholder="请选择渠道" v-model:value="queryParam.channelId" dictCode="open_gateway.op_channel,channel_name,id"/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
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
      <!--省市区字段回显插槽-->
      <template #pcaSlot="{text}">
        {{ getAreaTextByCode(text) }}
      </template>
      <!--图片素材插槽-->
      <template #iconMaterialPathContent="{ record }">
        <Tag color="green">
          <img v-if ="record.iconMaterialPackPath" :src="getImage(record.iconMaterialPackPath)" min-width="70" height="70" />
        </Tag>
      </template>
      <template #screenMaterialPackPathContent="{ record }">
        <Tag color="green">
          <img v-if ="record.screenMaterialPackPath" :src="getImage(record.screenMaterialPackPath)" min-width="70" height="70" />
        </Tag>
      </template>
      <template #loadingMaterialPackPathContent="{ record }">
        <Tag color="green">
          <img v-if ="record.loadingMaterialPackPath" :src="getImage(record.loadingMaterialPackPath)" min-width="70" height="70" />
        </Tag>
      </template>
      <template #loginMaterialPackPathContent="{ record }" >
        <Tag color="green">
          <img v-if ="record.loginMaterialPackPath" :src="getImage(record.loginMaterialPackPath)" min-width="70" height="70" />
        </Tag>
      </template>
      <template #logoMaterialPackPathContent="{ record }">
        <Tag color="green">
          <img v-if ="record.logoMaterialPackPath" :src="getImage(record.logoMaterialPackPath)" min-width="70" height="70" />
        </Tag>
      </template>
      <template #apkPathContent="{ record }">
        <span v-if="!record.apkPath" style="font-size: 12px;font-style: italic;">无文件</span>
        <a-button v-else :ghost="true" type="primary" preIcon="ant-design:download-outlined" size="small" @click="downloadFile(record.apkPath)">下载</a-button>
      </template>
    </BasicTable>
    <!-- 表单区域 -->
    <OpPkgModal ref="registerModal" @success="handleSuccess"></OpPkgModal>
    <OpCommodityModal ref="registerGoodsModal" ></OpCommodityModal>
    <OpPkgChannelConfigModal ref="registerChannelConfigModal" @ok="handleOk" @success="handleSuccess"></OpPkgChannelConfigModal>
  </div>
</template>

<script lang="ts" name="game-opPkg" setup>
  import { ref, reactive, watch, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpPkg.data';
  import { list, saveOrUpdate, deleteOne, batchDelete, getImportUrl, getExportUrl, updatePackState } from './OpPkg.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpPkgModal from './components/OpPkgModal.vue'
  import OpPkgChannelConfigModal from './components/OpPkgChannelConfigModal.vue'
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import { optionList } from '../opSubGame/OpSubGame.api';
  import { getFileAccessHttpUrl } from '/@/utils/common/compUtils';
  import GameOptionForm from '/@/views/common/gameOptionForm.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  import {BasicModal, useModalInner} from '/@/components/Modal';
  import OpCommodityModal from '/@/views/operationTool/gameManager/opCommodity/components/OpCommodityModal.vue'
  import { goodsList } from '/@/views/operationTool/gameManager/opCommodity/OpCommodity.api';

  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const registerConfigModal = ref();
  const registerChannelConfigModal =ref();
  const readPhotoUrl = (import.meta.env.VITE_GLOB_API_URL) + '/game/opPackMaterial/readPhoto?path=';
  const selectForm= ref();
  const registerGoodsModal = ref();
  let toGoodData = ref<any>({});
  let getGameVal = (e:any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
  }
  function handleCancel() {
    visible.value = false
  }
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_pkg',
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
      name: "op_pkg",
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
   * 内购商品配置
   */
  function handleShopConf(record) {
    goodsList({pkgId:record.id}).then((res: any)=>{
      if(res === null) {
        toGoodData.value.id = null;
      }else {
        toGoodData.value.id = res.id;
        toGoodData.value.goodsConf = res.goodsConf;
      }
      
    }).finally(() => {
      toGoodData.value.gameId = record.id;
      registerGoodsModal.value.edit(toGoodData.value,2);
    })
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
  
  /**
   * 渠道配置
   */
   function handleChannelConfig(record: Recordable) {
    registerChannelConfigModal.value.edit(record);
  }

  /**
   * 打包
   */
  function handlePackage(record){
    updatePackState({ id: record.id }, handleSuccess);
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
      }
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
        label: '打包',
        onClick: handlePackage.bind(null,record),
      },{
        label: '渠道参数设置',
        onClick: handleChannelConfig.bind(null, record),
      },{
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
    selectForm.value.reload();
    //刷新数据
    reload();
  }

    // 获取图片
  function getImage(item) {
    if (item) {
      return getFileAccessHttpUrl(item)
    }
    return '';
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
