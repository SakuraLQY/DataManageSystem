<template>
  <div>
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
            <GameOptionForm ref="selectForm" @handler="getGameVal"/>
          <a-col :lg="8">
            <a-form-item label="创建用户">
              <j-search-select placeholder="请选择创建用户" v-model:value="queryParam.createBy" dict="sys_user,username,username" allowClear/>
            </a-form-item>
          </a-col>
          <a-col>
            <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
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
      <!--落地页内容-->
      <template #siteContent = "{record}">
        <a-card
        :title="record.siteName"
        :bodyStyle="{'padding': '0px'}"
        :headStyle="{'backgroundColor': '#rgba(255, 255, 255, 0.4)'}"
        style="width:240px"
        >
        <div class="template-thumb">
          <div :class="{float_top: content.float == 'top', float_bottom: content.float_bottom == 'bottom'}" 
          v-for="content in JSON.parse(record.siteContent)">
            <!-- 下载按钮 -->
            <div
                :style="{ 'text-align': 'center', 'background-color': content.bg_color, color: content.color }"
                v-if="content.name == 'XrButton' && content.image_url == undefined"
              >
                {{ content.text }}
            </div>
              <!-- 图片 -->
              <img :src="content.image_url" v-else-if="content.image_url != undefined" />
              <img :src="content.group_content[0]['image_url']" v-else />
          </div>
        </div>
        </a-card>
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
    <OpJrttSiteModal ref="registerModal" @success="handleSuccess"></OpJrttSiteModal>
  </div>
</template>

<script lang="ts" name="opJrttSite-opJrttSite" setup>
  import { ref, reactive, onMounted, onUnmounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns } from './OpJrttSite.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './OpJrttSite.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  import OpJrttSiteModal from './components/OpJrttSiteModal.vue'
  import GameOptionForm from '/@/views/common/gameOptionForm.vue'
  import JSearchSelect from '/@/components/Form/src/jeecg/components/JSearchSelect.vue';
  
  const queryParam = ref<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: 'op_jrtt_site',
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
      name: "op_jrtt_site",
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

  let getGameVal = (e:any) => {
    queryParam.value.gameId = e.gameId;
    queryParam.value.subGameId = e.subGameId;
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
      // {
      //   label: '编辑',
      //   onClick: handleEdit.bind(null, record),
      // },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
        },

      }
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      // {
      //   label: '详情',
      //   onClick: handleDetail.bind(null, record),
      // }, {
      //   label: '删除',
      //   popConfirm: {
      //     title: '是否确认删除',
      //     confirm: handleDelete.bind(null, record),
      //   }
      // }
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
  .template-thumb{
        width: 100%;
        height: 300px;
        overflow-y:auto;
        position:relative;
        margin-bottom: 5px;
    }
    .template-thumb img{
        width: 100%;
        /*margin: 5px 2%;*/
    }
    .template-thumb .float_top{
        position:sticky;
        top: 0;
        left: 0;
    }
    .template-thumb .float_bottom{
        position:sticky;
        bottom: 0;
        left: 0;
    }
</style>
